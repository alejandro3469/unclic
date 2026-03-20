variable "name_prefix" { type = string }
variable "vpc_id" { type = string }
variable "public_subnet_ids" { type = list(string) }
variable "registry_ami_id" { type = string }
variable "registry_instance_type" {
  type    = string
  default = "t3.micro"
}
variable "author" { type = string }
