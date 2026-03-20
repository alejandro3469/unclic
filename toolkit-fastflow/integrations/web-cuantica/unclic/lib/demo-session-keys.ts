/** Claves sessionStorage para gate de demos (mismo contrato en login, /demo/access, DemoGate). */

export const DEMO_ACCESS_KEY = 'unclic_demo_access';
/** Alcance: pos = solo app; full = Jenkins + Gitea + app. */
export const DEMO_SCOPE_KEY = 'unclic_demo_scope';
/** Correo usado en /login; obligatorio en modo allowlist para validar sesión. */
export const DEMO_LOGIN_EMAIL_KEY = 'unclic_login_email';
/** Solo se escribe con ?unlock=TOKEN válido en modo allowlist (preview sin correo). */
export const DEMO_BYPASS_KEY = 'unclic_demo_bypass';
