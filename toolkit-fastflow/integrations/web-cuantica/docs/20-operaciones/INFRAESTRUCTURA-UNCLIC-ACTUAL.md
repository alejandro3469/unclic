# Plantilla de infraestructura (dominio + Jenkins + Gitea)

Documento **genérico** para anotar **tu** despliegue: dominio, DNS, EC2 y URLs. **No contiene datos reales** — rellénalo en tu copia interna si lo usas como registro operativo.

**Replicar desde cero:** [REPLICAR-UNCLIC-COMPLETO.md](REPLICAR-UNCLIC-COMPLETO.md). **Índice:** [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md).

---

## 1. Resumen (rellenar)

| Componente | Dónde se configura | Valor / URL (placeholder) |
|------------|--------------------|---------------------------|
| **Dominio** | Tu registrador | `tu-dominio.example` |
| **DNS** | Registrador → DNS → registros A | Ver §2 |
| **Jenkins** | EC2 | `https://jenkins.tu-dominio.example` y/o `http://<IP_JENKINS>:8080` |
| **Gitea** | EC2 | `http://gitea.tu-dominio.example:3000` o `http://<IP_GITEA>:3000` |
| **EC2 Jenkins** | AWS Console (ej. us-east-2) | Nombre: `fastflow-jenkins-controller`, IP: `<IP_JENKINS>` |
| **EC2 Gitea** | Misma región | Nombre: `fastflow-gitea`, IP: `<IP_GITEA>` |

**POS / pipeline:** suele desplegarse en la misma EC2 que Jenkins (puerto **8111**) o en una **tercera** EC2; ver [REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md](REPLICAR-POS-ONLINE-FASTFLOW-COMPLETO.md) y [50-tecnologias/AWS-EC2.md](../50-tecnologias/AWS-EC2.md).

---

## 2. DNS (ejemplo de registros)

**Dónde:** tu registrador (Namecheap, Route53, etc.) → zona DNS del dominio.

| Type | Host | Value | Uso |
|------|------|--------|-----|
| A | `jenkins` | `<IP_JENKINS>` | Jenkins |
| A | `gitea` | `<IP_GITEA>` | Gitea |

Añade **MX**, **TXT** (SPF/DKIM), **CNAME** según correo y verificaciones; no los borres al editar A.

---

## 3. AWS EC2

**Dónde:** AWS Console → **EC2** → **Instances** (la región que uses, ej. `us-east-2`).

| Nombre (tag) | Instance ID (ejemplo) | IP pública | Tipo | Puertos típicos |
|--------------|------------------------|------------|------|------------------|
| `fastflow-jenkins-controller` | `i-0abc123456789abcd` | `<IP_JENKINS>` | t3.micro | 22, 8080, 443 (si Nginx) |
| `fastflow-gitea` | `i-0def987654321fedc` | `<IP_GITEA>` | t3.micro | 22, 3000 |

- **Security groups:** abre solo lo necesario; **22** restrictivo (tu IP/VPN), **8080/3000** según política (ideal: IP fija o VPN, no `0.0.0.0/0` en producción).
- Sin **Elastic IP**, al reiniciar la instancia puede cambiar la IP → actualiza los registros A.

---

## 4. Servicios y URLs

| Servicio | URL (patrón) | Configuración |
|----------|----------------|---------------|
| **Jenkins** | `https://jenkins.tu-dominio.example` o `:8080` | Jenkins → System → Jenkins URL; Nginx si usas 443 |
| **Gitea** | `http://gitea.tu-dominio.example:3000` | `app.ini` ROOT_URL / asistente inicial |
| **App POS** (si aplica) | `http://<IP_JENKINS>:8111` | Job Jenkins / contenedor en EC2 Jenkins |

HTTPS: [HTTPS-UNCLIC-GITEA-JENKINS.md](HTTPS-UNCLIC-GITEA-JENKINS.md), [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md) (sustituye dominio y IPs por los tuyos).

---

## 5. Correo (opcional)

Si usas **Google Workspace**, **Microsoft 365** u otro proveedor, los registros **MX** y **TXT** (SPF/DKIM) viven en el DNS del dominio; las cuentas se administran en la consola del proveedor. **No** publiques en este repo direcciones personales reales.

---

## 6. Qué suele ir en cada EC2 (referencia)

### EC2 Jenkins

- **SO:** Amazon Linux 2023 (recomendado) o AL2 (planificar migración).
- **Jenkins**, **Java 17**, **Maven**, **Git**; usuario `jenkins` con `mvn` en PATH.
- **Swap** en t3.micro: [EC2-SWAP-T3MICRO.md](EC2-SWAP-T3MICRO.md).

### EC2 Gitea

- **Gitea** en 3000 (Docker o binario).
- Repos típicos: `TU_USUARIO/pos-online`, `TU_USUARIO/smartbussiness-generic-model` (nombres a tu elección).

---

## 7. Dónde cambiar qué

| Objetivo | Dónde |
|----------|--------|
| Cambiar IP de `jenkins.*` / `gitea.*` | DNS → editar registros A |
| Arrancar / parar EC2 | AWS → EC2 → Instances |
| Reglas de firewall | EC2 → Security Groups |
| URL en Jenkins | Manage Jenkins → System |
| URL en Gitea | `app.ini` |

---

## 8. Comprobar que es el Jenkins de la EC2 (no el local)

En **Manage Jenkins → Nodes**, el **Built-In Node** debe ser **Linux**. Si ves **Mac OS X**, estás en un Jenkins local.

1. Abre `http://<IP_JENKINS>:8080` (IP pública de la EC2).
2. Verifica **Linux** en Nodes.
3. Evita túneles SSH `-R` que reenvíen 8080 a tu máquina y entradas en `/etc/hosts` que apunten el hostname del Jenkins a `127.0.0.1`.

---

## 9. Documentos relacionados

- [REPLICAR-ESTADO-ACTUAL-INDICE.md](REPLICAR-ESTADO-ACTUAL-INDICE.md)
- [REPOS-LOCALES-Y-GITEA.md](REPOS-LOCALES-Y-GITEA.md)
- [DOMINIO-NAMECHEAP-UNCLIC-EC2.md](DOMINIO-NAMECHEAP-UNCLIC-EC2.md)
- [EC2-CREAR-LANDING-UNCLIC.md](EC2-CREAR-LANDING-UNCLIC.md)
- [README.md](../README.md)
