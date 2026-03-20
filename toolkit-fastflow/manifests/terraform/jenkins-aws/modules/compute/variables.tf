variable "name_prefix" { type = string }
variable "vpc_id" { type = string }
variable "public_subnet_ids" { type = list(string) }
variable "private_subnet_ids" { type = list(string) }
# Si true, Jenkins se lanza en subred pública (IP pública, solo para demo/Free Tier).
variable "use_public_subnet" {
  type    = bool
  default = false
}
variable "jenkins_ami_id" { type = string }
variable "jenkins_instance_type" { type = string }
variable "author" { type = string }
