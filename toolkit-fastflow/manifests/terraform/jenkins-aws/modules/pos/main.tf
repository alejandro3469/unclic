# POS en EC2: Jenkins deploy levanta app aquí (Docker). SSH 22, app 8111, Nginx/Certbot 80/443.
resource "aws_security_group" "pos" {
  name   = "${var.name_prefix}-pos-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 8111
    to_port     = 8111
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

resource "aws_instance" "pos" {
  ami                         = var.pos_ami_id
  instance_type               = var.pos_instance_type
  subnet_id                   = element(var.public_subnet_ids, 0)
  vpc_security_group_ids      = [aws_security_group.pos.id]
  associate_public_ip_address = true
  tags = {
    Name   = "${var.name_prefix}-pos"
    Author = var.author
  }
}
