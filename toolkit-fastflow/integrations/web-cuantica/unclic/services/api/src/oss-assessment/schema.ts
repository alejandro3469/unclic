/**
 * Cuestionario versionado para estimación orientativa OSS + fases tipo FastFlow/UnClic.
 * Consumible vía GET /v1/oss-assessment/schema desde la web o apps self-hosted.
 */

export const OSS_ASSESSMENT_VERSION = '1.0.0' as const;

export type QuestionOption = { value: string; label: string; hint?: string };

export type OssAssessmentQuestion = {
  id: string;
  type: 'single' | 'multi';
  title: string;
  description?: string;
  options: QuestionOption[];
  /** multi: mínimo de selecciones */
  minSelections?: number;
  maxSelections?: number;
};

export const OSS_ASSESSMENT_QUESTIONS: OssAssessmentQuestion[] = [
  {
    id: 'phase',
    type: 'single',
    title: '¿En qué fase está tu producto o plataforma de software?',
    description:
      'Nos ayuda a calibrar riesgo, entorno productivo y profundidad de automatización.',
    options: [
      { value: 'idea', label: 'Idea / prueba de concepto', hint: 'Aún no hay usuarios pagando o en producción.' },
      { value: 'mvp', label: 'MVP en marcha', hint: 'Primeros usuarios, pocos entornos.' },
      { value: 'growth', label: 'Crecimiento', hint: 'Tráfico o equipos creciendo; más entornos.' },
      { value: 'production', label: 'Producción estable', hint: 'SLA, auditoría o retail crítico.' },
      { value: 'legacy', label: 'Legado a modernizar', hint: 'Deuda técnica, migración o paridad con nuevo stack.' },
    ],
  },
  {
    id: 'domain',
    type: 'single',
    title: '¿Cuál es el contexto principal del software?',
    options: [
      { value: 'retail_pos', label: 'Retail / POS / tiendas' },
      { value: 'ecommerce', label: 'E-commerce / omnicanal' },
      { value: 'internal', label: 'Herramientas internas / back-office' },
      { value: 'saas', label: 'SaaS B2B / producto propio' },
      { value: 'integration', label: 'Integración JDE / ERP / datos' },
      { value: 'other', label: 'Otro' },
    ],
  },
  {
    id: 'team',
    type: 'single',
    title: '¿Tamaño aproximado del equipo que toca el stack (dev + DevOps)?',
    options: [
      { value: '1-2', label: '1–2 personas' },
      { value: '3-5', label: '3–5' },
      { value: '6-15', label: '6–15' },
      { value: '15+', label: 'Más de 15' },
    ],
  },
  {
    id: 'needs',
    type: 'multi',
    title: '¿Qué necesidades quieres cubrir con open source (puedes marcar varias)?',
    description: 'Influye en coste recurrente de infra y en esfuerzo de implementación.',
    minSelections: 1,
    maxSelections: 12,
    options: [
      { value: 'cicd', label: 'CI/CD y pipeline as code (Jenkins, Gitea, etc.)' },
      { value: 'registry', label: 'Registry de imágenes y promoción de versiones' },
      { value: 'observability', label: 'Métricas, logs, alertas (Prometheus, Grafana…)' },
      { value: 'idp', label: 'Identidad / SSO (Keycloak, OIDC)' },
      { value: 'erp', label: 'ERP / back office (p. ej. ERPNext)' },
      { value: 'llm', label: 'LLM o agentes locales (Ollama, proxies)' },
      { value: 'k8s', label: 'Kubernetes o orquestación fuerte' },
      { value: 'data', label: 'Colas, eventos, integración datos (NATS, RabbitMQ…)' },
      { value: 'backup', label: 'Backup, DR, multi-región' },
      { value: 'compliance', label: 'Cumplimiento / hardening / segregación' },
      { value: 'mobile', label: 'Apps móviles o edge en roadmap' },
      { value: 'finops', label: 'FinOps, etiquetado, presupuestos cloud' },
    ],
  },
  {
    id: 'cloud',
    type: 'single',
    title: '¿Preferencia de despliegue para la primera fase?',
    options: [
      { value: 'aws', label: 'AWS (EC2, ALB, RDS opcional…)' },
      { value: 'multi', label: 'Multi-cloud o neutral' },
      { value: 'onprem', label: 'On-prem / datacenter propio' },
      { value: 'hybrid', label: 'Híbrido (on-prem + nube)' },
    ],
  },
  {
    id: 'sensitivity',
    type: 'single',
    title: 'Sensibilidad de presupuesto (orientativo, no compromiso comercial)',
    options: [
      { value: 'bootstrap', label: 'Mínimo gasto fijo; priorizar free tier y OSS' },
      { value: 'balanced', label: 'Equilibrado: coste vs tiempo al mercado' },
      { value: 'enterprise', label: 'Priorizar soporte, SLAs y reducir riesgo' },
    ],
  },
];
