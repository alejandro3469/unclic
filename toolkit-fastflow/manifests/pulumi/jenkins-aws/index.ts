/**
 * FastFlow: Jenkins + Gitea + POS on AWS (Pulumi).
 * Equivalent to Terraform jenkins-aws (VPC, Jenkins controller, Gitea, POS EC2).
 * Combine with Jenkins open source: this provisions the infra; Jenkins runs pipelines on it.
 */
import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";

const config = new pulumi.Config("jenkins-aws");
const awsConfig = new pulumi.Config("aws");

const namePrefix = config.get("namePrefix") ?? "fastflow";
const awsRegion = awsConfig.get("region") ?? "us-east-2";
const author = config.get("author") ?? "fastflow";

const jenkinsAmiId = config.require("jenkinsControllerAmiId");
const giteaAmiId = config.require("giteaAmiId");
const posAmiId = config.require("posAmiId");

const jenkinsInstanceType = config.get("jenkinsInstanceType") ?? "t3.medium";
const giteaInstanceType = config.get("giteaInstanceType") ?? "t3.micro";
const posInstanceType = config.get("posInstanceType") ?? "t3.micro";

// Use first two AZs in region
const availabilityZones = config.getObject<string[]>("availabilityZones") ?? [
  `${awsRegion}a`,
  `${awsRegion}b`,
];

// --- Network ---
const vpc = new aws.ec2.Vpc(`${namePrefix}-vpc`, {
  cidrBlock: "10.0.0.0/16",
  enableDnsHostnames: true,
  tags: { Name: `${namePrefix}-vpc`, Author: author },
});

const publicSubnets = availabilityZones.map((az, i) =>
  new aws.ec2.Subnet(`${namePrefix}-public-${i}`, {
    vpcId: vpc.id,
    cidrBlock: `10.0.${i * 2 + 1}.0/24`,
    availabilityZone: az,
    mapPublicIpOnLaunch: true,
    tags: { Name: `${namePrefix}-public-${i}` },
  })
);

const privateSubnets = availabilityZones.map((az, i) =>
  new aws.ec2.Subnet(`${namePrefix}-private-${i}`, {
    vpcId: vpc.id,
    cidrBlock: `10.0.${i * 2}.0/24`,
    availabilityZone: az,
    mapPublicIpOnLaunch: false,
    tags: { Name: `${namePrefix}-private-${i}` },
  })
);

const igw = new aws.ec2.InternetGateway(`${namePrefix}-igw`, {
  vpcId: vpc.id,
  tags: { Name: `${namePrefix}-igw`, Author: author },
});

const publicRt = new aws.ec2.RouteTable(`${namePrefix}-public-rt`, {
  vpcId: vpc.id,
  routes: [{ cidrBlock: "0.0.0.0/0", gatewayId: igw.id }],
  tags: { Name: `${namePrefix}-public-rt`, Author: author },
});

publicSubnets.forEach((subnet, i) =>
  new aws.ec2.RouteTableAssociation(`${namePrefix}-public-rta-${i}`, {
    subnetId: subnet.id,
    routeTableId: publicRt.id,
  })
);

// --- Security groups ---
const jenkinsSg = new aws.ec2.SecurityGroup(`${namePrefix}-jenkins-sg`, {
  vpcId: vpc.id,
  description: "Jenkins controller: 8080, 8111, 22",
  ingress: [
    { fromPort: 8080, toPort: 8080, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
    { fromPort: 8111, toPort: 8111, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
    { fromPort: 22, toPort: 22, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
  ],
  egress: [{ fromPort: 0, toPort: 0, protocol: "-1", cidrBlocks: ["0.0.0.0/0"] }],
  tags: { Name: `${namePrefix}-jenkins-sg`, Author: author },
});

const giteaSg = new aws.ec2.SecurityGroup(`${namePrefix}-gitea-sg`, {
  vpcId: vpc.id,
  description: "Gitea: 22, 3000, 80, 443",
  ingress: [
    { fromPort: 22, toPort: 22, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
    { fromPort: 3000, toPort: 3000, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
    { fromPort: 80, toPort: 80, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
    { fromPort: 443, toPort: 443, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
  ],
  egress: [{ fromPort: 0, toPort: 0, protocol: "-1", cidrBlocks: ["0.0.0.0/0"] }],
  tags: { Name: `${namePrefix}-gitea-sg`, Author: author },
});

const posSg = new aws.ec2.SecurityGroup(`${namePrefix}-pos-sg`, {
  vpcId: vpc.id,
  description: "POS deploy target: 22, 8111, 80, 443",
  ingress: [
    { fromPort: 22, toPort: 22, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
    { fromPort: 8111, toPort: 8111, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
    { fromPort: 80, toPort: 80, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
    { fromPort: 443, toPort: 443, protocol: "tcp", cidrBlocks: ["0.0.0.0/0"] },
  ],
  egress: [{ fromPort: 0, toPort: 0, protocol: "-1", cidrBlocks: ["0.0.0.0/0"] }],
  tags: { Name: `${namePrefix}-pos-sg`, Author: author },
});

// --- Instances (public subnet for demo / EC2 Instance Connect) ---
const jenkinsInstance = new aws.ec2.Instance(`${namePrefix}-jenkins-controller`, {
  ami: jenkinsAmiId,
  instanceType: jenkinsInstanceType,
  subnetId: publicSubnets[0].id,
  vpcSecurityGroupIds: [jenkinsSg.id],
  associatePublicIpAddress: true,
  tags: { Name: `${namePrefix}-jenkins-controller`, Author: author },
});

const giteaInstance = new aws.ec2.Instance(`${namePrefix}-gitea`, {
  ami: giteaAmiId,
  instanceType: giteaInstanceType,
  subnetId: publicSubnets[0].id,
  vpcSecurityGroupIds: [giteaSg.id],
  associatePublicIpAddress: true,
  tags: { Name: `${namePrefix}-gitea`, Author: author },
});

const posInstance = new aws.ec2.Instance(`${namePrefix}-pos`, {
  ami: posAmiId,
  instanceType: posInstanceType,
  subnetId: publicSubnets[0].id,
  vpcSecurityGroupIds: [posSg.id],
  associatePublicIpAddress: true,
  tags: { Name: `${namePrefix}-pos`, Author: author },
});

// --- Exports (for Jenkins / scripts / docs) ---
export const vpcId = vpc.id;
export const jenkinsPublicIp = jenkinsInstance.publicIp;
export const jenkinsPrivateIp = jenkinsInstance.privateIp;
export const giteaPublicIp = giteaInstance.publicIp;
export const posPublicIp = posInstance.publicIp;
export const jenkinsUrl = pulumi.interpolate`http://${jenkinsInstance.publicIp}:8080`;
export const giteaUrl = pulumi.interpolate`http://${giteaInstance.publicIp}:3000`;
