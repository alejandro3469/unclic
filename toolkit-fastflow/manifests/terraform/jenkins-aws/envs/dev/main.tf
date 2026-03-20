terraform {
  required_version = ">= 1.5.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

module "network" {
  source = "../../modules/network"

  name_prefix          = var.name_prefix
  vpc_cidr             = var.vpc_cidr
  availability_zones   = var.availability_zones
  public_subnet_count  = var.public_subnet_count
  private_subnet_count = var.private_subnet_count
  author               = var.author
}

module "compute" {
  source = "../../modules/compute"

  name_prefix         = var.name_prefix
  vpc_id              = module.network.vpc_id
  public_subnet_ids   = module.network.public_subnet_ids
  private_subnet_ids  = module.network.private_subnet_ids
  use_public_subnet   = var.jenkins_in_public_subnet

  jenkins_ami_id        = var.jenkins_controller_ami_id
  jenkins_instance_type = var.jenkins_instance_type
  author                = var.author
}

module "autoscaling" {
  source = "../../modules/autoscaling"

  name_prefix           = var.name_prefix
  vpc_id                = module.network.vpc_id
  private_subnet_ids    = module.network.private_subnet_ids
  workers_ami_id        = var.jenkins_worker_ami_id
  workers_instance_type = var.jenkins_worker_instance_type
  desired_capacity      = var.workers_desired_capacity
  min_size              = var.workers_min_size
  max_size              = var.workers_max_size
  author                = var.author
}

module "gitea" {
  source = "../../modules/gitea"

  name_prefix         = var.name_prefix
  vpc_id              = module.network.vpc_id
  public_subnet_ids   = module.network.public_subnet_ids
  gitea_ami_id        = var.gitea_ami_id
  gitea_instance_type = var.gitea_instance_type
  author              = var.author
}

module "registry" {
  count  = var.enable_registry ? 1 : 0
  source = "../../modules/registry"

  name_prefix            = var.name_prefix
  vpc_id                 = module.network.vpc_id
  public_subnet_ids      = module.network.public_subnet_ids
  registry_ami_id        = coalesce(var.registry_ami_id, var.jenkins_controller_ami_id)
  registry_instance_type = var.registry_instance_type
  author                 = var.author
}

module "pos" {
  source = "../../modules/pos"

  name_prefix       = var.name_prefix
  vpc_id            = module.network.vpc_id
  public_subnet_ids = module.network.public_subnet_ids
  pos_ami_id        = var.pos_ami_id
  pos_instance_type = var.pos_instance_type
  author            = var.author
}
