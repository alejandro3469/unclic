# Gitea: EC2 para repos (puerto 3000); SSH 22, HTTP 80/443 para Nginx proxy.
resource "aws_security_group" "gitea" {
  name   = "${var.name_prefix}-gitea-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 3000
    to_port     = 3000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 443
    to_port     = 443
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

resource "aws_instance" "gitea" {
  ami                         = var.gitea_ami_id
  instance_type               = var.gitea_instance_type
  subnet_id                   = element(var.public_subnet_ids, 0)
  vpc_security_group_ids      = [aws_security_group.gitea.id]
  associate_public_ip_address = true
  tags = {
    Name   = "${var.name_prefix}-gitea"
    Author = var.author
  }
}
