/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'export',
  trailingSlash: false,
  images: {
    unoptimized: true,
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'img.icons8.com',
        pathname: '/liquid-glass/**',
      },
      { protocol: 'https', hostname: 'placehold.co', pathname: '/**' },
      { protocol: 'https', hostname: 'images.unsplash.com', pathname: '/**' },
      { protocol: 'https', hostname: 'cdn.simpleicons.org', pathname: '/**' },
      { protocol: 'https', hostname: 'deifkwefumgah.cloudfront.net', pathname: '/**' },
      { protocol: 'https', hostname: 'unpkg.com', pathname: '/**' },
    ],
  },
  /**
   * En discos casi llenos, la caché de Webpack falla con ENOSPC.
   * - build (prod): caché desactivada por defecto.
   * - dev: activa `NEXT_DISABLE_WEBPACK_CACHE=1` si sigues sin espacio (más lento, menos disco).
   */
  webpack: (config, { dev }) => {
    if (!dev || process.env.NEXT_DISABLE_WEBPACK_CACHE === '1') {
      config.cache = false;
    }
    return config;
  },
};

module.exports = nextConfig;
