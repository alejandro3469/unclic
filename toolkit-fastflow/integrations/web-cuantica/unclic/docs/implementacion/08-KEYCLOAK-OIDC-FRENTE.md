# Keycloak (OIDC) delante de demos sensibles

## Qué resuelve

- Login único para `/demo/*`, paneles internos o subdominio `admin.`.
- Tokens JWT validados por el API o por el proxy (OAuth2 Proxy, Traefik forwardAuth, etc.).

Documentación oficial: [keycloak.org/documentation](https://www.keycloak.org/documentation).

## Lo que esta guía **no** hace

- No crea realm, clients ni users por ti (dependen de tu organización).
- No sustituye la guía de hardening de Keycloak.

## Patrones de integración (elegir uno)

1. **Proxy con OAuth2 Proxy** delante de rutas: Keycloak como IdP; upstream tu Next o API.
2. **Traefik + ForwardAuth** apuntando a servicio que valide sesión.
3. **API valida JWT**: Keycloak emite JWT; Hono middleware comprueba firma contra JWKS (`/.well-known/openid-configuration` del realm).

## Checklist

- [ ] HTTPS en Keycloak y en app.
- [ ] Client **confidential** o **public** según tipo de app (SPA vs server).
- [ ] Redirect URIs exactos (Keycloak es estricto).
- [ ] Rotación de client secret si aplica.

## Enlace con UnClic

- Sitio público de marketing puede seguir **sin** Keycloak.
- Solo proteger lo que de verdad es demo cerrada (alineado a matriz PLAN-OSS: Keycloak **Pendiente**).
