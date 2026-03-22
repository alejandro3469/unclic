# URLs y EC2 — patrones para probar

Documento **genérico** (sin IPs, IDs de cuenta ni instancias reales). Sustituye los placeholders por los valores de **tu** despliegue. DNS: [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) (adapta dominio y registrador).

---

## Placeholders

| Placeholder | Significado |
|-------------|-------------|
| `<IP_JENKINS>` | IPv4 pública de la EC2 donde corre Jenkins (y a menudo el POS en 8111) |
| `<IP_GITEA>` | IPv4 pública de la EC2 Gitea |
| `<IP_POS>` | IPv4 pública si el POS va en **otra** EC2 dedicada |
| `<TU_DOMINIO>` | Tu dominio (ej. `empresa.example`) |
| `TU_USUARIO` | Usuario dueño de los repos en Gitea |

---

## URLs típicas

### Jenkins

- `http://<IP_JENKINS>:8080`
- Si usas DNS: `http://jenkins.<TU_DOMINIO>:8080`
- Con HTTPS delante de Nginx: `https://jenkins.<TU_DOMINIO>/`

Contraseña inicial: en la EC2, `sudo cat /var/lib/jenkins/secrets/initialAdminPassword`. Ver [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md).

### App POS

- Misma EC2 que Jenkins: `http://<IP_JENKINS>:8111` (ej. `/health` o `/actuator/health` según el proyecto)
- EC2 dedicada: `http://<IP_POS>:8111`

Abre el puerto **8111** en el **security group** de esa instancia.

### Gitea

- `http://<IP_GITEA>:3000`
- Con DNS: `http://gitea.<TU_DOMINIO>:3000` (o HTTPS si lo configuraste)

Instalación: [INSTALAR-GITEA-SELF-HOSTED.md](INSTALAR-GITEA-SELF-HOSTED.md).

---

## Consola EC2 (referencia)

**Ruta:** AWS Console → **EC2** → **Instances**. Elige la región correcta en la barra superior.

**Columnas habituales:** Name, Instance ID, State, Instance type, Public IPv4, Security groups, etc.

**Conectar:** instancia → **Connect** → EC2 Instance Connect o SSH: `ssh -i tu-clave.pem ec2-user@<IP_PUBLICA>`.

**Security groups:** edita **inbound rules** para 22 (SSH), 8080 (Jenkins), 3000 (Gitea), 8111 (POS) según necesites. Restringe el origen en producción.

---

## Comprobar que es el Jenkins de la EC2

En **Manage Jenkins → Nodes**, el nodo integrado debe ser **Linux**. Si ves **Mac OS X**, estás en un Jenkins local: usa la URL con **`<IP_JENKINS>`** y revisa túneles SSH o `/etc/hosts`.

---

## Documentos relacionados

- [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) — plantilla para anotar tus valores
- [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md)
- [50-tecnologias/AWS-EC2.md](../50-tecnologias/AWS-EC2.md) — cuántas instancias crear
- [PASO-A-PASO-MINIMO-HOY.md](PASO-A-PASO-MINIMO-HOY.md)
