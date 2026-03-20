# Patrón página “Por qué [marca]” (tipo Why Sequoia)

**Referencia:** [sequoia.com/why-sequoia](https://www.sequoia.com/why-sequoia/) — argumentario **central de diferenciación**: promesa, cita de liderazgo, cifras, plataforma + asesoría, legado, segmentos (tamaño / inversores), experiencia usuario, impacto social.

Ruta UnClic sugerida: **`/por-que-unclic`** además del ancla **`#why`** en home (misma narrativa, más espacio para profundizar).

**No copiar copy de Sequoia**; solo **secuencia de bloques** y **equivalentes** (pipeline, silos Git/Jenkins, retail, inversores en software).

---

## Navegación: mega menú «Why Sequoia» → Our Approach

En el header, **Why Sequoia** no es solo un enlace plano: abre un **desplegable** que resume el argumento y enlaza a **secciones** de la misma página (anclas) + **un artículo destacado del blog**.

| Ítem Sequoia | Rol | Equivalente UnClic |
|--------------|-----|-------------------|
| **Our Approach** + línea “Learn why Sequoia stands apart…” | Entrada al argumento | **Nuestro enfoque** — *Por qué UnClic no es un broker más del pipeline* → `/por-que-unclic` (top) o `#enfoque` |
| **Advisory + Platform** | Diferenciador producto + humano | **Asesoría + plataforma** → ancla **`#stack-integrado`** (bloque §4) |
| **Legacy of Expertise** | Tiempo en mercado | **Trayectoria** → **`#trayectoria`** (§5) |
| **Any Size, Any Situation** | Escala / madurez | **Cualquier tamaño** → **`#escala`** (§6) |
| **Employee Experience** | UX personas | **Experiencia del equipo** → **`#experiencia-equipo`** (§7) |
| **Social Impact** | Valores / comunidad | **Impacto / comunidad** → **`#impacto`** (§8) |
| **Featured from our blog** | Tráfico a Insights | **Destacado del blog** — 1 tarjeta (imagen, título, 2 líneas, *Leer artículo*) → post real |

**Implementación:** en `/por-que-unclic`, cada H2 correspondiente lleva `id` fijo (`id="stack-integrado"`, etc.) para **deep links** desde el header. El primer ítem puede ser `/por-que-unclic` sin hash o `#enfoque` en el hero.

**Header UnClic:** si **Por qué UnClic** pasa de solo `#why` a dropdown, reutilizar esta tabla como etiquetas del menú.

---

## 1. Hero argumental — ancla opcional `#enfoque` (Our Approach)

| Bloque Sequoia | Equivalente UnClic |
|----------------|---------------------|
| **Eyebrow** `WHY SEQUOIA` | **`POR QUÉ UNClic`** |
| **H1** “Not just any people partner will do” | **No cualquier socio de pipeline sirve** (o: *Desde el primer deploy ya estás en el negocio de no fallar en producción*) |
| Párrafo (disruption, capital, first hire, balance pay/benefits vs investors) | Párrafo equivalente: **velocidad vs estabilidad**, presión de negocio, **primer pipeline serio**, equilibrio entre innovación y riesgo operativo — qué hace UnClic distinto en ese contexto |

---

## 2. Cita destacada (pull quote)

| Sequoia | UnClic |
|---------|--------|
| Quote en comillas + **Nombre · Rol** (silos comp/benefits → promise) | Cita corta: **silos** (Jenkins aislado, Git sin Jenkinsfile, AWS sin trazabilidad) → **promesa** de socio que **cumple** con guía + stack integrado |
| Foto o nombre repetido | Misma jerarquía visual |

---

## 3. “By the numbers”

| Sequoia | UnClic |
|---------|--------|
| Bloque **Sequoia by the numbers** + métricas grandes | **UnClic en cifras** — solo datos reales (años, pipelines, documentos, demos) |

---

## 4. Una plataforma + un equipo — ancla sugerida `#stack-integrado`

| Bloque Sequoia | Equivalente UnClic |
|----------------|---------------------|
| **ONE TEAM, ONE INTEGRATED PLATFORM** | **UN EQUIPO, UN STACK INTEGRADO** (o *Pipeline + plataforma documentada*) |
| Párrafo: tech amplifies advisory, never abandon to software | Tecnología (Jenkins, Gitea, registry) **amplía** la asesoría; **no** entregar solo herramientas sin runbooks |
| **3 tarjetas** Advisory · Outsourcing · Platform | Misma tríada UnClic → enlaces a `/soluciones/...` o anclas |

---

## 5. Legado + sub-bloque “inversores” — `#trayectoria`

| Sequoia | UnClic |
|---------|--------|
| **LEGACY OF EXPERTISE** — decades, extension of team, one source of truth | **Trayectoria** — años en retail/POS/pipeline, extensión del equipo cliente, **una fuente de verdad** (Git + CI) |
| **Investor-Backed Companies** + **VC & PE** card | **Equipos con presión de crecimiento** / **CTO que debe demostrar control de releases** — o inversores en **software** que exigen madurez de entrega (solo si aplica al posicionamiento) |
| Párrafo inner workings comp, equity, healthcare… | Párrafo: **Jenkinsfile, registry, despliegue, observabilidad, documentación viva** — conocimiento sistematizado |

---

## 6. Cualquier tamaño, cualquier etapa — `#escala`

| Sequoia | UnClic |
|---------|--------|
| **ANY SIZE COMPANY…** startup scaling vs mature + gráfico de fases | **De piloto a cadena** — startup con un servicio vs retail multi-tienda; **misma metodología**, distinta escala; gráfico **fases de madurez CI/CD** (manual → Git → Jenkins → registry → K8s) |
| “right-sized solutions” | Soluciones **a la medida**: asesoría puntual vs pipeline gestionado vs hub completo |

---

## 7. Experiencia elevada (producto visible) — `#experiencia-equipo`

| Sequoia | UnClic |
|---------|--------|
| **ELEVATED EMPLOYEE EXPERIENCE** — pay, equity, benefits holísticos | **Experiencia del equipo de ingeniería** — ver builds, saber qué versión está en qué entorno, menos incertidumbre; **holístico** = código + pipeline + doc en un mismo relato |
| Capturas Sequoia OS / mobile app | Capturas **hub**, **Blue Ocean** o **Gitea + Jenkins** (lo que sea demo real) |

---

## 8. Impacto / valores (opcional) — `#impacto`

| Sequoia | UnClic |
|---------|--------|
| **IMPACT PROGRAM** + Our Story / Our Team | Si hay **programa de impacto** o **código abierto / comunidad**, bloque equivalente; si no, sustituir por **Cultura y valores** o enlaces a **Nuestra historia** / **Equipo** (`/empresa`) |

---

## 9. Orden sugerido en `/por-que-unclic`

1. Eyebrow + H1 + párrafo  
2. Pull quote  
3. Cifras  
4. Un equipo + plataforma + 3 soluciones  
5. Legado + bloque inversores / madurez  
6. Cualquier tamaño + visual de fases  
7. Experiencia equipo + screenshots  
8. Impacto o valores + enlaces  
9. CTA **Comenzar** / **Hablemos**  

La home puede mantener **solo el resumen** (#why con 4 pilares); esta página **despliega** el argumento completo.

---

## Relación con otras plantillas

- **Por qué en home** → [IA-SITIO-ESTILO-ENTERPRISE.md](IA-SITIO-ESTILO-ENTERPRISE.md) (4 pilares).  
- **Soluciones (3 tarjetas)** → [PATRON-PAGINA-SOLUCION-SEQUOIA.md](PATRON-PAGINA-SOLUCION-SEQUOIA.md).  
- **Empresa / historia** → [PATRON-PAGINA-EMPRESA-SEQUOIA.md](PATRON-PAGINA-EMPRESA-SEQUOIA.md).  
- **SEO (title, OG, JSON-LD, jerarquía, anclas):** [PATRON-WHY-SEO-SEQUOIA.md](PATRON-WHY-SEO-SEQUOIA.md).

## Enlace Sequoia (formato)

- [Why Sequoia](https://www.sequoia.com/why-sequoia/)
