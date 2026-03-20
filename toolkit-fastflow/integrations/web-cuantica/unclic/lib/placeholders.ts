/**
 * URLs de media para el sitio.
 * Imágenes: Unsplash (reales). Audio/video: muestras de prueba públicas.
 * Alternativa: imágenes generadas por Gemini en public/ o URLs propias.
 */

const UNSPLASH_BASE = 'https://images.unsplash.com';

/** Parámetros comunes para Unsplash (tamaño y calidad). */
function unsplashParams(width: number, quality = 80): string {
  return `?w=${width}&q=${quality}&fit=crop`;
}

/** Hero: imagen destacada real (Unsplash). */
export const HERO_IMAGE_PLACEHOLDER =
  `${UNSPLASH_BASE}/photo-1460925895917-afdab827c52f${unsplashParams(1200)}`;

/** Galería: 8 imágenes reales (Unsplash) — estilo Shadcn Gallery. */
export const GALLERY_PLACEHOLDERS = [
  `${UNSPLASH_BASE}/photo-1551431009-a22ee0f75490${unsplashParams(800)}`, // laptop/workspace
  `${UNSPLASH_BASE}/photo-1522071820081-009f0129c71c${unsplashParams(800)}`, // team
  `${UNSPLASH_BASE}/photo-1552664730-d307ca884978${unsplashParams(800)}`, // team meeting
  `${UNSPLASH_BASE}/photo-1504384308090-c894fdcc538d${unsplashParams(800)}`, // office
  `${UNSPLASH_BASE}/photo-1517245386807-bb43f82c33c4${unsplashParams(800)}`, // startup
  `${UNSPLASH_BASE}/photo-1497366216548-37526070297c${unsplashParams(800)}`, // workspace
  `${UNSPLASH_BASE}/photo-1556761175-b413da4baf72${unsplashParams(800)}`, // collaboration
  `${UNSPLASH_BASE}/photo-1553877522-43269d4ea984${unsplashParams(800)}`, // coding
] as const;

/** Compatibilidad: función placeholder por si se quieren placehold.co en desarrollo. */
export function placeholderImage(
  width: number,
  height: number,
  text = 'Placeholder'
): string {
  return `${UNSPLASH_BASE}/photo-1557804506-669a67965ba0${unsplashParams(width)}`;
}

/** Audio: una sola pista de prueba pública. Sustituir por public/audio/ en uso final. */
export const PLACEHOLDER_AUDIO_URL =
  'https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3';

/** Video: muestra de prueba pública. Sustituir por public/video/ o URL final. */
export const PLACEHOLDER_VIDEO_URL =
  'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4';
