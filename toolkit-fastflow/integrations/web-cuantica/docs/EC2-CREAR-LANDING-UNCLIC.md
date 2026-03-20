# Crear servidor gratis para landing page unclic

Guía para añadir **una instancia EC2** (Free Tier) para la **landing page de unclic** (ej. landing.unclic.consulting o www).

**Para Vantive** (servidor, pipeline, Jenkins): ver la subcompañía **Kings & Joers** en `toolkit-fastflow/integrations/kings-joers/`.

Mismo patrón que Jenkins y Gitea: región **us-east-2**, **t3.micro**, Amazon Linux 2. DNS y Nginx para servir HTML estático.

---

## 1. Resumen

| Servidor | Nombre sugerido (tag) | Puerto(s) | Uso |
|----------|------------------------|-----------|-----|
| Landing unclic | **fastflow-landing-unclic** | 22 (SSH), 80 (HTTP), 443 (HTTPS opcional) | Página de presentación unclic.consulting |

**Free Tier:** t3.micro entra en Free Tier (750 h/mes por cuenta).

---

## 2. AWS Console: crear la EC2

**Dónde:** AWS Console → **EC2** → **Instances** (región **us-east-2**, Ohio).

1. **Launch Instance**
2. **Name:** `fastflow-landing-unclic`
3. **AMI:** Amazon Linux 2 (por defecto)
4. **Instance type:** **t3.micro** (Free tier eligible)
5. **Key pair:** El mismo que usas para Jenkins/Gitea (o crear uno nuevo).
6. **Network settings — Create security group:**
   - **Security group name:** `landing-unclic-sg`
   - **Inbound rules:** SSH (22), **HTTP (80)** from 0.0.0.0/0, **HTTPS (443)** from 0.0.0.0/0 (opcional).
7. **Storage:** 8 GiB gp3 (por defecto).
8. **Launch instance**.

**Anotar** la **Public IPv4 address**. Sin Elastic IP puede cambiar al parar/arrancar.

---

## 3. Namecheap: DNS (opcional)

**Dónde:** Namecheap → **unclic.consulting** → **Manage** → **Advanced DNS** → **HOST RECORDS**.

- **ADD NEW RECORD** → Type **A**, Host **landing**, Value **\<IP de fastflow-landing-unclic\>**, TTL Automatic.

Tras propagar: **http://landing.unclic.consulting** resuelve a tu EC2.

---

## 4. En la EC2: Nginx y contenido

**Conectar:** EC2 → **Connect** → **EC2 Instance Connect**.

```bash
sudo yum install -y nginx
sudo systemctl start nginx
sudo systemctl enable nginx
```

El contenido se sirve desde **/usr/share/nginx/html/**. Sustituye `index.html` con tu landing.

(Opcional: HTTPS con certificado wildcard unclic; ver [HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md](HTTPS-UNCLIC-WILDCARD-TODO-DOMINIO.md).)

---

## 5. Documentos relacionados

- [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md) Fase 1 — mismo patrón para Jenkins y Gitea.
- [INFRAESTRUCTURA-UNCLIC-ACTUAL.md](INFRAESTRUCTURA-UNCLIC-ACTUAL.md) — tabla de instancias y DNS.
- [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) — registros A y subdominios.
