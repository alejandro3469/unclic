variable "name_prefix" { type = string }
variable "vpc_id" { type = string }
variable "public_subnet_ids" { type = list(string) }
variable "gitea_ami_id" { type = string }
variable "gitea_instance_type" {
  type    = string
  default = "t3.micro"
}
variable "author" { type = string }
