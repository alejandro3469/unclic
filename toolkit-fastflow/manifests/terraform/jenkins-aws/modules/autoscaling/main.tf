resource "aws_security_group" "workers" {
  name   = "${var.name_prefix}-workers-sg"
  vpc_id = var.vpc_id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_launch_template" "workers" {
  name_prefix   = "${var.name_prefix}-workers-"
  image_id      = var.workers_ami_id
  instance_type = var.workers_instance_type

  vpc_security_group_ids = [aws_security_group.workers.id]

  tag_specifications {
    resource_type = "instance"
    tags = {
      Name   = "${var.name_prefix}-jenkins-worker"
      Author = var.author
    }
  }
}

resource "aws_autoscaling_group" "workers" {
  name                = "${var.name_prefix}-workers-asg"
  desired_capacity    = var.desired_capacity
  max_size            = var.max_size
  min_size            = var.min_size
  vpc_zone_identifier = var.private_subnet_ids

  launch_template {
    id      = aws_launch_template.workers.id
    version = "$Latest"
  }
}

output "workers_asg_name" {
  value = aws_autoscaling_group.workers.name
}
