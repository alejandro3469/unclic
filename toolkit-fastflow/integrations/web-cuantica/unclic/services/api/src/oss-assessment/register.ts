import type { Hono } from 'hono';
import { OSS_ASSESSMENT_QUESTIONS, OSS_ASSESSMENT_VERSION } from './schema.js';
import {
  computeOssAssessment,
  ossAssessmentAnswersSchema,
} from './calculator.js';

export function registerOssAssessmentRoutes(app: Hono) {
  app.get('/v1/oss-assessment/schema', (c) =>
    c.json({
      ok: true,
      version: OSS_ASSESSMENT_VERSION,
      questions: OSS_ASSESSMENT_QUESTIONS,
    })
  );

  app.post('/v1/oss-assessment/estimate', async (c) => {
    let body: unknown;
    try {
      body = await c.req.json();
    } catch {
      return c.json({ ok: false, error: 'invalid_json' }, 400);
    }

    const parsed = ossAssessmentAnswersSchema.safeParse(body);
    if (!parsed.success) {
      return c.json(
        { ok: false, error: 'validation', details: parsed.error.flatten() },
        400
      );
    }

    const result = computeOssAssessment(parsed.data, OSS_ASSESSMENT_VERSION);
    return c.json({ ok: true, answers: parsed.data, estimate: result });
  });
}
