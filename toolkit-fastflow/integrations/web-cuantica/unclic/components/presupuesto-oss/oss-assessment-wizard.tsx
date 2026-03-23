'use client';

import { useCallback, useEffect, useMemo, useState } from 'react';
import { ArrowLeft, ArrowRight, Calculator, Loader2 } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { getUnclicApiBase } from '@/lib/api-base';
import { presupuestoOssPage } from '@/lib/copy-presupuesto-oss';
import { cn } from '@/lib/utils';

type Option = { value: string; label: string; hint?: string };
type Question = {
  id: string;
  type: 'single' | 'multi';
  title: string;
  description?: string;
  options: Option[];
  minSelections?: number;
  maxSelections?: number;
};

type AnswersState = Record<string, string | string[] | undefined>;

type EstimatePayload = {
  ok: boolean;
  estimate?: {
    disclaimer: string;
    indicativeMonthlyInfra: { low: number; high: number; currency: string };
    indicativeImplementationOnce: { low: number; high: number; currency: string };
    alignment: { currentPhaseHint: string; targetPhases: string[] };
    phases: Array<{
      id: string;
      title: string;
      summary: string;
      effortWeeks: { low: number; high: number; unit: string };
    }>;
    stackSuggestions: string[];
    howWeWouldAchieve: string[];
    risksAndGaps: string[];
  };
  error?: string;
};

export function OssAssessmentWizard() {
  const apiBase = getUnclicApiBase();
  const [questions, setQuestions] = useState<Question[]>([]);
  const [loadErr, setLoadErr] = useState<string | null>(null);
  const [step, setStep] = useState(0);
  const [answers, setAnswers] = useState<AnswersState>({});
  const [contextNotes, setContextNotes] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [result, setResult] = useState<EstimatePayload['estimate'] | null>(null);
  const [submitErr, setSubmitErr] = useState<string | null>(null);

  useEffect(() => {
    if (!apiBase) {
      setLoadErr(presupuestoOssPage.missingApiUrl);
      return;
    }
    let cancelled = false;
    (async () => {
      try {
        const res = await fetch(`${apiBase}/v1/oss-assessment/schema`);
        const data = (await res.json()) as { questions?: Question[] };
        if (!res.ok || !data.questions?.length) {
          throw new Error('schema');
        }
        if (!cancelled) {
          setQuestions(data.questions);
          setLoadErr(null);
        }
      } catch {
        if (!cancelled) setLoadErr(presupuestoOssPage.schemaLoadError);
      }
    })();
    return () => {
      cancelled = true;
    };
  }, [apiBase]);

  const q = questions[step];
  const isLast = step >= questions.length - 1;

  const setSingle = useCallback((id: string, value: string) => {
    setAnswers((a) => ({ ...a, [id]: value }));
  }, []);

  const toggleMulti = useCallback((id: string, value: string, max?: number) => {
    setAnswers((a) => {
      const cur = (a[id] as string[] | undefined) ?? [];
      const has = cur.includes(value);
      const next = has ? cur.filter((x) => x !== value) : [...cur, value];
      if (max && next.length > max) return a;
      return { ...a, [id]: next };
    });
  }, []);

  const canAdvance = useMemo(() => {
    if (!q) return false;
    if (q.type === 'single') return Boolean(answers[q.id]);
    const arr = (answers[q.id] as string[] | undefined) ?? [];
    const min = q.minSelections ?? 1;
    return arr.length >= min;
  }, [q, answers]);

  const submit = useCallback(async () => {
    if (!apiBase) return;
    setSubmitting(true);
    setSubmitErr(null);
    try {
      const body: Record<string, unknown> = {};
      for (const qq of questions) {
        const v = answers[qq.id];
        if (qq.type === 'single') body[qq.id] = v;
        else body[qq.id] = v ?? [];
      }
      if (contextNotes.trim()) body.contextNotes = contextNotes.trim();

      const res = await fetch(`${apiBase}/v1/oss-assessment/estimate`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      });
      const data = (await res.json()) as EstimatePayload;
      if (!res.ok || !data.ok || !data.estimate) {
        setSubmitErr(presupuestoOssPage.estimateError);
        return;
      }
      setResult(data.estimate);
    } catch {
      setSubmitErr(presupuestoOssPage.estimateError);
    } finally {
      setSubmitting(false);
    }
  }, [apiBase, answers, contextNotes, questions]);

  if (loadErr) {
    return (
      <div className="rounded-xl border border-amber-500/30 bg-amber-500/5 p-6 text-sm text-foreground">
        <p className="font-medium">{presupuestoOssPage.configTitle}</p>
        <p className="mt-2 text-muted-foreground">{loadErr}</p>
        <pre className="mt-4 overflow-x-auto rounded-lg bg-muted p-3 text-xs">{presupuestoOssPage.envSnippet}</pre>
      </div>
    );
  }

  if (!q && questions.length === 0) {
    return (
      <div className="flex items-center gap-2 text-muted-foreground">
        <Loader2 className="size-5 animate-spin" />
        {presupuestoOssPage.loadingSchema}
      </div>
    );
  }

  if (result) {
    return (
      <div className="space-y-8">
        <div className="rounded-xl border border-primary/20 bg-primary/5 p-6">
          <h2 className="flex items-center gap-2 text-lg font-semibold">
            <Calculator className="size-5" />
            {presupuestoOssPage.resultTitle}
          </h2>
          <p className="mt-2 text-sm text-muted-foreground">{result.disclaimer}</p>
          <div className="mt-6 grid gap-4 sm:grid-cols-2">
            <div className="rounded-lg border border-border bg-card p-4">
              <p className="text-xs font-medium uppercase text-muted-foreground">
                {presupuestoOssPage.monthlyInfra}
              </p>
              <p className="mt-1 text-2xl font-semibold tabular-nums">
                ${result.indicativeMonthlyInfra.low.toLocaleString()} – $
                {result.indicativeMonthlyInfra.high.toLocaleString()}
                <span className="text-sm font-normal text-muted-foreground"> / {presupuestoOssPage.month}</span>
              </p>
            </div>
            <div className="rounded-lg border border-border bg-card p-4">
              <p className="text-xs font-medium uppercase text-muted-foreground">
                {presupuestoOssPage.implOnce}
              </p>
              <p className="mt-1 text-2xl font-semibold tabular-nums">
                ${result.indicativeImplementationOnce.low.toLocaleString()} – $
                {result.indicativeImplementationOnce.high.toLocaleString()}
              </p>
              <p className="mt-1 text-xs text-muted-foreground">{presupuestoOssPage.implFootnote}</p>
            </div>
          </div>
        </div>

        <div>
          <h3 className="text-base font-semibold">{presupuestoOssPage.alignmentTitle}</h3>
          <p className="mt-2 text-sm text-muted-foreground">{result.alignment.currentPhaseHint}</p>
          <p className="mt-2 text-sm">
            <span className="font-medium">{presupuestoOssPage.targetPhases} </span>
            {result.alignment.targetPhases.join(', ')}
          </p>
        </div>

        <div>
          <h3 className="text-base font-semibold">{presupuestoOssPage.phasesTitle}</h3>
          <ul className="mt-3 space-y-3">
            {result.phases.map((p) => (
              <li key={p.id} className="rounded-lg border border-border p-4 text-sm">
                <span className="font-mono text-xs text-primary">{p.id}</span>
                <span className="ml-2 font-medium">{p.title}</span>
                <p className="mt-1 text-muted-foreground">{p.summary}</p>
                <p className="mt-2 text-xs text-muted-foreground">
                  {presupuestoOssPage.effort}: {p.effortWeeks.low}–{p.effortWeeks.high} {presupuestoOssPage.weeks}
                </p>
              </li>
            ))}
          </ul>
        </div>

        <div>
          <h3 className="text-base font-semibold">{presupuestoOssPage.stackTitle}</h3>
          <ul className="mt-2 list-inside list-disc text-sm text-muted-foreground">
            {result.stackSuggestions.map((s, i) => (
              <li key={i}>{s}</li>
            ))}
          </ul>
        </div>

        <div>
          <h3 className="text-base font-semibold">{presupuestoOssPage.howTitle}</h3>
          <ul className="mt-2 list-inside list-decimal text-sm text-muted-foreground">
            {result.howWeWouldAchieve.map((s, i) => (
              <li key={i}>{s}</li>
            ))}
          </ul>
        </div>

        <div>
          <h3 className="text-base font-semibold">{presupuestoOssPage.risksTitle}</h3>
          <ul className="mt-2 list-inside list-disc text-sm text-muted-foreground">
            {result.risksAndGaps.map((s, i) => (
              <li key={i}>{s}</li>
            ))}
          </ul>
        </div>

        <Button
          type="button"
          variant="outline"
          onClick={() => {
            setResult(null);
            setStep(0);
            setAnswers({});
            setContextNotes('');
          }}
        >
          {presupuestoOssPage.restart}
        </Button>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between text-xs text-muted-foreground">
        <span>
          {presupuestoOssPage.stepOf} {step + 1} / {questions.length}
        </span>
      </div>

      <div>
        <h2 className="text-xl font-semibold tracking-tight">{q.title}</h2>
        {q.description && <p className="mt-2 text-sm text-muted-foreground">{q.description}</p>}
        <div className="mt-4 flex flex-col gap-2">
          {q.type === 'single' &&
            q.options.map((opt) => (
              <button
                key={opt.value}
                type="button"
                onClick={() => setSingle(q.id, opt.value)}
                className={cn(
                  'rounded-lg border px-4 py-3 text-left text-sm transition hover:bg-muted/50',
                  answers[q.id] === opt.value
                    ? 'border-primary bg-primary/10'
                    : 'border-border bg-card'
                )}
              >
                <span className="font-medium">{opt.label}</span>
                {opt.hint && <span className="mt-1 block text-xs text-muted-foreground">{opt.hint}</span>}
              </button>
            ))}
          {q.type === 'multi' && (
            <div className="flex flex-col gap-2">
              {q.options.map((opt) => {
                const selected = ((answers[q.id] as string[]) ?? []).includes(opt.value);
                return (
                  <button
                    key={opt.value}
                    type="button"
                    onClick={() => toggleMulti(q.id, opt.value, q.maxSelections)}
                    className={cn(
                      'rounded-lg border px-4 py-3 text-left text-sm transition hover:bg-muted/50',
                      selected ? 'border-primary bg-primary/10' : 'border-border bg-card'
                    )}
                  >
                    {opt.label}
                  </button>
                );
              })}
              <p className="text-xs text-muted-foreground">{presupuestoOssPage.multiHint}</p>
            </div>
          )}
        </div>
      </div>

      {isLast && (
        <div>
          <label className="text-sm font-medium" htmlFor="oss-context">
            {presupuestoOssPage.contextLabel}
          </label>
          <textarea
            id="oss-context"
            className="mt-2 min-h-[100px] w-full rounded-lg border border-input bg-background px-3 py-2 text-sm"
            value={contextNotes}
            onChange={(e) => setContextNotes(e.target.value)}
            placeholder={presupuestoOssPage.contextPlaceholder}
          />
        </div>
      )}

      {submitErr && <p className="text-sm text-destructive">{submitErr}</p>}

      <div className="flex flex-wrap gap-2">
        <Button type="button" variant="outline" disabled={step === 0} onClick={() => setStep((s) => s - 1)}>
          <ArrowLeft className="mr-2 size-4" />
          {presupuestoOssPage.back}
        </Button>
        {!isLast ? (
          <Button type="button" disabled={!canAdvance} onClick={() => setStep((s) => s + 1)}>
            {presupuestoOssPage.next}
            <ArrowRight className="ml-2 size-4" />
          </Button>
        ) : (
          <Button type="button" disabled={!canAdvance || submitting} onClick={() => void submit()}>
            {submitting ? <Loader2 className="size-4 animate-spin" /> : presupuestoOssPage.calculate}
          </Button>
        )}
      </div>
    </div>
  );
}
