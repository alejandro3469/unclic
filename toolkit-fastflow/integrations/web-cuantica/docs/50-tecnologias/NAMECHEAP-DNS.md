# Namecheap DNS — Por qué y cómo replicar

## Qué es Namecheap (contexto)

**Namecheap** es un registrador de dominios (ICANN-accredited). En este proyecto usamos el dominio **unclic.consulting** para apuntar subdominios (jenkins, gitea, pos, www) a las IP de las EC2.

**Interfaz — Domain Details:** Tras **Domain List** → **unclic.consulting** → **Manage** verás las pestañas **Domain** | **Products** | **Sharing & Transfer** | **Advanced DNS**. El DNS (registros A, CNAME, MX) se configura en **Advanced DNS** → **HOST RECORDS** y **MAIL SETTINGS**. En **Domain** ves Status & Validity (ACTIVE, fechas, AUTO-RENEW), Domain Privacy, PremiumDNS, NAMESERVERS, REDIRECT DOMAIN, DOMAIN CONTACTS (Registrant, Administrator, Technical, Billing, EDIT). En **Sharing & Transfer** están Share Access (New Manager), Change Ownership (New Owner), Transfer Out (Domain Lock, Auth Code).

## Por qué lo usamos

- **Dominio unclic.consulting:** Subdominios (jenkins, gitea, pos, www) apuntan a las IP de las EC2. URLs amigables en lugar de solo IPs.
- **Objetivo:** No es software que instalamos; es el registro DNS del dominio. Estándar (registros A, CNAME, MX); puedes migrar el dominio a otro registrador.

## Cómo replicar

| Consola | Acción |
|---------|--------|
| Navegador | Namecheap → Domain List → unclic.consulting → Manage → pestaña **Advanced DNS** |
| Namecheap UI | HOST RECORDS: A **jenkins** → IP Jenkins; **gitea** → IP Gitea; **pos** → IP POS si aplica; **@** / **www** → IP del **landing** (EC2 Nginx u hosting estático) |

Ver [DOMINIO-NAMECHEAP-UNCLIC-EC2](../20-operaciones/DOMINIO-NAMECHEAP-UNCLIC-EC2.md) para la estructura completa de la UI (pestañas Domain, Sharing & Transfer, Advanced DNS), valores actuales y tabla de registros.

## Enlaces

- [Namecheap](https://www.namecheap.com) — registro y gestión de dominios.
- Help Center, Knowledgebase, Live Chat desde la cuenta Namecheap.
