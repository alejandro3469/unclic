/// <reference path="./.sst/platform/config.d.ts" />
export default $config({
  app(input) {
    return {
      name: "fastflow-sst-example",
      removal: input?.stage === "production" ? "retain" : "remove",
      home: "aws",
    };
  },
  async run() {
    const bucket = new sst.aws.Bucket("MyBucket", {
      access: "public",
    });

    const fn = new sst.aws.Function("MyFunction", {
      handler: "src/lambda.handler",
      link: [bucket],
    });

    return { bucketName: bucket.name, functionArn: fn.arn };
  },
});
