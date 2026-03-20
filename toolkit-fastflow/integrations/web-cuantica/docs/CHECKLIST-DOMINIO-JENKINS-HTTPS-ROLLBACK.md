# Checklist: dominio Namecheap + Jenkins en IP pública + HTTPS + rollback por registry

> **Secuencia completa paso a paso (incluye Postman y checklist amplio):** [GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md](GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md).

Guía corta para tu caso: **EC2 `fastflow-jenkins-controller`** (ej. IP pública `18.218.37.76`, región **us-east-2**), **Jenkins en :8080**, dominio **unclic.consulting** en Namecheap.

---

## 1. Lo que ya tienes (bien)

- Instancia en marcha con IP pública.
- **Java 17 (Corretto)** instalado.
- **Jenkins** activo (`systemctl start jenkins`) escuchando en **8080**.

---

## 2. Pasos siguientes (en este orden)

### A. IP estable (muy recomendable)

La IP pública **autoasignada** puede **cambiar** si reinicias la instancia de cierta forma.

1. En **AWS → EC2 → Elastic IPs** → **Allocate**.
2. **Associate** esa Elastic IP con `i-0bd179fb77e42be48`.
3. En **Namecheap** apunta el DNS a **esa** IP (no a una IP que pueda rotar).

Con **EIP** el DNS en Namecheap permanece válido aunque reinicies la instancia.

### B. Namecheap DNS

En **Advanced DNS** del dominio:

| Type | Host | Value | TTL |
|------|------|--------|-----|
| **A** | **jenkins** | **`<TU_ELASTIC_IP_o_IP_actual>`** | Automatic |

Opcional si todo va por el mismo servidor:

| Type | Host | Value |
|------|------|--------|
| **A** | **@** o **gitea** / **registry** / **pos** | misma IP o otras instancias |

Espera propagación (minutos a horas). Prueba: `ping jenkins.unclic.consulting` o `dig jenkins.unclic.consulting`.

### C. Security Group (firewall AWS)

En la instancia Jenkins, el security group debe permitir:

- **Inbound TCP 22** — SSH (idealmente solo **tu IP** / VPN).
- **Inbound TCP 8080** — solo mientras pruebas; mejor **no** dejar Jenkins expuesto en 8080 a `0.0.0.0/0` a largo plazo.
- **Inbound TCP 443** — cuando pongas **Nginx + TLS** delante (recomendado).

### D. HTTPS (recomendado en producción)

No uses `http://jenkins.unclic.consulting:8080` como URL final si puedes evitarlo.

1. Instala **Nginx** en la misma EC2 (o en un ALB; en t3.micro suele ser Nginx en la misma máquina).
2. **Certbot** (Let’s Encrypt) para `jenkins.unclic.consulting`.
3. Nginx: `listen 443 ssl`; `proxy_pass http://127.0.0.1:8080;`.
4. En Jenkins: **Manage Jenkins → System** → **Jenkins URL** = `https://jenkins.unclic.consulting` (sin `:8080`).

Documentación alineada a tu stack: busca en esta carpeta guías **HTTPS** / **JENKINS** (p. ej. `HTTPS-UNCLIC-*.md`, `INSTALAR-JENKINS.md`).

### E. Jenkins “Unlock” y admin

- Contraseña inicial: `sudo cat /var/lib/jenkins/secrets/initialAdminPassword`
- Completa el asistente, instala plugins sugeridos, crea usuario admin.
- **Maven** en el agente: `sudo yum install -y maven` (o el método que uses en tus guías).
- **Git**, **Docker** (si el pipeline construye imagen): según tu guía FastFlow.

### F. Comando que pegaste en la terminal

`http://jenkins.unclic.consulting:8080` **no es un comando de shell**; ábrelo en el **navegador** cuando DNS y security group apunten bien.

---

## 3. Rollback “fácil” con Docker registry

Tu **Jenkinsfile** ya hace push de **dos tags**: `${BUILD_NUMBER}` y **`latest`**.

### Idea

- **Versionado real** = imagen `REGISTRY/pos-online:123` (cada build).
- **`latest`** = último desplegado con éxito (si solo haces deploy del último build).

### Rollback operativo (en el servidor donde corre el POS)

```bash
# Ver tags disponibles (según tu registry; ejemplo genérico)
# docker pull REGISTRY/pos-online:BUILD_ANTERIOR
docker stop pos-online && docker rm pos-online
docker run -d --name pos-online -p 8111:8111 --restart=unless-stopped REGISTRY/pos-online:BUILD_ANTERIOR
```

Sustituye `REGISTRY` y `BUILD_ANTERIOR` (ej. `42`).

### Hacerlo más cómodo en Jenkins (recomendado)

En el repo **pos-online / pos-online-fastflow** ya hay un pipeline listo:

- **Archivo:** `repo-pos-fastflow/Jenkinsfile.rollback`
- **Doc:** `repo-pos-fastflow/ROLLBACK-POS-DOCKER-REGISTRY.md` (comandos manuales, parámetros del job, varias instancias POS)

Crea un segundo job **Pipeline from SCM** con **Script Path** = `Jenkinsfile.rollback` y usa **Build with Parameters** (tag = build anterior, `REGISTRY`, `POS_DEPLOY_HOST`, credencial SSH).

Alternativa: job **Freestyle** parametrizado con un único paso SSH que ejecute `docker pull` + `docker run` como en la sección anterior.

### Buenas prácticas

- No borres tags viejos del registry hasta tener política de retención (o guardar al menos los últimos **10–20** builds).
- Tras un deploy bueno, documenta en un sitio el tag: “producción = `pos-online:57`”.

---

## 4. Resumen “¿qué me falta?”

| Ítem | Estado típico |
|------|----------------|
| EC2 + Jenkins + Java | Hecho |
| **Elastic IP** + DNS **jenkins** | Suele faltar |
| **Security group** (22, 443, 8080 temporal) | Revisar |
| **Nginx + HTTPS** + Jenkins URL en HTTPS | Muy recomendado |
| Maven, Git, Docker en la EC2 | Según pipeline |
| Job Jenkins apuntando a Gitea + `Jenkinsfile` | Configurar |
| **Registry** accesible desde EC2 Jenkins y servidor POS | Variables `REGISTRY` |
| **Rollback** documentado / job parametrizado | Opcional pero útil |

---

## 5. Enlaces útiles en este repo

- Flujo completo Gitea + Jenkins + POS: `REPLICAR-FLUJO-COMPLETO-GITEA-JENKINS-POS-AWS.md`
- Demos y URLs HTTPS (sitio UnClic): `../unclic/docs/DEMOS-PRODUCCION-END-TO-END.md`
- Pipeline POS (tags en registry): `../repo-pos-fastflow/Jenkinsfile` (stage **Push to registry**)
