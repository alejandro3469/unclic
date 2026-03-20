# Terraform: despliegue POS Online en cluster Kubernetes existente.
# Requiere: cluster creado y kubeconfig. Imagen: Jenkins build → push registry → aquí.

terraform {
  required_version = ">= 1.0"
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.23"
    }
  }
}

provider "kubernetes" {}

resource "kubernetes_namespace" "pos" {
  metadata {
    name   = var.namespace
    labels = { "app.kubernetes.io/name" = "pos-online" }
  }
}

resource "kubernetes_deployment" "pos" {
  metadata {
    name      = "pos-online"
    namespace = kubernetes_namespace.pos.metadata[0].name
    labels    = { app = "pos-online" }
  }
  spec {
    replicas = 1
    selector { match_labels = { app = "pos-online" } }
    template {
      metadata { labels = { app = "pos-online" } }
      spec {
        container {
          name             = "pos-online"
          image            = var.pos_image
          image_pull_policy = "IfNotPresent"
          port { container_port = 8111; name = "http" }
          liveness_probe {
            http_get { path = "/health"; port = 8111 }
            initial_delay_seconds = 15
            period_seconds        = 20
          }
          readiness_probe {
            http_get { path = "/health"; port = 8111 }
            initial_delay_seconds = 5
            period_seconds        = 10
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "pos" {
  metadata {
    name      = "pos-online"
    namespace = kubernetes_namespace.pos.metadata[0].name
    labels    = { app = "pos-online" }
  }
  spec {
    type = "ClusterIP"
    port {
      port        = 8111
      target_port = 8111
      protocol    = "TCP"
      name        = "http"
    }
    selector = { app = "pos-online" }
  }
}
