# Checklist operación producción (sin sorpresas)

Lista **verificable** antes de decir que UnClic (sitio + API) está en producción “seria”. Basada en prácticas estándar; ajusta según SLA del cliente.

## 1. Red y TLS

- [ ] **HTTPS** en front (`https://unclic.consulting` o tu dominio) y en API (`https://api.…`).
- [ ] **Certificados** renovados (Let’s Encrypt con Caddy/Traefik/Certbot o ACM).
- [ ] **HSTS** considerado en proxy (tras validar que todo es HTTPS).
- [ ] **CORS** en API = lista explícita de orígenes de producción (no `*` si usas cookies/credenciales en el futuro).

## 2. Secretos y configuración

- [ ] Ningún secreto en repo; **solo** variables en host, Docker secrets, o vault (OpenBao, SOPS, cloud secret manager).
- [ ] `NEXT_PUBLIC_*` revisado: solo lo que **debe** ser público.
- [ ] Rotación documentada para Gmail app password, Stripe keys, Facturapi.

## 3. Datos y persistencia

- [ ] Si hay **Postgres**: backups automáticos (dump + retención + prueba de restore **al menos una vez**).
- [ ] Si **no** hay DB aún: documentado que webhooks reintentan y puedes perder estado en reinicio (aceptación explícita).

## 4. Observabilidad mínima

- [ ] **Logs** estructurados (JSON o línea clara) hacia stdout; recolección en servidor o `journalctl`.
- [ ] **Healthchecks**: `GET /health` en API (ya existe); monitor externo (Uptime Kuma, blackbox, etc.) opcional pero recomendado.
- [ ] **Métricas** (opcional fase 2): ver [06-METRICAS-PROMETHEUS.md](06-METRICAS-PROMETHEUS.md).

## 5. Despliegue y rollback

- [ ] Imagen o artefacto **versionado** (tag git o semver).
- [ ] Procedimiento de **rollback**: imagen anterior + `out/` anterior conservados.
- [ ] Pipeline (Jenkins/Coolify) **no** imprime secretos en logs.

## 6. Seguridad aplicación

- [ ] **Webhook Stripe** (cuando exista): verificación de firma + idempotencia por `event.id`.
- [ ] **Rate limit** en proxy o API para rutas públicas (login, leads, webhooks abiertos con cuidado).
- [ ] Dependencias: `npm audit` / actualización periódica; criterio de severidad documentado.

## 7. Legal y datos personales (leads, IA)

- [ ] Aviso de privacidad / bases jurídicas si guardas emails (según tu abogado y jurisdicción).
- [ ] Si usas **L19 (LLM)**: política de **qué datos** pueden ir al modelo (ver [07-PROXY-LLM-OLLAMA-DMR.md](07-PROXY-LLM-OLLAMA-DMR.md)).

## 8. Runbook de una página

- [ ] “Si cae la API: pasos 1–5” (reinicio compose, revisar disco, revisar SMTP, revisar CORS).
- [ ] Contacto y acceso a proveedores (Stripe dashboard, Facturapi, DNS).

---

*Completar este checklist no sustituye pentest ni compliance sectorial (salud, PCI full, etc.).*
