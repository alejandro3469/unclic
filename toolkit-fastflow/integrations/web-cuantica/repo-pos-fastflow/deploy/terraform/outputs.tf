output "namespace" {
  value = kubernetes_namespace.pos.metadata[0].name
}

output "port_forward_command" {
  value = "kubectl port-forward svc/pos-online 8111:8111 -n ${kubernetes_namespace.pos.metadata[0].name}"
}
