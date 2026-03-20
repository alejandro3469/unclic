# Dónde seguir trabajando y documentación para replicar

## Carpeta en la que seguir trabajando

**Carpeta principal (origen de verdad):**  
`toolkit-fastflow/integrations/web-cuantica/unclic`

Ahí está el proyecto Next.js del landing UnClic, el `Jenkinsfile`, la app y toda la documentación. Aquí se hacen los cambios de código y contenido.

**Copia opcional para Gitea (nucleic):**  
`~/Downloads/nucleic-landing` (o donde tengas el clon del repo **nucleic** en Gitea)

Solo para reflejar cambios de **unclic** en el repo remoto **nucleic**: puedes copiar con `rsync` desde unclic o clonar desde Gitea y luego hacer commits granulares y push. El origen de verdad es **unclic**; nucleic-landing es un espejo para publicar en Gitea.

---

## Índice de documentación para replicar

| Doc | Contenido |
|-----|-----------|
| **[REPLICAR-UNCLIC-CONSULTING-COMPLETO.md](REPLICAR-UNCLIC-CONSULTING-COMPLETO.md)** | Guía maestra: DNS, EC2, Nginx, Certbot, deploy (2 pasos), Gitea, Jenkins |
| [NAMECHEAP-DNS-UNCLIC-ROOT.md](NAMECHEAP-DNS-UNCLIC-ROOT.md) | Registros A @ y www en Namecheap |
| [COMANDOS-EC2-UNCLIC-CONSULTING.md](COMANDOS-EC2-UNCLIC-CONSULTING.md) | Comandos en la EC2 (Nginx, Certbot, deploy en 2 pasos) |
| [SUBIR-SITIO-A-UNCLIC-CONSULTING.md](SUBIR-SITIO-A-UNCLIC-CONSULTING.md) | Subir sitio (manual y Jenkins) |
| [GITEA-NUCLEIC-PUSH.md](GITEA-NUCLEIC-PUSH.md) | Subir código a nucleic (push por bloques) |
| [GITEA-413-PUSH-RESUELTO.md](GITEA-413-PUSH-RESUELTO.md) | Solución 413 en Nginx de Gitea |
| [PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md](PLAN-SUBDOMINIO-LANDING-DEMOS-UNCLIC.md) | Plan subdominio, demos, Jenkins |

---

## Resumen rápido

- **Desarrollo:** trabaja en `unclic` (monorepo).
- **Publicar en Gitea:** cuando quieras, sincroniza a nucleic-landing y haz push por bloques (ver GITEA-NUCLEIC-PUSH.md).
- **Replicar todo el entorno:** sigue [REPLICAR-UNCLIC-CONSULTING-COMPLETO.md](REPLICAR-UNCLIC-CONSULTING-COMPLETO.md).
