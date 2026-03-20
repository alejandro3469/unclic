resource "aws_security_group" "jenkins" {
  name   = "${var.name_prefix}-jenkins-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  # Puerto app POS (misma EC2: commit → pipeline → Deploy levanta app aquí)
  ingress {
    from_port   = 8111
    to_port     = 8111
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 22
    to_port     = 22
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

resource "aws_instance" "jenkins" {
  ami                         = var.jenkins_ami_id
  instance_type               = var.jenkins_instance_type
  subnet_id                   = var.use_public_subnet ? element(var.public_subnet_ids, 0) : element(var.private_subnet_ids, 0)
  vpc_security_group_ids      = [aws_security_group.jenkins.id]
  associate_public_ip_address = var.use_public_subnet
  tags = {
    Name   = "${var.name_prefix}-jenkins-controller"
    Author = var.author
  }
}

output "jenkins_lb_dns" {
  description = "IP privada del controller (o pública si use_public_subnet)"
  value       = aws_instance.jenkins.private_ip
}
output "jenkins_public_ip" {
  description = "IP pública (solo si use_public_subnet); acceso http://<esta_ip>:8080"
  value       = try(aws_instance.jenkins.public_ip, null)
}
