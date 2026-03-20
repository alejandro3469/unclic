# Requisitos de landing para Google Merchant Center (UnClic)

Este documento es un checklist interno para que la landing de UnClic cumpla los [requisitos de landing de Google Merchant Center](https://support.google.com/merchants/answer/6167118) cuando se usen anuncios de compras o listados gratuitos.

## Requisitos mínimos

### Producto / oferta visible

- [x] **Precio visible**: La sección de precios (`#pricing`) muestra precios claros (Desde 150 USD, Desde 350 USD, Gratuito) con moneda explícita.
- [x] **Moneda**: Se muestra "Precios en USD" en la descripción de la sección y en cada plan (USD en el texto).
- [x] **CTA / compra**: Cada plan tiene un botón (Solicitar cotización, Probar la demo) que lleva a `#contacto` o `#demos`.
- [x] **Sin tapar contenido**: El TopBanner es una barra superior cerrable; no cubre precio ni CTA (y debe mantenerse así).

### Experiencia consistente

- [x] **Idioma**: `lang="es"` en `<html>`, `inLanguage: 'es-ES'` en Schema.org y contenido en español, alineado con el idioma del feed si se usa Merchant.
- [x] **Misma oferta para todos**: La página no cambia producto/precio por ubicación, cookies o dispositivo; el contenido es estable para tráfico desde anuncios.
- [x] **Disponibilidad**: Los planes mostrados están disponibles; si algo deja de ofrecerse, actualizar copy y schema.

### Página en funcionamiento

- [x] **Landing es una página web**: No se enlaza a PDF, imagen, audio ni email como destino del anuncio.
- [x] **Móvil**: Sitio responsive; sin Flash, Silverlight ni ActiveX.
- [x] **Botón atrás**: No se bloquea el botón atrás del navegador.
- [x] **Mismo dominio**: Los enlaces de la landing no redirigen a otro dominio distinto al verificado en Merchant Center.

### Datos estructurados (buenas prácticas)

- [x] **Precio y moneda en el HTML inicial**: En `app/layout.tsx` se incluye Schema.org `Service` con `offers` (AggregateOffer: `priceCurrency`, `lowPrice`, `highPrice`, `availability`) en el JSON-LD del documento.
- [ ] **Si se añaden productos concretos al feed**: Incluir en cada página de producto/detalle el schema Product/Service con `offers` (precio, moneda, disponibilidad) en la respuesta HTTP inicial; evitar depender solo de JS para precio/disponibilidad.

## Mantenimiento

- Al cambiar precios o moneda: actualizar `lib/copy-pricing.ts`, la sección de precios y el JSON-LD en `app/layout.tsx`.
- Si se añade un pop-up o modal: asegurarse de que sea cerrable y que no cubra precio, descripción principal ni botones de CTA.
- Si se usan redirects (p. ej. por idioma o región): mantener el mínimo necesario y que la landing final cumpla estos puntos.

## Referencia

- [About landing page requirements – Google Merchant Center Help](https://support.google.com/merchants/answer/6167118)
