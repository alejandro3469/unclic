'use client';

/**
 * Franja decorativa tipo “agente / voz” (patrón visual cercano a catálogos tipo ElevenLabs UI),
 * sin SDK ni API de pago: solo presentación.
 */
export function AgentWaveformStrip() {
  const bars = 28;
  return (
    <div
      className="mt-6 flex h-11 max-w-md items-end justify-center gap-0.5 opacity-90"
      aria-hidden
    >
      {Array.from({ length: bars }, (_, i) => {
        const h = 22 + ((i * 17) % 55);
        return (
          <span
            key={i}
            className="animate-unclic-agent-wavebar w-[3px] shrink-0 rounded-full bg-primary/35"
            style={{
              height: `${h}%`,
              animationDelay: `${i * 0.035}s`,
            }}
          />
        );
      })}
    </div>
  );
}
