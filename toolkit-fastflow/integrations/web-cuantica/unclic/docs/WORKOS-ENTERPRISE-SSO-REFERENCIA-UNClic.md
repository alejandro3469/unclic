# WorkOS — referencia UnClic (SSO y features enterprise, SaaS)

**Qué es:** plataforma **comercial** (no open source) de **APIs y componentes** para que una aplicación B2B cumpla requisitos típicos de **IT enterprise**: inicio de sesión único (**SAML / OIDC**) contra docenas de IdP (Okta, Microsoft Entra ID, Google, OneLogin, ADFS, Ping, Duo, etc.), **Directory Sync** (SCIM, HRIS), gestión de usuarios y organizaciones, **AuthKit** (UI de auth con Radix), **Magic Auth** (código por email), MFA, **Admin Portal** alojado para que el administrador IT del **cliente** configure la conexión sin tickets infinitos a tu soporte, audit logs, y productos adicionales según su roadmap (Radar, Vault, RBAC avanzado, etc.).

**Sitio y precios:** [workos.com](https://workos.com/) · [Documentación](https://workos.com/docs) · [Pricing](https://workos.com/pricing) · programa [Startups](https://workos.com/startups) (si aplica).

**Por qué está en el plan UnClic:** cuando vendes **software a empresas**, el checklist de compras suele incluir **“¿tenéis SSO?”** y a veces **SCIM**. Montar SAML + metadatos + rotación + UX para IT **a mano** es costoso; WorkOS (y similares: Auth0 Enterprise, Clerk, etc.) **acortan tiempo a mercado**. El plan OSS de UnClic sigue priorizando **Keycloak / Zitadel / JWT propio** donde el cliente quiere **control y auto-hospedaje**; WorkOS entra como **opción explícita de pago** en **L10** cuando el negocio lo justifica.

---

## 1. Mapeo a capas (PLAN-STACK)

| Capa | Uso típico con WorkOS |
|------|------------------------|
| **L10** | SSO SAML/OIDC, sesiones/tokens normalizados, MFA, magic auth |
| **L10** (ext.) | Directory Sync / SCIM — alta, baja y grupos desde el IdP del cliente |
| **Operación / soporte** | Admin Portal (marca blanca, dominio CNAME) — reduce carga en tu equipo |
| **L12** (parcial) | Audit logs (según producto; no sustituye SIEM completo) |

**Pricing:** siempre verificar **precio vigente** en [workos.com/pricing](https://workos.com/pricing); el modelo suele ser por **MAU**, **organización** o **add-ons** según producto — no documentamos cifras aquí porque cambian.

---

## 2. Relación con **UnClic / portal actual**

| Hoy (repo) | Con WorkOS |
|------------|------------|
| `services/api` + registro por correo + **JWT** para `/portal`, `/demo`, `/flow-demo` | Podrías **delegar** login enterprise (SSO) y seguir emitiendo JWT propio tras callback, o **sustituir** parte del flujo — requiere **diseño de arquitectura** y revisión legal/DPA |
| Sin SAML/SCIM en producto | WorkOS cubre integraciones con IdP sin que implementes cada uno |
| Mensaje público “stack abierto” | WorkOS es **SaaS cerrado**; en propuestas **ser transparente**: “componente enterprise de pago donde el cliente exige SSO” |

**Regla:** no mezclar en la misma frase “100% OSS” y “login solo WorkOS” sin matizar el alcance.

---

## 3. Cuándo proponerlo

- **Sí:** contrato con empresa mediana/grande; RFP pide **SSO** o **SCIM**; tiempo corto; presupuesto de línea “identity vendor”.  
- **Evaluar vs Keycloak:** cliente **exige datos en su VPC** o política **no SaaS para identidad** → priorizar OSS (Keycloak, Zitadel) aunque el esfuerzo sea mayor.  
- **No como default** del sitio demostración UnClic (landing + demos FastFlow) salvo **PoC** acotada.

---

## 4. Próximo paso atómico (si un cliente lo pide)

1. Crear proyecto en WorkOS (dev + staging); leer última doc de **SSO** y **AuthKit** o flujo headless.  
2. PoC: un endpoint en `services/api` que intercambia `code` por perfil (patrón similar al snippet Node de su web) y emite **JWT interno** compatible con `DemoGate` / portal.  
3. Documentar en este archivo: versión SDK, variables de entorno, y decisión **SSO-only vs SSO + email/password**.

---

## 5. Referencia de mensaje comercial (resumen)

El pitch público de WorkOS (“**Enterprise Ready**”, SSO en minutos, Admin Portal, unificación de APIs) es útil para **alinear expectativas con stakeholders**; la decisión técnica sigue siendo: **TCO**, **lock-in**, **datos residencia**, **SLA** y **roadmap** frente a Keycloak + operación propia.

*Documento de planificación UnClic; no sustituye contrato ni documentación legal de WorkOS.*
