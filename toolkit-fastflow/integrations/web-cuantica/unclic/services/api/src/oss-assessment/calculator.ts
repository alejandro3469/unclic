import { z } from 'zod';

/** Respuesta validada del cuestionario (POST /v1/oss-assessment/estimate). */
export const ossAssessmentAnswersSchema = z.object({
  phase: z.enum(['idea', 'mvp', 'growth', 'production', 'legacy']),
  domain: z.enum(['retail_pos', 'ecommerce', 'internal', 'saas', 'integration', 'other']),
  team: z.enum(['1-2', '3-5', '6-15', '15+']),
  needs: z
    .array(
      z.enum([
        'cicd',
        'registry',
        'observability',
        'idp',
        'erp',
        'llm',
        'k8s',
        'data',
        'backup',
        'compliance',
        'mobile',
        'finops',
      ])
    )
    .min(1)
    .max(12),
  cloud: z.enum(['aws', 'multi', 'onprem', 'hybrid']),
  sensitivity: z.enum(['bootstrap', 'balanced', 'enterprise']),
  /** Contexto libre opcional (no se usa en cálculo numérico). */
  contextNotes: z.string().max(4000).optional(),
});

export type OssAssessmentAnswers = z.infer<typeof ossAssessmentAnswersSchema>;

export type EstimateBand = { low: number; high: number; currency: 'USD' };

export type PhaseRow = {
  id: string;
  title: string;
  summary: string;
  effortWeeks: { low: number; high: number; unit: 'weeks' };
};

export type OssAssessmentResult = {
  version: string;
  disclaimer: string;
  /** Infra + licencias OSS (sin fee UnClic): orientativo mensual */
  indicativeMonthlyInfra: EstimateBand;
  /** Implementación / acompañamiento tipo consultoría (muy orientativo) */
  indicativeImplementationOnce: EstimateBand;
  /** Alineación con etapas E0–E5 del plan UnClic */
  alignment: { currentPhaseHint: string; targetPhases: string[] };
  phases: PhaseRow[];
  stackSuggestions: string[];
  howWeWouldAchieve: string[];
  risksAndGaps: string[];
};

const NEED_MONTHLY: Record<string, [number, number]> = {
  cicd: [15, 80],
  registry: [10, 60],
  observability: [25, 120],
  idp: [20, 150],
  erp: [40, 350],
  llm: [30, 400],
  k8s: [80, 900],
  data: [20, 200],
  backup: [15, 120],
  compliance: [30, 250],
  mobile: [10, 80],
  finops: [5, 40],
};

function phaseMultiplier(phase: OssAssessmentAnswers['phase']): number {
  const m: Record<OssAssessmentAnswers['phase'], number> = {
    idea: 0.45,
    mvp: 0.7,
    growth: 1,
    production: 1.35,
    legacy: 1.25,
  };
  return m[phase];
}

function teamMultiplier(team: OssAssessmentAnswers['team']): number {
  const m: Record<OssAssessmentAnswers['team'], number> = {
    '1-2': 0.85,
    '3-5': 1,
    '6-15': 1.2,
    '15+': 1.45,
  };
  return m[team];
}

function cloudMultiplier(cloud: OssAssessmentAnswers['cloud']): number {
  const m: Record<OssAssessmentAnswers['cloud'], number> = {
    aws: 1,
    multi: 1.15,
    onprem: 0.9,
    hybrid: 1.2,
  };
  return m[cloud];
}

function sensitivitySpread(s: OssAssessmentAnswers['sensitivity']): [number, number] {
  if (s === 'bootstrap') return [0.75, 0.95];
  if (s === 'enterprise') return [1.15, 1.55];
  return [0.95, 1.2];
}

export function computeOssAssessment(
  answers: OssAssessmentAnswers,
  version: string
): OssAssessmentResult {
  let lowM = 35;
  let highM = 120;

  for (const n of answers.needs) {
    const band = NEED_MONTHLY[n];
    if (band) {
      lowM += band[0];
      highM += band[1];
    }
  }

  const pm = phaseMultiplier(answers.phase);
  const tm = teamMultiplier(answers.team);
  const cm = cloudMultiplier(answers.cloud);
  const [sLow, sHigh] = sensitivitySpread(answers.sensitivity);

  lowM = Math.round(lowM * pm * tm * cm * sLow);
  highM = Math.round(highM * pm * tm * cm * sHigh);

  const implLow = Math.round((8 + answers.needs.length * 4) * tm * pm);
  const implHigh = Math.round((24 + answers.needs.length * 12) * tm * pm * (answers.sensitivity === 'enterprise' ? 1.35 : 1));

  const phaseHint =
    answers.phase === 'idea' || answers.phase === 'mvp'
      ? 'E0–E1: requisitos, repo, pipeline mínimo y entorno de prueba.'
      : answers.phase === 'growth'
        ? 'E2–E3: registry, promoción de imágenes, observabilidad básica, durabilidad.'
        : 'E3–E5: producción, seguridad perimetral, DR, multi-entorno y operación.';

  const targetPhases =
    answers.phase === 'production' || answers.phase === 'legacy'
      ? ['E3', 'E4', 'E5']
      : answers.phase === 'growth'
        ? ['E2', 'E3', 'E4']
        : ['E0', 'E1', 'E2'];

  const phases: PhaseRow[] = [
    {
      id: 'E0',
      title: 'Descubrimiento y alcance',
      summary: 'Inventario de sistemas, riesgos, y mapa OSS vs legado.',
      effortWeeks: { low: 1, high: 3, unit: 'weeks' },
    },
    {
      id: 'E1',
      title: 'Cimientos repo + CI',
      summary: 'Gitea/Jenkins (o equivalente), pipeline Maven/Docker, secretos.',
      effortWeeks: { low: 2, high: 6, unit: 'weeks' },
    },
    {
      id: 'E2',
      title: 'Registry y promoción',
      summary: 'Tags, entornos, rollback documentado.',
      effortWeeks: { low: 2, high: 5, unit: 'weeks' },
    },
    {
      id: 'E3',
      title: 'Producción mínima viable',
      summary: 'HTTPS, backups, monitorización, runbooks.',
      effortWeeks: { low: 3, high: 10, unit: 'weeks' },
    },
    {
      id: 'E4',
      title: 'Integración negocio',
      summary: 'ERP, identidad, colas según tu dominio.',
      effortWeeks: { low: 4, high: 16, unit: 'weeks' },
    },
    {
      id: 'E5',
      title: 'Escala y mejora continua',
      summary: 'K8s opcional, FinOps, DR, agentes/LLM acotados.',
      effortWeeks: { low: 4, high: 20, unit: 'weeks' },
    },
  ];

  const stackSuggestions: string[] = [];
  if (answers.needs.includes('cicd')) stackSuggestions.push('Gitea + Jenkins (FastFlow) o GitLab CE si unificáis Git+CI.');
  if (answers.needs.includes('registry')) stackSuggestions.push('Registry Docker (Harbor o registry ligero) con políticas de retención.');
  if (answers.needs.includes('observability')) stackSuggestions.push('Prometheus + Grafana; logs con Loki o stack equivalente.');
  if (answers.needs.includes('idp')) stackSuggestions.push('Keycloak u OIDC delante de apps sensibles.');
  if (answers.needs.includes('erp')) stackSuggestions.push('ERPNext u otro ERP OSS; integración por API/documentar límites.');
  if (answers.needs.includes('llm')) stackSuggestions.push('Ollama en VPC o on-prem; proxy sin exponer modelos a Internet.');
  if (answers.needs.includes('k8s')) stackSuggestions.push('k3s/EKS según tamaño; empezar por Compose/VM si el equipo es pequeño.');
  if (answers.needs.includes('data')) stackSuggestions.push('NATS, RabbitMQ o Kafka según volumen y patrón de mensajería.');
  if (stackSuggestions.length === 0) stackSuggestions.push('Empezar por repositorio + pipeline + un entorno reproducible.');

  const howWeWouldAchieve = [
    'Taller corto (1–2 sesiones) para validar fase real, dueños y SLAs.',
    'Backlog priorizado por riesgo: primero reproducibilidad del build y trazabilidad de imágenes.',
    'Entregables por sprint: pipeline verde, registry escribible, checklist de go-live.',
    'Si el dominio es retail/POS: alinear generic model, entornos JDE/Oracle y ventanas de despliegue.',
    'Conectar esta API desde vuestras herramientas (n8n, Mattermost, intranet) para re-ejecutar la estimación cuando cambie el alcance.',
  ];

  const risksAndGaps: string[] = [];
  if (answers.needs.includes('compliance') && answers.sensitivity === 'bootstrap') {
    risksAndGaps.push('Cumplimiento estricto suele chocar con “mínimo gasto”; prever auditoría y segregación desde E2.');
  }
  if (answers.domain === 'retail_pos' && !answers.needs.includes('cicd')) {
    risksAndGaps.push('POS/retail sin CI/CD reproducible incrementa riesgo en certificación y rollback.');
  }
  if (answers.team === '1-2' && answers.needs.length > 6) {
    risksAndGaps.push('Muchas capacidades con equipo muy pequeño: plantear externalizar parte o fasear en trimestres.');
  }
  if (risksAndGaps.length === 0) {
    risksAndGaps.push('Las cifras son indicativas; el presupuesto real depende de SLAs, volumen de transacciones y deuda técnica descubierta en E0.');
  }

  return {
    version,
    disclaimer:
      'Estimación automática orientativa. No es presupuesto contractual. Los importes cubren orden de magnitud de infraestructura OSS típica y esfuerzo de implementación; no incluyen licencias propietarias ni fee de terceros no listados.',
    indicativeMonthlyInfra: { low: lowM, high: highM, currency: 'USD' },
    indicativeImplementationOnce: { low: implLow * 800, high: implHigh * 1800, currency: 'USD' },
    alignment: { currentPhaseHint: phaseHint, targetPhases },
    phases,
    stackSuggestions,
    howWeWouldAchieve,
    risksAndGaps,
  };
}
