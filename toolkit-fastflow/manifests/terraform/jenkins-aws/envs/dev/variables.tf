variable "aws_region" { type = string }
variable "name_prefix" {
  type    = string
  default = "fastflow"
}
variable "author" {
  type    = string
  default = "fastflow"
}

variable "vpc_cidr" {
  type    = string
  default = "10.0.0.0/16"
}
variable "availability_zones" { type = list(string) }
variable "public_subnet_count" {
  type    = number
  default = 2
}
variable "private_subnet_count" {
  type    = number
  default = 2
}

variable "jenkins_controller_ami_id" { type = string }
variable "jenkins_worker_ami_id" { type = string }
variable "jenkins_instance_type" {
  type    = string
  default = "t3.medium"
}
variable "jenkins_worker_instance_type" {
  type    = string
  default = "t3.small"
}
# Free tier: poner true para demo sin bastion (Jenkins en subred pública con IP pública)
variable "jenkins_in_public_subnet" {
  type    = bool
  default = false
}
# Free tier: poner 0,0,0 para no levantar workers (solo 1 instancia Jenkins)
variable "workers_desired_capacity" {
  type    = number
  default = 2
}
variable "workers_min_size" {
  type    = number
  default = 2
}
variable "workers_max_size" {
  type    = number
  default = 6
}

# Gitea (EC2)
variable "gitea_ami_id" { type = string }
variable "gitea_instance_type" {
  type    = string
  default = "t3.micro"
}

# Registry (opcional)
variable "enable_registry" {
  type    = bool
  default = false
}
variable "registry_ami_id" {
  type    = string
  default = ""
}
variable "registry_instance_type" {
  type    = string
  default = "t3.micro"
}

# POS (EC2 para deploy desde Jenkins)
variable "pos_ami_id" { type = string }
variable "pos_instance_type" {
  type    = string
  default = "t3.micro"
}
