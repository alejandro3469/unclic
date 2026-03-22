import type { Transporter } from 'nodemailer';

export type LeadPayload = {
  email: string;
  source?: string;
};

function escapeHtml(s: string) {
  return s
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

export async function sendLeadNotifications(
  transport: Transporter,
  opts: {
    fromAddress: string;
    fromName: string;
    notifyTo: string;
    lead: LeadPayload;
  }
) {
  const { fromAddress, fromName, notifyTo, lead } = opts;
  const from = `"${fromName}" <${fromAddress}>`;
  const safeEmail = lead.email;
  const sourceLine = lead.source ? `\nOrigen: ${lead.source}` : '';

  await transport.sendMail({
    from,
    to: notifyTo,
    replyTo: safeEmail,
    subject: `[UnClic] Nuevo contacto: ${safeEmail}`,
    text: `Nuevo correo capturado en el sitio.\n\nCorreo: ${safeEmail}${sourceLine}\n`,
    html: `<p>Nuevo correo capturado en el sitio.</p><p><strong>Correo:</strong> ${escapeHtml(safeEmail)}</p>${lead.source ? `<p><strong>Origen:</strong> ${escapeHtml(lead.source)}</p>` : ''}`,
  });

  await transport.sendMail({
    from,
    to: safeEmail,
    replyTo: notifyTo,
    subject: 'Gracias por tu interés en UnClic',
    text: `Hola,

Gracias por dejarnos tu correo (${safeEmail}). Revisamos cada mensaje y te contactamos cuando encaje con lo que buscas.

— ${fromName}
`,
    html: `<p>Hola,</p>
<p>Gracias por dejarnos tu correo (<strong>${escapeHtml(safeEmail)}</strong>). Revisamos cada mensaje y te contactamos cuando encaje con lo que buscas.</p>
<p>— ${escapeHtml(fromName)}</p>`,
  });
}
