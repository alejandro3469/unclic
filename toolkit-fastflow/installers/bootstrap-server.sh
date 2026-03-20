#!/usr/bin/env bash
set -euo pipefail

# Bootstrap portable Linux para Fast Flow Toolkit
# Uso:
#   ./bootstrap-server.sh core   # Jenkins + Java + Maven + Docker
#   ./bootstrap-server.sh k8s    # core + kubectl + helm
#   ./bootstrap-server.sh full   # k8s + terraform + kubeadm stack base

PROFILE="${1:-core}"

if [[ "$PROFILE" != "core" && "$PROFILE" != "k8s" && "$PROFILE" != "full" ]]; then
  echo "[ERROR] Perfil inválido: $PROFILE"
  echo "Perfiles válidos: core | k8s | full"
  exit 1
fi

detect_os_family() {
  if [[ -f /etc/os-release ]]; then
    . /etc/os-release
    case "${ID:-}" in
      ubuntu|debian) echo "debian" ;;
      amzn|rhel|centos|rocky|almalinux|fedora) echo "rpm" ;;
      *) echo "unknown" ;;
    esac
  else
    echo "unknown"
  fi
}

OS_FAMILY="$(detect_os_family)"
echo "[INFO] Perfil seleccionado: $PROFILE"
echo "[INFO] Familia Linux detectada: $OS_FAMILY"

install_core_debian() {
  sudo apt-get update
  sudo apt-get install -y git curl unzip jq ca-certificates gnupg lsb-release fontconfig
  # Java 17 para runtime de Jenkins moderno
  sudo apt-get install -y openjdk-17-jre
  # Java 11 para builds Java 11 (Spring Boot 2.x / legacy lane)
  sudo apt-get install -y openjdk-11-jdk
  # Maven
  sudo apt-get install -y maven

  # Docker Engine + Compose plugin (repo oficial Docker para Ubuntu/Debian)
  sudo install -m 0755 -d /etc/apt/keyrings
  sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
  sudo chmod a+r /etc/apt/keyrings/docker.asc
  sudo tee /etc/apt/sources.list.d/docker.sources >/dev/null <<EOF
Types: deb
URIs: https://download.docker.com/linux/ubuntu
Suites: $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}")
Components: stable
Signed-By: /etc/apt/keyrings/docker.asc
EOF
  sudo apt-get update
  sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
  sudo systemctl enable --now docker
}

install_core_rpm() {
  if grep -qi "Amazon Linux" /etc/os-release; then
    # Amazon Linux 2023 / AL2 (según documentación AWS Corretto y AL package manager)
    sudo dnf -y update || sudo yum -y update
    sudo dnf -y install git curl unzip jq || sudo yum -y install git curl unzip jq
    sudo dnf -y install java-17-amazon-corretto-headless || sudo yum -y install java-17-amazon-corretto-headless
    sudo dnf -y install java-11-amazon-corretto-devel || sudo yum -y install java-11-amazon-corretto-devel
    sudo dnf -y install maven || sudo yum -y install maven
    sudo dnf -y install docker || sudo yum -y install docker
    sudo systemctl enable --now docker
  else
    sudo dnf -y update || sudo yum -y update
    sudo dnf -y install git curl unzip jq fontconfig || sudo yum -y install git curl unzip jq fontconfig
    sudo dnf -y install java-17-openjdk java-11-openjdk-devel maven || sudo yum -y install java-17-openjdk java-11-openjdk-devel maven

    # Docker CE en RPM family (RHEL-like)
    sudo dnf -y install dnf-plugins-core || true
    sudo dnf config-manager --add-repo https://download.docker.com/linux/rhel/docker-ce.repo || true
    sudo dnf -y install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin \
      || sudo yum -y install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    sudo systemctl enable --now docker
  fi
}

install_k8s_tools_debian() {
  # kubectl desde repo Kubernetes pkgs.k8s.io
  sudo apt-get install -y apt-transport-https
  sudo mkdir -p /etc/apt/keyrings
  curl -fsSL https://pkgs.k8s.io/core:/stable:/v1.35/deb/Release.key | sudo gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg
  echo 'deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://pkgs.k8s.io/core:/stable:/v1.35/deb/ /' | sudo tee /etc/apt/sources.list.d/kubernetes.list
  sudo apt-get update
  sudo apt-get install -y kubectl

  # Helm (script oficial)
  curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
  chmod 700 get_helm.sh
  ./get_helm.sh
}

install_k8s_tools_rpm() {
  cat <<EOF | sudo tee /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://pkgs.k8s.io/core:/stable:/v1.35/rpm/
enabled=1
gpgcheck=1
gpgkey=https://pkgs.k8s.io/core:/stable:/v1.35/rpm/repodata/repomd.xml.key
exclude=kubelet kubeadm kubectl cri-tools kubernetes-cni
EOF
  sudo yum install -y kubectl --disableexcludes=kubernetes || sudo dnf install -y kubectl --disableexcludes=kubernetes

  curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
  chmod 700 get_helm.sh
  ./get_helm.sh
}

install_full_extras_debian() {
  # Terraform (repo oficial HashiCorp)
  wget -O - https://apt.releases.hashicorp.com/gpg | sudo gpg --dearmor -o /usr/share/keyrings/hashicorp-archive-keyring.gpg
  echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(grep -oP '(?<=UBUNTU_CODENAME=).*' /etc/os-release || lsb_release -cs) main" \
    | sudo tee /etc/apt/sources.list.d/hashicorp.list
  sudo apt-get update && sudo apt-get install -y terraform

  # kubeadm/kubelet (opcional en server único; útil para labs/POC)
  sudo apt-get install -y kubelet kubeadm kubectl
  sudo apt-mark hold kubelet kubeadm kubectl
}

install_full_extras_rpm() {
  # Terraform por paquete (HashiCorp)
  if command -v dnf >/dev/null 2>&1; then
    sudo dnf -y install terraform || true
  else
    sudo yum -y install terraform || true
  fi

  sudo yum install -y kubelet kubeadm kubectl --disableexcludes=kubernetes || true
}

echo "[INFO] Instalando perfil core..."
case "$OS_FAMILY" in
  debian) install_core_debian ;;
  rpm) install_core_rpm ;;
  *)
    echo "[WARN] No se detectó familia soportada automáticamente."
    echo "[WARN] Instala manualmente: Java17 runtime, Java11 build JDK, Maven, Docker Engine, Docker Compose."
    ;;
esac

if [[ "$PROFILE" == "k8s" || "$PROFILE" == "full" ]]; then
  echo "[INFO] Instalando herramientas k8s (kubectl + helm)..."
  case "$OS_FAMILY" in
    debian) install_k8s_tools_debian ;;
    rpm) install_k8s_tools_rpm ;;
  esac
fi

if [[ "$PROFILE" == "full" ]]; then
  echo "[INFO] Instalando extras full (terraform + kubeadm/kubelet)..."
  case "$OS_FAMILY" in
    debian) install_full_extras_debian ;;
    rpm) install_full_extras_rpm ;;
  esac
fi

echo ""
echo "[OK] Bootstrap completado para perfil: $PROFILE"
echo "[NEXT] Validar herramientas:"
echo "  java -version"
echo "  mvn -v"
echo "  docker --version && docker compose version"
echo "  kubectl version --client (si aplica)"
echo "  helm version (si aplica)"
echo "  terraform -version (si aplica)"
echo ""
echo "[IMPORTANT] Jenkins moderno debe correr sobre Java 17+."
echo "[IMPORTANT] Tus builds Java 11 se mantienen configurando JDK toolchain en Jenkins jobs/pipeline."
