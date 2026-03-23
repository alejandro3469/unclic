/**
 * Copy para captura de leads (solo correo) + correos transaccionales vía Gmail SMTP.
 */
export const leadEmailForm = {
  title: 'Déjanos tu correo',
  description:
    'A: “¿y ahora qué?”. B: siguiente paso por correo (demo o propuesta) sin ruido.',
  label: 'Correo electrónico',
  placeholder: 'tu@empresa.com',
  submit: 'Enviar',
  submitting: 'Enviando…',
  success:
    'Listo. Revisa tu bandeja (y spam): te enviamos un correo de confirmación. Te contactamos pronto.',
  errorGeneric: 'No se pudo enviar. Intenta de nuevo o escríbenos directamente.',
  errorConfig:
    'El formulario no está configurado en el servidor. Si eres el administrador, revisa las variables de entorno de Gmail.',
  privacyNote: 'Al enviar aceptas que usemos tu correo solo para responder a tu interés.',
} as const;
