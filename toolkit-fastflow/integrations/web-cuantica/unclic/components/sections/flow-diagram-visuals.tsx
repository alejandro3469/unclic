'use client';

/**
 * Diagramas de flujo: nodos con color, hover = tooltip con detalle.
 * Sin bloques tipo card alrededor (lo controla la sección).
 */

import React, { forwardRef, useRef } from 'react';
import { AnimatedBeam } from '@/components/ui/animated-beam';
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip';
import { cn } from '@/lib/utils';
import { Server, Package, Layers, Rocket, FolderGit, User, Cloud, Globe, Container } from 'lucide-react';

export const flowBeamProps = {
  gradientStartColor: 'hsl(var(--primary))',
  gradientStopColor: 'hsl(var(--primary) / 0.55)',
  pathColor: 'hsl(var(--border) / 0.7)',
  duration: 3,
};

/** Clases por nodo: borde + fondo suave + color del icono */
const T = {
  amber:
    'border-amber-500/70 bg-gradient-to-br from-amber-400/35 to-orange-500/25 text-amber-950 dark:from-amber-500/30 dark:to-orange-600/20 dark:text-amber-100',
  emerald:
    'border-emerald-500/70 bg-gradient-to-br from-emerald-400/35 to-teal-500/25 text-emerald-950 dark:from-emerald-500/30 dark:to-teal-600/20 dark:text-emerald-100',
  sky: 'border-sky-500/70 bg-gradient-to-br from-sky-400/35 to-cyan-500/25 text-sky-950 dark:from-sky-500/30 dark:to-cyan-600/20 dark:text-sky-100',
  violet:
    'border-violet-500/70 bg-gradient-to-br from-violet-400/35 to-fuchsia-500/25 text-violet-950 dark:from-violet-500/30 dark:to-fuchsia-600/20 dark:text-violet-100',
  rose: 'border-rose-500/70 bg-gradient-to-br from-rose-400/35 to-pink-500/25 text-rose-950 dark:from-rose-500/30 dark:to-pink-600/20 dark:text-rose-100',
  orange:
    'border-orange-500/70 bg-gradient-to-br from-orange-400/35 to-amber-500/25 text-orange-950 dark:from-orange-500/30 dark:to-amber-600/20 dark:text-orange-100',
  blue: 'border-blue-500/70 bg-gradient-to-br from-blue-400/35 to-indigo-500/25 text-blue-950 dark:from-blue-500/30 dark:to-indigo-600/20 dark:text-blue-100',
  teal: 'border-teal-500/70 bg-gradient-to-br from-teal-400/35 to-emerald-500/25 text-teal-950 dark:from-teal-500/30 dark:to-emerald-600/20 dark:text-teal-100',
  indigo:
    'border-indigo-500/70 bg-gradient-to-br from-indigo-400/35 to-blue-600/25 text-indigo-950 dark:from-indigo-500/30 dark:to-blue-700/20 dark:text-indigo-100',
  cyan: 'border-cyan-500/70 bg-gradient-to-br from-cyan-400/35 to-sky-500/25 text-cyan-950 dark:from-cyan-500/30 dark:to-sky-600/20 dark:text-cyan-100',
} as const;

export const FlowCircle = forwardRef<
  HTMLDivElement,
  { className?: string; children?: React.ReactNode; toneClass?: string }
>(({ className, children, toneClass }, ref) => (
  <div
    ref={ref}
    className={cn(
      'z-10 flex shrink-0 cursor-default items-center justify-center rounded-full border-2 p-3 shadow-[0_4px_24px_-8px_rgba(0,0,0,0.35)] transition-transform hover:scale-[1.04]',
      toneClass ?? 'border-border/80 bg-muted/40 text-muted-foreground',
      className
    )}
  >
    {children}
  </div>
));
FlowCircle.displayName = 'FlowCircle';

function NodeWithTip({
  refEl,
  toneClass,
  icon: IconComp,
  label,
  tip,
  iconClass,
  nodeSize = 'md',
}: {
  refEl: React.RefObject<HTMLDivElement | null>;
  toneClass: string;
  icon: React.ComponentType<{ className?: string }>;
  label: string;
  tip: string;
  iconClass?: string;
  /** `lg`: nodos más grandes (sección hero / flujo). */
  nodeSize?: 'md' | 'lg';
}) {
  const isLg = nodeSize === 'lg';
  return (
    <Tooltip>
      <TooltipTrigger asChild>
        <div className={cn('flex flex-col items-center', isLg ? 'gap-2' : 'gap-1')}>
          <FlowCircle
            ref={refEl}
            toneClass={toneClass}
            className={cn(isLg ? 'size-16 p-4 md:size-[4.75rem] md:p-5' : 'size-12 md:size-14')}
          >
            <IconComp
              className={cn(
                isLg ? 'size-7 md:size-9' : 'size-5 md:size-6',
                iconClass ?? 'opacity-90'
              )}
              aria-hidden
            />
          </FlowCircle>
          <span
            className={cn(
              'max-w-[6rem] text-center font-semibold leading-tight text-foreground',
              isLg ? 'max-w-[7.5rem] text-xs md:text-sm' : 'text-[11px] md:text-xs'
            )}
          >
            {label}
          </span>
        </div>
      </TooltipTrigger>
      <TooltipContent side="top" className="max-w-[260px]">
        <p className="font-medium text-foreground">{label}</p>
        <p className="mt-1 text-muted-foreground">{tip}</p>
      </TooltipContent>
    </Tooltip>
  );
}

const INTEGRATION_TIPS = [
  'Tu repo en la máquina. Hacia Gitea: git push (→).',
  'Código y Jenkinsfile en el remoto. Jenkins clona desde aquí (→).',
  'Pipeline desde SCM: clone + Jenkinsfile (→).',
  'Maven: empaquetado en una etapa del mismo pipeline (→).',
  'Mismo pipeline tras el build: demo en :8111 local; producción: SSH/scp a la app (→).',
] as const;

const INTEGRATION_TONES = [T.amber, T.emerald, T.sky, T.violet, T.rose] as const;

/** Código local → Gitea → Jenkins → Build → Deploy */
export function IntegrationFaithfulFlowDiagram({
  className,
  scale = 'default',
}: {
  className?: string;
  /** `large`: animación más grande (landing hero). */
  scale?: 'default' | 'large';
}) {
  const large = scale === 'large';
  const containerRef = useRef<HTMLDivElement>(null);
  const refs = [
    useRef<HTMLDivElement>(null),
    useRef<HTMLDivElement>(null),
    useRef<HTMLDivElement>(null),
    useRef<HTMLDivElement>(null),
    useRef<HTMLDivElement>(null),
  ];

  const nodes = [
    { label: 'Código local', Icon: FolderGit },
    { label: 'Gitea', Icon: Server },
    { label: 'Jenkins', Icon: Layers },
    { label: 'Build', Icon: Package },
    { label: 'Deploy', Icon: Rocket },
  ] as const;

  return (
    <TooltipProvider delayDuration={180}>
      <div className={cn('w-full', className)}>
        <div
          ref={containerRef}
          className={cn(
            'relative w-full overflow-x-auto overflow-y-visible pl-4 pr-8 md:overflow-hidden',
            large
              ? 'min-h-[240px] py-10 md:min-h-[300px] md:py-14'
              : 'min-h-[200px] py-8 md:py-10'
          )}
        >
          <div
            className={cn(
              'relative mx-auto flex max-w-5xl flex-row items-center justify-between',
              large ? 'min-w-[640px] gap-2 md:min-w-0 md:gap-4' : 'min-w-[600px] max-w-4xl gap-1 md:min-w-0 md:gap-2'
            )}
          >
            {nodes.map((n, i) => (
              <NodeWithTip
                key={n.label}
                refEl={refs[i]}
                toneClass={INTEGRATION_TONES[i]}
                icon={n.Icon}
                label={n.label}
                tip={INTEGRATION_TIPS[i]}
                nodeSize={large ? 'lg' : 'md'}
              />
            ))}
          </div>
          {refs.slice(0, -1).map((_, i) => (
            <AnimatedBeam
              key={i}
              containerRef={containerRef}
              fromRef={refs[i]}
              toRef={refs[i + 1]}
              curvature={large ? (i % 2 === 0 ? -20 : 20) : i % 2 === 0 ? -14 : 14}
              delay={i * 0.25}
              {...flowBeamProps}
            />
          ))}
        </div>
      </div>
    </TooltipProvider>
  );
}

function FlowWithUserBidirectional({
  mainNodes,
  mainIcons,
  mainTips,
  mainTones,
  userLabel,
  userTip,
  userTone,
  className,
}: {
  mainNodes: readonly string[];
  mainIcons: ReadonlyArray<React.ComponentType<{ className?: string }>>;
  mainTips: readonly string[];
  mainTones: readonly string[];
  userLabel: string;
  userTip: string;
  userTone: string;
  className?: string;
}) {
  const containerRef = useRef<HTMLDivElement>(null);
  const m0 = useRef<HTMLDivElement>(null);
  const m1 = useRef<HTMLDivElement>(null);
  const m2 = useRef<HTMLDivElement>(null);
  const m3 = useRef<HTMLDivElement>(null);
  const m4 = useRef<HTMLDivElement>(null);
  const userRef = useRef<HTMLDivElement>(null);
  const slotRefs = [m0, m1, m2, m3, m4];
  const n = mainNodes.length;
  const mainRefs = slotRefs.slice(0, n);
  const appOrPosIndex = n - 1;

  return (
    <TooltipProvider delayDuration={180}>
      <div
        ref={containerRef}
        className={cn(
          'relative min-h-[260px] w-full overflow-x-auto py-8 md:overflow-hidden md:py-10',
          className
        )}
      >
        <div className="mx-auto flex min-w-[500px] max-w-3xl flex-row flex-wrap items-start justify-center gap-5 md:min-w-0 md:flex-nowrap md:justify-between md:gap-6">
          {mainNodes.slice(0, -1).map((label, i) => {
            const Icon = mainIcons[i] ?? Package;
            return (
              <NodeWithTip
                key={label}
                refEl={mainRefs[i]}
                toneClass={mainTones[i] ?? T.sky}
                icon={Icon}
                label={label}
                tip={mainTips[i] ?? ''}
              />
            );
          })}
          <div className="flex flex-col items-center gap-8">
            <NodeWithTip
              refEl={mainRefs[appOrPosIndex]}
              toneClass={mainTones[appOrPosIndex] ?? T.teal}
              icon={mainIcons[appOrPosIndex] ?? Globe}
              label={mainNodes[appOrPosIndex]}
              tip={mainTips[appOrPosIndex] ?? ''}
            />
            <NodeWithTip
              refEl={userRef}
              toneClass={userTone}
              icon={User}
              label={userLabel}
              tip={userTip}
            />
          </div>
        </div>
        {mainNodes.slice(0, -1).map((_, i) => {
          if (i >= appOrPosIndex - 1) return null;
          return (
            <AnimatedBeam
              key={`m-${i}`}
              containerRef={containerRef}
              fromRef={mainRefs[i]}
              toRef={mainRefs[i + 1]}
              curvature={10}
              delay={i * 0.2}
              {...flowBeamProps}
            />
          );
        })}
        <AnimatedBeam
          containerRef={containerRef}
          fromRef={mainRefs[appOrPosIndex - 1]}
          toRef={mainRefs[appOrPosIndex]}
          curvature={-8}
          delay={0.4}
          {...flowBeamProps}
        />
        <AnimatedBeam
          containerRef={containerRef}
          fromRef={mainRefs[appOrPosIndex]}
          toRef={userRef}
          curvature={0}
          startYOffset={0}
          endYOffset={0}
          delay={0.6}
          {...flowBeamProps}
        />
        <AnimatedBeam
          containerRef={containerRef}
          fromRef={userRef}
          toRef={mainRefs[appOrPosIndex]}
          reverse
          curvature={0}
          startYOffset={0}
          endYOffset={0}
          delay={0.9}
          {...flowBeamProps}
        />
      </div>
    </TooltipProvider>
  );
}

const DEMO_ICONS = [User, Server, Container, Globe] as const;
const DEMO_TONES = [T.orange, T.emerald, T.blue, T.teal, T.cyan] as const;
const DEMO_TIPS = [
  'Push a Gitea (→). Flujo unidireccional.',
  'Jenkins obtiene el repo: clone / SCM (→).',
  'Build y deploy en la misma EC2; app en puerto 8111 (→).',
  'App servida en :8111. Tráfico con el usuario: bidireccional (↔).',
] as const;

/** Demo: una EC2 con Jenkins + app :8111 */
export function DemoFlowFaithfulDiagram() {
  return (
    <FlowWithUserBidirectional
      mainNodes={['Desarrollador', 'Gitea', 'Jenkins', 'App :8111']}
      mainIcons={DEMO_ICONS}
      mainTips={DEMO_TIPS}
      mainTones={DEMO_TONES}
      userLabel="Usuario"
      userTip="Usa la app en el navegador. Conexión ↔ bidireccional con App :8111 (HTTP)."
      userTone={T.cyan}
    />
  );
}

const REAL_ICONS = [User, Server, Container, Package, Cloud] as const;
const REAL_TONES = [T.orange, T.emerald, T.blue, T.violet, T.indigo, T.cyan] as const;
const REAL_TIPS = [
  'git push a Gitea, rama main (→).',
  'Pipeline from SCM: clone del repo (→).',
  'Maven package u otras etapas (→).',
  'Artefacto listo; siguiente paso deploy a la app (→).',
  'EC2 dedicada a la app: scp del JAR, ssh java -jar, Nginx + HTTPS. Verificación con curl (→).',
] as const;

export function RealFlowFaithfulDiagram() {
  return (
    <FlowWithUserBidirectional
      mainNodes={['Desarrollador', 'Gitea', 'Jenkins', 'Build', 'App']}
      mainIcons={REAL_ICONS}
      mainTips={REAL_TIPS}
      mainTones={REAL_TONES}
      userLabel="Usuario"
      userTip="Acceso HTTPS a la app (recursos y finanzas). Tráfico ↔ bidireccional."
      userTone={T.cyan}
    />
  );
}

const TOPO_ICONS = [FolderGit, Server, Container, Package, Cloud] as const;
const TOPO_TIPS = [
  'Código en tu máquina; push a Gitea (→).',
  'Repositorio remoto; Jenkins clona (→).',
  'CI/CD: build y orquestación (→).',
  'Empaquetado Maven / artefacto (→).',
  'App: despliegue y servicio (→).',
] as const;

export function TopologyFlowFaithfulDiagram() {
  return (
    <FlowWithUserBidirectional
      mainNodes={['Local', 'Gitea', 'Jenkins', 'Build', 'App']}
      mainIcons={TOPO_ICONS}
      mainTips={TOPO_TIPS}
      mainTones={REAL_TONES}
      userLabel="Usuario"
      userTip="Cliente final ↔ app (HTTPS, bidireccional)."
      userTone={T.cyan}
    />
  );
}
