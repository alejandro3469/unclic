import { describe, expect, it } from 'vitest';
import { computeOssAssessment, ossAssessmentAnswersSchema } from './calculator.js';

describe('ossAssessmentAnswersSchema', () => {
  it('accepts minimal valid payload', () => {
    const r = ossAssessmentAnswersSchema.safeParse({
      phase: 'mvp',
      domain: 'saas',
      team: '3-5',
      needs: ['cicd', 'registry'],
      cloud: 'aws',
      sensitivity: 'balanced',
    });
    expect(r.success).toBe(true);
  });

  it('rejects empty needs', () => {
    const r = ossAssessmentAnswersSchema.safeParse({
      phase: 'mvp',
      domain: 'saas',
      team: '3-5',
      needs: [],
      cloud: 'aws',
      sensitivity: 'balanced',
    });
    expect(r.success).toBe(false);
  });
});

describe('computeOssAssessment', () => {
  it('returns bands and phases', () => {
    const out = computeOssAssessment(
      {
        phase: 'production',
        domain: 'retail_pos',
        team: '6-15',
        needs: ['cicd', 'registry', 'observability', 'k8s'],
        cloud: 'aws',
        sensitivity: 'enterprise',
      },
      'test'
    );
    expect(out.indicativeMonthlyInfra.low).toBeGreaterThan(0);
    expect(out.indicativeMonthlyInfra.high).toBeGreaterThan(out.indicativeMonthlyInfra.low);
    expect(out.phases.length).toBeGreaterThan(0);
    expect(out.stackSuggestions.length).toBeGreaterThan(0);
  });
});
