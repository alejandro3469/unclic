# LinkedIn — perfil pulido (Alejandro Pérez)

**Referencia:** Esto se armó a partir de tu LinkedIn **tal como estaba** (Web developer, CeQuality/TIP con texto duplicado EN/ES, autónomo solo con Upcoming Wedding, portfolio, Open to work, etc.). **Este documento no es una copia del perfil:** es la **versión propuesta** para dejarlo más claro y alineado con POS, DevOps y UnClic.

Objetivos: **menos ruido**, **misma historia** (POS + JDE + DevOps + SaaS), **UnClic visible** como prueba técnica pública.

**Tu URL pública:** `https://www.linkedin.com/in/alejandro-perez-22060b180`  
En UnClic (`.env.local`): `NEXT_PUBLIC_LINKEDIN_URL=https://www.linkedin.com/in/alejandro-perez-22060b180`

**Cómo ordenar el perfil personal** (mapeo tipo página empresa): [GUIA-LINKEDIN-PERSONAL-ESTRUCTURA-SEQUOIA.md](GUIA-LINKEDIN-PERSONAL-ESTRUCTURA-SEQUOIA.md).  
**Página de empresa UnClic** (cuando exista): [PATRON-LINKEDIN-PAGINA-EMPRESA-SEQUOIA.md](PATRON-LINKEDIN-PAGINA-EMPRESA-SEQUOIA.md).

---

## 1. Headline (titular) — opciones

Elige **una** línea (max ~220 caracteres en la práctica):

**A — Enterprise + stack**  
> Full Stack & DevOps | POS retail · JD Edwards · Java/Spring · React | Pipeline as Code · AWS · Docker

**B — Abierto a trabajo + foco**  
> Software Engineer · POS multi-marca (JDE, SAT) · CI/CD Jenkins/Gitea · Open to work

**C — Marca propia**  
> Desarrollo POS enterprise + UnClic (demos Jenkins/Gitea/AWS) | Full Stack · DevOps

Evita solo *“Web developer”* si buscas DevOps/POS: es demasiado genérico para tu experiencia.

---

## 2. Ubicación y “Open to”

- **Open to work:** mantén *Software Engineer, DevOps, Full Stack*; si saturan las alertas, deja 2–3 roles máximo.
- **Website en contacto:** pon **UnClic** (dominio producción) como *Portfolio* o *Other* además del portfolio Vercel — así reclutadores ven pipeline + demos en un clic.

---

## 3. Acerca de (About) — texto listo para pegar (~1.500 caracteres)

```
Desarrollo software end-to-end para retail y operación crítica: integración con ERP (JD Edwards/Oracle), POS multi-marca, facturación alineada a normativa (SAT), y despliegue en Linux con Docker y mensajería (RabbitMQ).

En CeQuality/TIP México lideré el POS retail (React, Java Spring Boot, MySQL) y el sistema de renta de autos (C#, Oracle, Kendo). Mezclo backend fuerte, SQL complejo y criterio de negocio.

En paralelo construyo producto propio: UnClic — hub público de demos (Jenkins, Gitea, registry, arquitectura en AWS/Cloudcraft) orientado a Pipeline as Code y POS FastFlow. También SaaS multi-tenant (Next.js, Supabase, pagos vía Whop).

Busco roles donde aporte arquitectura, integración ERP/POS y/o DevOps. Abierto a remoto y híbrido.
```

(Ajusta “normativa” o ciudad si quieres.)

---

## 4. Experiencia — dejar UNA entrada por empresa (evitar duplicar EN/ES)

### CeQuality / TIP México — un solo puesto

**Título sugerido:** `External IT Consultant` o `Consultor TI · Full Stack`  
**Fechas:** Aug 2024 – Presente

**Descripción (bullets cortos; copia una versión):**

- Lideré **POS multi-marca** retail: **JD Edwards (Oracle)** + **MySQL**, frontend **React**, backend **Java Spring Boot**.
- Implementé lógica de **facturación compatible con SAT** (impuestos, subtotales) sobre modelo de precios genérico.
- **RabbitMQ** (productor/consumidor), generador de folios/órdenes consecutivas, permisos admin/operativo.
- **Docker** (imágenes/registry), **Eureka**, despliegue en **Linux**; análisis de logs en producción.
- SQL avanzado, reportes y optimización de esquemas.
- **Sistema renta de autos:** **C# MVC**, **Oracle**, **Dapper**, **jQuery/Kendo UI**, notificaciones por correo; alineación con equipos SIS.

*(Elimina el segundo bloque duplicado en español si ya tienes lo mismo en inglés — LinkedIn penaliza perfiles con texto repetido.)*

---

### Autónomo — dos bloques claros (opcional: dos “experiencias” o un solo texto con subtítulos)

Si LinkedIn solo permite un puesto autónomo, usa **un** texto con dos secciones:

**Título:** `Full Stack Engineer · Producto propio`  
**Descripción:**

- **UnClic / FastFlow** — Sitio y demos públicas: **Jenkins**, **Gitea**, **registry Docker**, **AWS**, diagrama **Cloudcraft**; Pipeline as Code aplicado a POS y documentación técnica.
- **Upcoming Wedding (SaaS)** — **Next.js**, **React**, **TypeScript**, **Supabase** (PostgreSQL, RLS, Storage); **Whop** (pagos y webhooks); multi-tenant y **RBAC**; deploy **Vercel**.

Así **UnClic deja de ser invisible** frente al proyecto de invitaciones.

---

## 5. Proyectos (Featured)

1. **UnClic** — URL de producción. Texto: *Demos vivas: Jenkins, Gitea, POS, arquitectura cloud.*  
2. **Portfolio** — tu enlace Vercel actual.  
3. Post *Open for business* — puedes dejarlo o sustituirlo por un post corto sobre UnClic.

---

## 6. Skills — priorizar (top 5 visibles)

Orden sugerido para que encajen con búsquedas:  
`Java` · `Spring Boot` · `React.js` · `Docker` · `DevOps` · `Oracle Database` · `MySQL` · `AWS` · `Jenkins` · `Git` · `C#` · `RabbitMQ` · `TypeScript` · `Next.js`

Pide recomendaciones a compañeros de CeQuality en las skills que más te interesan.

---

## 7. Checklist rápido

- [ ] Un solo bloque de texto por rol en CeQuality (quitar duplicado ES/EN).
- [ ] Mencionar **UnClic** en Experiencia o Proyectos.
- [ ] Headline con **POS / JDE / DevOps** o **Pipeline as Code**.
- [ ] Website: **UnClic** + portfolio.
- [ ] About en primera persona y con números/stack concretos.

---

*Documento interno UnClic — no commitear credenciales ni datos sensibles del cliente.*
