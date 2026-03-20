packer {
  required_plugins {
    amazon = {
      version = ">= 1.3.0"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

variable "aws_region" { type = string }
variable "instance_type" { type = string  default = "t3.small" }
variable "source_ami" { type = string }
variable "ami_name_prefix" { type = string default = "fastflow-jenkins-agent" }

source "amazon-ebs" "agent" {
  region        = var.aws_region
  instance_type = var.instance_type
  source_ami    = var.source_ami
  ssh_username  = "ec2-user"
  ami_name      = "${var.ami_name_prefix}-${formatdate("YYYYMMDD-hhmmss", timestamp())}"
}

build {
  name    = "jenkins-agent"
  sources = ["source.amazon-ebs.agent"]

  provisioner "shell" {
    script = "scripts/agent-setup.sh"
  }
}
