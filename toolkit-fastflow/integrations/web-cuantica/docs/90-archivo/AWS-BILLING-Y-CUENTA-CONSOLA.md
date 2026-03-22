# AWS — Billing and Cost Management y Account (qué ves en la consola)

Ruta típica: **AWS Console** (cabecera **Global**) → menú de cuenta → **Billing and Cost Management** o **Account**. Usuario y cuenta aparecen en la cabecera (ej. nombre e **ID de cuenta de 12 dígitos**).

---

## Menú lateral — Billing and Cost Management

- **Billing View** (New) | **Home** | **Getting Started** | **Dashboards** (New)
- **Billing and Payments:** Bills | Payments | Credits | Purchase Orders
- **Cost and Usage Analysis:** Cost Explorer | Cost Explorer Saved Reports | Cost Anomaly Detection | **Free Tier** | Data Exports | Customer Carbon Footprint Tool
- **Cost Organization:** Cost Categories | Cost Allocation Tags | Billing Conductor
- **Budgets and Planning:** Budgets (New) | Budgets Reports | **Pricing Calculator** (New)
- **Savings and Commitments:** Cost Optimization Hub (New) | **Savings Plans** (Overview, Inventory, Recommendations, Purchase Analyzer, Utilization/Coverage, Purchase, Cart) | **Reservations** (Overview, Recommendations, Utilization/Coverage)
- **Preferences and Settings:** Payment Preferences | Billing Preferences | Cost Management Preferences (New) | Tax Settings | Invoice Configuration | **Billing Transfer** (New)
- **Legacy Pages**

---

## Account — Account Info y detalles

Sección **Account** (o **Account Info**): botón **Close account** (arriba a la derecha según la UI).

| Campo (etiqueta) | Descripción |
|-------------------|-------------|
| **Name** | Nombre de la cuenta en la consola |
| **ID** | ID de cuenta AWS (12 dígitos) |
| **Service provider** | Ej. AMAZON WEB SERVICES MEXICO (según región de facturación) |
| **ARN** | `arn:aws:account::<account-id>:account` |

- **Account display settings — new:** Edit (ej. **Account color**).
- **Contact information:** Edit — **Full name**, **Phone number**, **Company name**, **Website URL**, **Address** (país, CP). *No commitear datos reales en el repo; actualizar solo en la consola.*
- **Alternate contacts:** Billing / Operations / Security — *Add* si quieres notificaciones por tema sin depender solo del titular.
- **AWS Regions:** Tabla **Region** | **Status**. Algunas regiones **Enabled by default**; otras **Enabled** o **Disabled** según hayas optado por habilitarlas. Ejemplos de nombres: United States (Ohio), United States (N. Virginia), Mexico (Central), Europe (Ireland), etc. **Disable / Enable** para gestionar qué regiones pueden usarse en la cuenta.
- **Amazon Web Services in Mainland China:** Nota de que Pekín/Ningxia requieren **cuenta AWS China** separada.
- **IAM user and role access to Billing information:** Activar/desactivar si usuarios IAM pueden ver facturación (por defecto suele estar desactivado).
- **Reserved instance marketplace settings:** Enlaces para vendedor/cuenta bancaria e información fiscal (1099K, W-8BEN, etc.).
- **Account Contract Information:** Ej. Service public sector customer.
- **Other settings:** Enlaces a Payment preferences, Communication preferences, Support plans, GovCloud, Customer verification.

Pie de página estándar AWS: CloudShell, Feedback, © 2026 Amazon Web Services, Privacy, Terms, Cookie preferences.

---

## Enlaces útiles

- [AWS Billing and Cost Management](https://console.aws.amazon.com/billing/) (consola; requiere inicio de sesión).
- [Free Tier](https://aws.amazon.com/free/) — seguimiento de uso en consola → **Free Tier** en el menú lateral.
- Documentación relacionada en este repo: [CAPITULO-SERVICIOS-CORE-AWS.md](CAPITULO-SERVICIOS-CORE-AWS.md), [CAPITULO-FACTURACION-Y-PRECIOS.md](CAPITULO-FACTURACION-Y-PRECIOS.md) si existen.
