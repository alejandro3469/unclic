# Guía cronológica — un solo implementador

Este archivo solo responde: **“¿en qué orden pienso las piezas?”**  
El **procedimiento** (comandos, pantallas, checklist) está en la **[guía única](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)** — no lo dupliques leyendo esto al detalle.

---

## TL;DR

1. Cuenta AWS + IAM + CLI si hace falta → [PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md](PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md)  
2. *(Opcional)* Contexto “por qué” → [GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md](GUIA-USUARIO-FINAL-PORQUE-Y-COMO.md)  
3. **Ejecutar** la guía única del índice al §12 según tu dominio → misma guía única  
4. Postman + rollback cuando la guía lo indique → [../postman/README.md](../postman/README.md), `repo-pos-fastflow/`

---

## Orden lógico (referencia rápida)

| Fase | Contenido | Enlace |
|------|-----------|--------|
| Cuenta / costes | IAM, CLI, Billing | [PRIMEROS-PASOS…](PRIMEROS-PASOS-CUENTA-AWS-NUEVA.md), [../90-archivo/AWS-BILLING-Y-CUENTA-CONSOLA.md](../90-archivo/AWS-BILLING-Y-CUENTA-CONSOLA.md) |
| Infra | EC2, SG, SSH | [../50-tecnologias/AWS-EC2.md](../50-tecnologias/AWS-EC2.md) |
| DNS | Namecheap, registros | [../50-tecnologias/NAMECHEAP-DNS.md](../50-tecnologias/NAMECHEAP-DNS.md), [../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md) |
| Dependencia Java | generic-model | [../20-operaciones/DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md](../20-operaciones/DEPENDENCIA-GENERIC-MODEL-Y-CONTEXTO.md) |
| **Hilo conductor** | Todo el happy path | **[GUIA-UNICA…](../10-guia-unica/GUIA-UNICA-COMMIT-JENKINS-POSTMAN-UNCLIC.md)** |
| HTTPS / extras | Cuando la guía única lo pida | `20-operaciones/HTTPS-*`, `PASO-1-GUIA-HTTPS*` (listado en [../README.md](../README.md)) |

**Fichas por herramienta** (solo si atascas en un detalle): [../50-tecnologias/README.md](../50-tecnologias/README.md).

**Repo `fastflow-webcuantica`:** misma lógica; muchos runbooks viven en `docs/90-archivo/` en lugar de `20-operaciones/`.

---

## Volver al mapa humano

[EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md](EMPIEZA-AQUI-GEORGE-O-COLABORADOR.md) · [../README.md](../README.md) · [../../AGENTS.md](../../AGENTS.md)
