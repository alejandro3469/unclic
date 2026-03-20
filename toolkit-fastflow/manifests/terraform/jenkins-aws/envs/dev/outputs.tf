output "vpc_id" {
  value = module.network.vpc_id
}

output "jenkins_lb_dns" {
  value = module.compute.jenkins_lb_dns
}
output "jenkins_public_ip" {
  value = module.compute.jenkins_public_ip
}

output "workers_asg_name" {
  value = module.autoscaling.workers_asg_name
}

output "gitea_public_ip" {
  value = module.gitea.gitea_public_ip
}

output "registry_public_ip" {
  value       = var.enable_registry ? module.registry[0].registry_public_ip : null
  description = "Solo si enable_registry = true"
}

output "pos_public_ip" {
  value = module.pos.pos_public_ip
}
