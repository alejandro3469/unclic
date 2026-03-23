# Appwrite — referencia para el plan UnClic (BaaS OSS)

**Qué es:** plataforma **open source** “todo en uno” para backend de aplicaciones: autenticación, bases de datos documentales, almacenamiento de archivos, funciones serverless, tiempo real, mensajería, despliegue de sitios (Appwrite **Sites**), red/regiones en cloud. Modelo **self-hosted** o **Appwrite Cloud** (precios y límites en su web).

**Doc oficial:** [appwrite.io](https://appwrite.io/) · [docs.appwrite.io](https://appwrite.io/docs)

**Por qué entra en el plan UnClic:** encaja con el principio **OSS primero** y con clientes que quieren **acelerar E1–E2** (MVP) sin ensamblar a mano Keycloak + Postgres + S3 + cola + workers. Sirve como **alternativa curada** frente a Firebase/Supabase **cuando** el cliente acepta operar o contratar Appwrite (cloud o VPS propio).

---

## 1. Mapeo a capas del plan (PLAN-STACK)

| Capa UnClic | Cómo lo cubre Appwrite (orientativo) |
|-------------|--------------------------------------|
| **L6** Datos | Databases (documental / modelo Appwrite; no sustituye ERP SQL sin diseño) |
| **L10** Identidad | Auth (email/contraseña, OAuth, magic URL, anónimo, etc.) |
| **L5** Funciones | Functions (runtimes múltiples) |
| **Storage** (entre L3/L6 según uso) | Storage (compresión, transformaciones de imagen en cloud) |
| **L8 / eventos** (parcial) | Realtime + reglas; no reemplaza RabbitMQ/NATS para todos los casos |
| **L13** (parcial) | Messaging (canales unificados; no reemplaza SMTP transaccional completo sin evaluar) |
| **L17** | **Sites** — build/deploy frontend integrado (comparable en rol a hosting Git→build, no sustituye por sí solo tu pipeline Jenkins/Gitea si ese es el requisito) |
| **L19** (IA / agentes) | **MCP**, skills y documentación orientada a conectar agentes/LLM al backend sin integraciones ad hoc — útil como **opción** cuando el producto del cliente incluye agentes; ver [Appwrite docs / AI](https://appwrite.io/docs) y anuncios recientes en su blog. |

---

## 2. Relación con **UnClic tal como está hoy**

| Aspecto | UnClic repo actual | Con Appwrite |
|---------|-------------------|--------------|
| Sitio | Next.js estático + export | Next puede consumir SDK Appwrite o seguir estático con API Appwrite |
| Portal / JWT | `services/api` (Hono), registro/verify/login propios | **Sustitución posible** de la capa auth+usuarios si se migra; requiere diseño y DoD |
| Leads / SMTP | API + Gmail SMTP ([LEADS-CORREO-GMAIL-SMTP.md](LEADS-CORREO-GMAIL-SMTP.md)) | Puede convivir (Appwrite para app cliente; API UnClic para leads) o consolidarse vía Functions |
| Demos Jenkins/Gitea | FastFlow / toolkit | **Independiente**; Appwrite no reemplaza CI/registry salvo que el alcance del proyecto cambie |

**Regla:** Appwrite es **Pendiente / alternativa** en la [matriz granular](PLAN-OSS-ESTADO-E-EJECUCION-GRANULAR-UNClic.md) hasta que un cliente o producto interno tenga **decisión explícita** y PoC con criterios de salida (coste cloud vs VPS, lock-in, backup, compliance).

---

## 3. Cuándo proponerlo en una propuesta

- **Sí:** MVP móvil/web con auth + datos + archivos en semanas; equipo pequeño; cliente abierto a BaaS OSS; necesidad de **Sites** + auth unificados; roadmap con **agentes** y quieren backend ya modelado para MCP/skills.
- **No por defecto:** retail/POS/JDE/Oracle donde ya mandan **pipeline Jenkins**, **Gitea**, **generic-model** y entrega UnClic documentada — ahí el núcleo sigue siendo tu stack actual salvo fase de “producto satélite”.

---

## 4. Próximo paso atómico (si se adopta)

1. PoC: proyecto Appwrite Cloud **o** Docker self-host en VPS de prueba.  
2. Una pantalla Next (ruta bajo `/labs` o demo) con login Appwrite + lectura de una colección.  
3. Documentar en esta misma nota: URL, versión, política de datos y comparación de coste vs API propia.

---

## 5. Enlaces útiles (homepage / producto)

- Producto y precios: [appwrite.io](https://appwrite.io/)  
- Quickstarts (Next.js, etc.): sección **QUICK STARTS** en el sitio  
- Seguridad / compliance (referencia comercial): páginas **Security**, **GDPR**, **SOC-2**, etc. — validar con necesidad real del cliente antes de prometer cumplimiento.

*Este archivo no sustituye la documentación oficial de Appwrite; sirve para alinear discurso UnClic con el [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md).*
