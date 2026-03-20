variable "name_prefix" { type = string }
variable "vpc_id" { type = string }
variable "private_subnet_ids" { type = list(string) }
variable "workers_ami_id" { type = string }
variable "workers_instance_type" { type = string }
variable "author" { type = string }

# Free tier: usar desired_capacity = 0, min_size = 0, max_size = 0 para no levantar workers
variable "desired_capacity" {
  type    = number
  default = 2
}
variable "min_size" {
  type    = number
  default = 2
}
variable "max_size" {
  type    = number
  default = 6
}
