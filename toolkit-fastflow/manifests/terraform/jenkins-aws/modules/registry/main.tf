# Registry Docker (puerto 5000). Configurar insecure-registries en Jenkins/daemon si no usas TLS.
resource "aws_security_group" "registry" {
  name   = "${var.name_prefix}-registry-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 5000
    to_port     = 5000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "registry" {
  ami                         = var.registry_ami_id
  instance_type               = var.registry_instance_type
  subnet_id                   = element(var.public_subnet_ids, 0)
  vpc_security_group_ids      = [aws_security_group.registry.id]
  associate_public_ip_address = true
  tags = {
    Name   = "${var.name_prefix}-registry"
    Author = var.author
  }
}
