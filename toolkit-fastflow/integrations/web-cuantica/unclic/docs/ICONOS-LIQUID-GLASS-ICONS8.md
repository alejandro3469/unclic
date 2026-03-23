# Iconos Liquid Glass (Icons8)

## Proveedor y licencia

- Estilo: [Liquid Glass — Icons8](https://icons8.com/liquid-glass)
- Carga: PNG vía `https://img.icons8.com/liquid-glass/{128}/{slug}.png`
- Atribución en pie del sitio: enlace a Icons8 (uso gratuito seleccionado según sus términos).

## Implementación

- Helper: `lib/icons8-liquid-glass.ts` (`icons8LiquidGlassUrl`, mapa `LG`)
- Componente: `components/ui/liquid-glass-icon.tsx` (`LiquidGlassIcon` + `next/image`)
- `next.config.js`: `remotePatterns` para `img.icons8.com/liquid-glass/**`

## Dónde se usa (marketing)

Hero (fila de beneficios, CTA), pilares home, features, cómo funciona, journey, CTA final, cliente ideal, teaser atlas, precios (lista), audio (cabecera lista), globo, Hero154, Hero2 (badge).

Los iconos de **control UI** (chevron, menú, sheet, select) siguen en **Lucide** para consistencia con shadcn/ui.

## Comprobar

1. `npm run build`
2. En dev: abrir `/` y en red del navegador filtrar `icons8.com/liquid-glass` → respuestas **200**.
