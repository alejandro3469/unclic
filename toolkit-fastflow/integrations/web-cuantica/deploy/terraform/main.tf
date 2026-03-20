# ------------------------------------------------------------------------------
# Terraform: despliegue de FastFlow en un cluster Kubernetes existente
# ------------------------------------------------------------------------------
# Requiere: cluster ya creado y kubeconfig (o KUBECONFIG) apuntando a él.
# El cluster (EKS, GKE, minikube, kind) se puede provisionar aparte (ver manifests/terraform/jenkins-aws).
# Uso: terraform init && terraform plan -var="fastflow_image=mi-registry/fastflow-server:latest"
#      terraform apply (con las variables en variables.tf o .tfvars).
# ------------------------------------------------------------------------------

terraform {
  required_version = ">= 1.0"
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.23"
    }
  }
}

provider "kubernetes" {
  # Usa KUBECONFIG o ~/.kube/config por defecto
  # Para EKS: config_path = "~/.kube/config" o config_context
}

resource "kubernetes_namespace" "fastflow" {
  metadata {
    name   = var.namespace
    labels = {
      "app.kubernetes.io/name" = "fastflow"
    }
  }
}

resource "kubernetes_config_map" "fastflow_config" {
  metadata {
    name      = "fastflow-config"
    namespace = kubernetes_namespace.fastflow.metadata[0].name
    labels    = { app = "fastflow-server" }
  }
  data = {
    NODE_ENV  = "production"
    BASE_URL  = var.base_url
  }
}

resource "kubernetes_deployment" "fastflow_server" {
  metadata {
    name      = "fastflow-server"
    namespace = kubernetes_namespace.fastflow.metadata[0].name
    labels    = { app = "fastflow-server" }
  }
  spec {
    replicas = 1
    selector {
      match_labels = { app = "fastflow-server" }
    }
    template {
      metadata {
        labels = { app = "fastflow-server" }
      }
      spec {
        container {
          name             = "fastflow-server"
          image            = var.fastflow_image
          image_pull_policy = "IfNotPresent"
          port {
            container_port = 3000
            name           = "http"
          }
          env_from {
            config_map_ref {
              name = kubernetes_config_map.fastflow_config.metadata[0].name
            }
          }
          resources {
            requests = {
              memory = "128Mi"
              cpu    = "100m"
            }
            limits = {
              memory = "256Mi"
              cpu    = "500m"
            }
          }
          liveness_probe {
            http_get {
              path = "/api/health"
              port = 3000
            }
            initial_delay_seconds = 10
            period_seconds        = 15
          }
          readiness_probe {
            http_get {
              path = "/api/health"
              port = 3000
            }
            initial_delay_seconds = 5
            period_seconds        = 10
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "fastflow_server" {
  metadata {
    name      = "fastflow-server"
    namespace = kubernetes_namespace.fastflow.metadata[0].name
    labels    = { app = "fastflow-server" }
  }
  spec {
    type = "ClusterIP"
    port {
      port        = 3000
      target_port = 3000
      protocol    = "TCP"
      name        = "http"
    }
    selector = { app = "fastflow-server" }
  }
}
