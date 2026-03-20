# Salidas útiles tras terraform apply: namespace, servicio, comando port-forward para pruebas locales.

output "namespace" {
  description = "Namespace donde se desplegó FastFlow"
  value       = kubernetes_namespace.fastflow.metadata[0].name
}

output "service_name" {
  description = "Nombre del Service para port-forward o Ingress"
  value       = kubernetes_service.fastflow_server.metadata[0].name
}

# Ejemplo para port-forward local:
# kubectl port-forward svc/fastflow-server 3000:3000 -n fastflow
output "port_forward_command" {
  description = "Comando sugerido para port-forward local"
  value       = "kubectl port-forward svc/${kubernetes_service.fastflow_server.metadata[0].name} 3000:3000 -n ${kubernetes_namespace.fastflow.metadata[0].name}"
}
