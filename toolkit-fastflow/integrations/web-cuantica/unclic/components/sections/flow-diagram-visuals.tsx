'use client';

/**
 * Diagramas de flujo — nodos + tooltips (shadcn Tooltip).
 * Conectores estáticos (ChevronRight / texto); sin Motion ni canvas.
 */

import React from 'react';
import { ChevronDown, ChevronRight } from 'lucide-react';
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from '@/components/ui/tooltip';
import { cn } from '@/lib/utils';
import { Server, Package, Layers, Rocket, FolderGit, User, Cloud, Globe, Container } from 'lucide-react';

/** Compat: antes se pasaba a AnimatedBeam; mantener export vacío por si algo importaba el objeto. */
export const flowBeamProps = {
  gradientStartColor: 'hsl(var(--primary))',
  gradientStopColor: 'hsl(var(--primary) / 0.55)',
  pathColor: 'hsl(var(--border) / 0.7)',
  duration: 3,
};

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

function FlowCircle({
  className,
  children,
  toneClass,
}: {
  className?: string;
  children?: React.ReactNode;
  toneClass?: string;
}) {
  return (
    <div
      className={cn(
        'z-10 flex shrink-0 cursor-default items-center justify-center rounded-full border-2 p-3 shadow-[0_4px_24px_-8px_rgba(0,0,0,0.35)] transition-transform hover:scale-[1.04]',
        toneClass ?? 'border-border/80 bg-muted/40 text-muted-foreground',
        className
      )}
    >
      {children}
    </div>
  );
}

function NodeWithTip({
  toneClass,
  icon: IconComp,
  label,
  tip,
  iconClass,
  nodeSize = 'md',
}: {
  toneClass: string;
  icon: React.ComponentType<{ className?: string }>;
  label: string;
  tip: string;
  iconClass?: string;
  nodeSize?: 'md' | 'lg';
}) {
  const isLg = nodeSize === 'lg';
  return (
    <Tooltip>
      <TooltipTrigger asChild>
        <div className={cn('flex flex-col items-center', isLg ? 'gap-2' : 'gap-1')}>
          <FlowCircle
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

export function IntegrationFaithfulFlowDiagram({
  className,
  scale = 'default',
}: {
  className?: string;
  scale?: 'default' | 'large';
}) {
  const large = scale === 'large';
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
          className={cn(
            'mx-auto flex w-full max-w-5xl flex-wrap items-center justify-center gap-2 py-8 md:flex-nowrap md:justify-between md:gap-3',
            large ? 'min-h-[200px] md:min-h-[260px] md:py-14' : 'min-h-[160px] md:py-10'
          )}
        >
          {nodes.map((n, i) => (
            <React.Fragment key={n.label}>
              <NodeWithTip
                toneClass={INTEGRATION_TONES[i]}
                icon={n.Icon}
                label={n.label}
                tip={INTEGRATION_TIPS[i]}
                nodeSize={large ? 'lg' : 'md'}
              />
              {i < nodes.length - 1 ? (
                <ChevronRight
                  className="hidden size-5 shrink-0 text-muted-foreground md:block"
                  aria-hidden
                />
              ) : null}
            </React.Fragment>
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
  const n = mainNodes.length;
  const appIndex = n - 1;
  const chain = mainNodes.slice(0, appIndex);
  const chainIcons = mainIcons.slice(0, appIndex);
  const chainTips = mainTips.slice(0, appIndex);
  const chainTones = mainTones.slice(0, appIndex);

  return (
    <TooltipProvider delayDuration={180}>
      <div
        className={cn(
          'flex w-full flex-col items-center gap-6 overflow-x-auto py-8 md:py-10',
          className
        )}
      >
        <div className="flex min-w-0 flex-wrap items-center justify-center gap-2 md:gap-3">
          {chain.map((label, i) => (
            <React.Fragment key={label}>
              <NodeWithTip
                toneClass={chainTones[i] ?? T.sky}
                icon={chainIcons[i] ?? Package}
                label={label}
                tip={chainTips[i] ?? ''}
              />
              {i < chain.length - 1 ? (
                <ChevronRight className="hidden size-5 shrink-0 text-muted-foreground sm:block" aria-hidden />
              ) : null}
            </React.Fragment>
          ))}
        </div>

        <ChevronDown className="size-5 text-muted-foreground" aria-hidden />

        <NodeWithTip
          toneClass={mainTones[appIndex] ?? T.teal}
          icon={mainIcons[appIndex] ?? Globe}
          label={mainNodes[appIndex]}
          tip={mainTips[appIndex] ?? ''}
        />

        <p className="text-xs font-medium text-muted-foreground">↔</p>

        <NodeWithTip
          toneClass={userTone}
          icon={User}
          label={userLabel}
          tip={userTip}
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
