/**
 * Shadcn Blocks–style primitives.
 * Toda la UI usa esta estructura (Container, Section, SectionHeader) para alinearse
 * con los bloques de shadcnblocks.com. El contenido sigue viniendo de lib/copy.
 *
 * Instalar un bloque real (con SHADCNBLOCKS_API_KEY en .env): nombres sin guión.
 *   npx shadcn add @shadcnblocks/hero2
 * Ver docs/INSTALAR-SHADCN-BLOCKS.md y docs/CONSISTENCIA-UI.md.
 * Luego sustituir el componente actual por el bloque instalado y pasar nuestro copy.
 */
export { BlockContainer } from './container';
export { BlockSectionHeader, BlockSectionHeaderLeft } from './section-header';
export { BlockSection } from './section';
