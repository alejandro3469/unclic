# Alternativas “toolchain 2026” (Biome, Bun, Deno, …)

Referencia cruzada con la curación tipo *Top Open Source Projects That Will Dominate 2026* (dev.to) y el plan [PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md](../PLAN-STACK-OSS-CAPAS-ETAPAS-E-INTEGRACION.md) (tabla §curación 2026).

## Posición UnClic **hoy**

- **Next.js + Node 20** en raíz y **`services/api`** (Hono). Cambiar a **Bun/Deno** o **Astro** es un **proyecto de migración**, no un toggle.
- Usa estas piezas como **alternativas** para **otros repos** del mismo cliente o para **PoC** documentadas.

## Cuándo tiene sentido (sin guesswork)

| Herramienta | Cuándo probarla |
|-------------|-----------------|
| **Biome** | Repo nuevo JS/TS; quieres un solo toolchain lint+format más rápido que ESLint+Prettier. |
| **Bun** | Script interno, CLI, o microservicio aislado donde aceptes dependencias nativas del runtime. |
| **Deno** | Edge, permisos estrictos, o equipo ya estandarizado en Deno. |
| **Turso** | Datos por región / offline-first; no sustituye Postgres transaccional fuerte sin diseño. |
| **Astro** | Sitio de **contenido** o docs; UnClic landing puede seguir en Next. |
| **Ruff** | Cualquier carpeta Python (workers, LangGraph). |
| **Zed / Continue** | Productividad **local**; política de qué código puede enviarse a modelos cloud. |

## Pasos si haces PoC Biome en este monorepo (ejemplo)

1. `npm install -D @biomejs/biome` en la raíz UnClic (o solo en `services/api`).
2. `npx @biomejs/biome init` → revisar `biome.json` vs reglas ESLint actuales.
3. Añadir script `lint:biome` en `package.json`; **no** borrar ESLint hasta paridad verificada.
4. Jenkins/CI: etapa `npx @biomejs/biome ci .` cuando esté estable.

Documentación: [biomejs.dev](https://biomejs.dev/).

## Enlaces oficiales rápidos

- Bun: [bun.sh/docs](https://bun.sh/docs)  
- Deno: [docs.deno.com](https://docs.deno.com/)  
- Turso: [docs.turso.tech](https://docs.turso.tech/)  
- Astro: [docs.astro.build](https://docs.astro.build/)  
- Ruff: [docs.astral.sh/ruff](https://docs.astral.sh/ruff/)  
- Zed: [zed.dev/docs](https://zed.dev/docs/)  
- Continue: [docs.continue.dev](https://docs.continue.dev/)
