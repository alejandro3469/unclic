import type { Config } from 'tailwindcss';

/** Semántica tipo shadcn Nova + zinc; paleta `nord` sigue en tokens para utilidades legacy. */
const config: Config = {
  darkMode: ['class'],
  content: [
    './app/**/*.{js,ts,jsx,tsx,mdx}',
    './components/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    /** Alineado a `BlockContainer`: mismo padding horizontal y tope 72rem en todos los breakpoints. */
    container: {
      center: true,
      padding: {
        DEFAULT: '1rem',
        sm: '1.5rem',
        lg: '2rem',
      },
      screens: {
        sm: '72rem',
        md: '72rem',
        lg: '72rem',
        xl: '72rem',
        '2xl': '72rem',
      },
    },
    extend: {
      fontFamily: {
        sans: ['var(--font-inter)', 'ui-sans-serif', 'system-ui', 'sans-serif'],
        /** Display / títulos: mismo Inter que body (patrón ui.shadcn.com Nova). */
        serif: ['var(--font-inter)', 'ui-sans-serif', 'system-ui', 'sans-serif'],
        mono: ['var(--font-space-mono)', 'ui-monospace', 'monospace'],
        inter: ['var(--font-inter)', 'ui-sans-serif', 'system-ui', 'sans-serif'],
        'space-mono': ['var(--font-space-mono)', 'ui-monospace', 'monospace'],
        ubuntu: ['var(--font-ubuntu)', 'ui-sans-serif', 'sans-serif'],
      },
      colors: {
        background: 'hsl(var(--background))',
        foreground: 'hsl(var(--foreground))',
        primary: {
          DEFAULT: 'hsl(var(--primary))',
          foreground: 'hsl(var(--primary-foreground))',
        },
        muted: {
          DEFAULT: 'hsl(var(--muted))',
          foreground: 'hsl(var(--muted-foreground))',
        },
        card: {
          DEFAULT: 'hsl(var(--card))',
          foreground: 'hsl(var(--card-foreground))',
        },
        border: 'hsl(var(--border))',
        input: 'hsl(var(--input))',
        ring: 'hsl(var(--ring))',
        accent: 'hsl(var(--accent))',
        'accent-foreground': 'hsl(var(--accent-foreground))',
        popover: {
          DEFAULT: 'hsl(var(--popover))',
          foreground: 'hsl(var(--popover-foreground))',
        },
        secondary: {
          DEFAULT: 'hsl(var(--secondary))',
          foreground: 'hsl(var(--secondary-foreground))',
        },
        destructive: {
          DEFAULT: 'hsl(var(--destructive))',
          foreground: 'hsl(var(--destructive-foreground))',
        },
        chart: {
          1: 'hsl(var(--chart-1))',
          2: 'hsl(var(--chart-2))',
          3: 'hsl(var(--chart-3))',
          4: 'hsl(var(--chart-4))',
          5: 'hsl(var(--chart-5))',
        },
        /* Nord palette — use as bg-nord-7, text-nord-10, border-nord-3, etc. */
        nord: {
          0: 'hsl(var(--nord0))',
          1: 'hsl(var(--nord1))',
          2: 'hsl(var(--nord2))',
          3: 'hsl(var(--nord3))',
          4: 'hsl(var(--nord4))',
          5: 'hsl(var(--nord5))',
          6: 'hsl(var(--nord6))',
          7: 'hsl(var(--nord7))',
          8: 'hsl(var(--nord8))',
          9: 'hsl(var(--nord9))',
          10: 'hsl(var(--nord10))',
          11: 'hsl(var(--nord11))',
          12: 'hsl(var(--nord12))',
          13: 'hsl(var(--nord13))',
          14: 'hsl(var(--nord14))',
          15: 'hsl(var(--nord15))',
        },
      },
      borderRadius: {
        lg: 'var(--radius)',
        md: 'calc(var(--radius) - 2px)',
        sm: 'calc(var(--radius) - 4px)',
        card: 'var(--radius-card, 1.5rem)',
        'card-sm': '0.75rem',
      },
      maxWidth: {
        content: 'var(--content-max, 72rem)',
        'content-wide': 'var(--content-wide, 80rem)',
      },
      fontSize: {
        'body-lg': ['1.1875rem', { lineHeight: '1.6' }],
        'display-sm': ['2rem', { lineHeight: '1.2', letterSpacing: '-0.02em' }],
        'display-md': ['2.5rem', { lineHeight: '1.15', letterSpacing: '-0.025em' }],
        'display-lg': ['3rem', { lineHeight: '1.1', letterSpacing: '-0.03em' }],
      },
      keyframes: {
        'accordion-down': {
          from: { height: '0' },
          to: { height: 'var(--radix-accordion-content-height)' },
        },
        'accordion-up': {
          from: { height: 'var(--radix-accordion-content-height)' },
          to: { height: '0' },
        },
        'sheet-in-left': { from: { transform: 'translateX(-100%)' }, to: { transform: 'translateX(0)' } },
        'sheet-out-left': { from: { transform: 'translateX(0)' }, to: { transform: 'translateX(-100%)' } },
        'sheet-in-right': { from: { transform: 'translateX(100%)' }, to: { transform: 'translateX(0)' } },
        'sheet-out-right': { from: { transform: 'translateX(0)' }, to: { transform: 'translateX(100%)' } },
        'fade-in': { from: { opacity: '0' }, to: { opacity: '1' } },
        'fade-out': { from: { opacity: '1' }, to: { opacity: '0' } },
      },
      animation: {
        'accordion-down': 'accordion-down 0.2s ease-out',
        'accordion-up': 'accordion-up 0.2s ease-out',
        'sheet-in-left': 'sheet-in-left 0.3s ease-out',
        'sheet-out-left': 'sheet-out-left 0.3s ease-out',
        'sheet-in-right': 'sheet-in-right 0.3s ease-out',
        'sheet-out-right': 'sheet-out-right 0.3s ease-out',
        'fade-in': 'fade-in 0.2s ease-out',
        'fade-out': 'fade-out 0.2s ease-out',
      },
    },
  },
  plugins: [require('@tailwindcss/typography')],
};

export default config;
