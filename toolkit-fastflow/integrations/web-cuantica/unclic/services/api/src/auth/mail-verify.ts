import { createTransport, getSmtpConfigFromEnv } from '../smtp.js';

export async function sendVerificationEmail(opts: {
  to: string;
  verifyUrl: string;
}): Promise<{ ok: true } | { ok: false; error: string }> {
  const smtp = getSmtpConfigFromEnv();
  if (!smtp) {
    return { ok: false, error: 'smtp_not_configured' };
  }

  const fromName = process.env.LEAD_FROM_NAME?.trim() || 'UnClic';
  const transport = createTransport(smtp);

  try {
    await transport.sendMail({
      from: `"${fromName}" <${smtp.user}>`,
      to: opts.to,
      subject: 'Verifica tu correo — Portal UnClic',
      text: `Confirma tu cuenta abriendo este enlace (válido 48 h):\n\n${opts.verifyUrl}\n\nSi no creaste la cuenta, ignora este mensaje.`,
      html: `<p>Confirma tu cuenta en el portal UnClic:</p><p><a href="${opts.verifyUrl}">${opts.verifyUrl}</a></p><p>El enlace caduca en 48 horas.</p>`,
    });
    return { ok: true };
  } catch (e) {
    console.error('[auth/mail-verify]', e);
    return { ok: false, error: 'send_failed' };
  }
}
