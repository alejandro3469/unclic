import { NextResponse } from 'next/server';
import { z } from 'zod';
import { createSmtpTransport, getSmtpConfigFromEnv } from '@/lib/email/smtp';
import { sendLeadNotifications } from '@/lib/email/send-lead-notifications';

export const runtime = 'nodejs';

const bodySchema = z.object({
  email: z.string().email({ message: 'Correo no válido' }).max(320),
  /** Honeypot: debe ir vacío */
  website: z.string().max(200).optional(),
  /** Referencia opcional (ej. pathname) */
  source: z.string().max(500).optional(),
});

export async function POST(request: Request) {
  let json: unknown;
  try {
    json = await request.json();
  } catch {
    return NextResponse.json({ ok: false, error: 'invalid_json' }, { status: 400 });
  }

  const parsed = bodySchema.safeParse(json);
  if (!parsed.success) {
    return NextResponse.json(
      { ok: false, error: 'validation', details: parsed.error.flatten() },
      { status: 400 }
    );
  }

  const { email, website, source } = parsed.data;
  if (website && website.trim().length > 0) {
    // Bot llenó el campo oculto: finge éxito
    return NextResponse.json({ ok: true });
  }

  const smtp = getSmtpConfigFromEnv();
  if (!smtp) {
    return NextResponse.json({ ok: false, error: 'smtp_not_configured' }, { status: 503 });
  }

  const notifyTo = process.env.LEAD_NOTIFY_TO?.trim() || smtp.user;
  const fromName = process.env.LEAD_FROM_NAME?.trim() || 'UnClic';

  try {
    const transport = createSmtpTransport(smtp);
    await sendLeadNotifications(transport, {
      fromAddress: smtp.user,
      fromName,
      notifyTo,
      lead: { email: email.trim().toLowerCase(), source: source?.trim() },
    });
  } catch (e) {
    console.error('[lead-email]', e);
    return NextResponse.json({ ok: false, error: 'send_failed' }, { status: 502 });
  }

  return NextResponse.json({ ok: true });
}
