# Microservices / Security - Valoraciones Curadas

## Microservices Security in Action

| Capítulo | Calificación | Nota breve |
|---|---:|---|
| 7 | 9.4 | OAuth2/OIDC aplicado a seguridad de APIs |
| 8 | 9.2 | JWT con foco práctico |
| 9 | 9.5 | Seguridad en SPA y frontend |
| 10 | 9.6 | Docker hardening y operación segura |
| 11 | 9.7 | Kubernetes security bien ejecutado |
| 12 | 9.8 | Istio/mTLS/JWT/policies de alto nivel |
| 13 | 9.4 | Secure coding + análisis estático/dinámico |

## Microservices in Action

| Capítulo | Calificación | Nota breve |
|---|---:|---|
| 1 | 9.3 | Excelente introducción conceptual-operativa |
| 2 | 9.5 | Muy sólido en diseño de feature y production readiness |
| 3 | 9.4 | Excelente marco arquitectónico de 4 capas y patrones de boundary/comunicación |
| 4 | 9.7 | Capítulo clave: scoping por capacidad/uso/volatilidad con tácticas reales de migración y ownership |
| 5 | 9.8 | Sobresaliente en consistencia distribuida: sagas, compensaciones, CQRS y trade-offs reales de disponibilidad |
| 6 | 9.9 | De los mejores: resiliencia práctica (retries, timeouts, circuit breakers, chaos/load testing, service mesh) con criterio operativo real |
| 7 | 9.4 | Muy útil para escalar equipos: microservice chassis, estándares compartidos y bootstrapping rápido sin perder autonomía |
| 8 | 9.6 | Muy sólido en despliegue real: artefactos inmutables, modelos de hosting, health checks, canary/rolling y fundamentos de plataforma |
| 9 | 9.8 | Excelente capítulo operativo: Docker + Kubernetes de punta a punta (pods, services, health checks, canary y rollback) |
| 10 | 9.9 | De primer nivel para entrega continua: Jenkins + Kubernetes, staging/producción, canary, rollback y separación deploy vs release |
| 11 | 9.7 | Excelente para observabilidad operativa: golden signals, Prometheus/Grafana, alertas accionables y correlación entre servicios |
| 12 | 9.8 | Muy fuerte en observabilidad profunda: logging estructurado (ELK/Fluentd), trazas distribuidas con Jaeger y correlación por request ID |
| 13 | 9.8 | Cierre excelente en cultura y organización: ownership, autonomía, on-call, diseño evolutivo y gobernanza para escalar microservicios |

## Spring Microservices in Action (2nd Edition)

| Capítulo | Calificación | Nota breve |
|---|---:|---|
| 1 | 9.1 | Muy buen onboarding de stack Spring y patrones operativos; algo amplio y más panorámico que profundo |
| 2 | 9.0 | Excelente mapa de Spring Cloud + 12-factor; útil y práctico, aunque mayormente introductorio y con poco detalle técnico profundo |
| 3 | 9.3 | Muy equilibrado entre arquitectura, desarrollo y DevOps; fuerte en diseño de límites y operación, con ejemplos útiles y accionables |
| 4 | 9.2 | Muy práctico para adopción de contenedores: Dockerfile, Compose y flujo build/run claros; algunas secciones quedan introductorias para escenarios avanzados |
| 5 | 9.5 | Excelente capítulo de configuración: separación config/código, Spring Cloud Config con filesystem/Git/Vault y cifrado de secretos con enfoque muy aplicable |
| 6 | 9.4 | Muy buen capítulo de service discovery: justifica trade-offs, aterriza Eureka + LoadBalancer y compara bien DiscoveryClient, RestTemplate y Feign |
| 7 | 9.6 | Capítulo sobresaliente en resiliencia: circuit breaker, fallback, bulkhead, retry y rate limiter con configuración realista y criterios operativos claros |
| 8 | 9.4 | Muy buen capítulo de gateway: routing automático/manual, predicados/filtros y trazabilidad con correlation IDs; enfoque práctico y reusable |
| 9 | 9.5 | Excelente capítulo de seguridad aplicada: OAuth2/OIDC + Keycloak con configuración end-to-end, propagación de tokens y buenas prácticas de hardening |
| 10 | 9.6 | Excelente capítulo de EDA: explica trade-offs síncrono vs asíncrono y aterriza Spring Cloud Stream + Kafka + Redis con caso real de caché distribuida |
| 11 | 9.7 | Sobresaliente en observabilidad distribuida: Sleuth + ELK + Zipkin con trazas HTTP/mensajería y spans custom para diagnóstico fino en producción |
| 12 | 9.4 | Muy buen cierre de delivery: CI/CD, infraestructura como código e inmutabilidad con Jenkins+AWS; alto valor práctico aunque más orientado a stack específico |
| A (Appendix A) | 9.1 | Muy buen compendio de prácticas (RMM, config externa, CI/CD, monitoreo, logging y API gateway); valioso como checklist, aunque menos profundo que los capítulos técnicos |
| B (Appendix B) | 8.8 | Buena explicación didáctica de grants y refresh tokens; pierde vigencia porque `password` e `implicit` hoy están desaconsejados frente a Authorization Code + PKCE |
| C (Appendix C) | 9.2 | Muy buen apéndice práctico de observabilidad (Actuator + Micrometer + Prometheus + Grafana) con pasos claros; le falta profundizar en alerting/SLO y hardening de producción |

## SonarQube in Action

| Capítulo | Calificación | Nota breve |
|---|---:|---|
| 2 | 9.2 | Excelente capítulo para convertir deuda técnica en hallazgos accionables: severidades, categorías de issues, perfiles de reglas y flujo de triage muy práctico |
| 3 | 9.3 | Muy sólido en testing práctico: cobertura (línea/rama), lectura a nivel archivo, calidad de tests e integración con IT; especialmente útil para alinear dev/QA |
| 4 | 9.1 | Muy útil para atacar duplicaciones con enfoque práctico (detección local/cross-project + refactor patterns); gran valor operativo aunque con ejemplos algo básicos |
| 5 | 9.0 | Buen capítulo para institucionalizar documentación útil (métricas, drilldown y proceso); aporta mucho en mantenibilidad aunque con menor profundidad técnica que testing/duplicaciones |
| 6 | 9.3 | Capítulo muy fuerte en diseño a nivel clase: combina McCabe, LCOM4, RFC y acoplamientos con criterios prácticos de refactor para reducir riesgo y complejidad |
| 7 | 9.4 | Excelente capítulo de arquitectura aplicada: DSM, ciclos, dependencias Maven y reglas arquitectónicas con enfoque muy accionable para gobernanza técnica |
| 8 | 9.2 | Muy valioso en estrategia de adopción: priorización de métricas, planes de remediación, trending e historia para sostener mejora continua sin frenar entregas |

## 100 Java Mistakes and How to Avoid Them

| Capítulo | Calificación | Nota breve |
|---|---:|---|
| 1 | 9.3 | Excelente arranque pragmático: combina static analysis, testing, mutation, dynamic analysis y assertions con trade-offs realistas para mejorar calidad desde el día uno |
| 10 | 9.4 | Capítulo sobresaliente por enfoque anti-falsos positivos en tests: muestra errores sutiles que hacen “pasar” pruebas inválidas y cómo blindarlas con prácticas modernas |

## Spring Security in Action (2nd Edition)

| Capítulo | Calificación | Nota breve |
|---|---:|---|
| 1 | 9.2 | Muy buena introducción estratégica: aterriza por qué seguridad importa, el alcance real de Spring Security y cómo pensar seguridad por capas desde el inicio |
| 2 | 9.4 | Capítulo excelente para arrancar en serio: explica defaults, arquitectura base y override de configuraciones con ejemplos claros y aplicables a proyectos reales |
| 3 | 9.4 | Muy sólido en fundamentos de identidad: explica UserDetails/UserDetailsService/UserDetailsManager con claridad y aterriza implementaciones reales (in-memory, JDBC y LDAP) |
| 4 | 9.3 | Muy buen capítulo técnico-práctico en manejo de credenciales: PasswordEncoder, estrategias delegadas y criptografía aplicada con enfoque útil para producción |
| 5 | 9.5 | Capítulo sobresaliente en arquitectura de filtros: enseña con mucha claridad cómo extender la cadena de seguridad sin romper orden, legibilidad ni mantenibilidad |
| 6 | 9.5 | Sobresaliente en autenticación avanzada: custom AuthenticationProvider, gestión de SecurityContext en concurrencia y configuración práctica de HTTP Basic y form login |
| 7 | 9.4 | Excelente base de autorización en endpoints: aclara muy bien authorities vs roles y cuándo usar hasAuthority/hasRole/access con criterio práctico de diseño |
| 8 | 9.3 | Muy buen capítulo de aplicación fina de reglas: requestMatchers por path/método, orden de matchers y uso de regex con enfoque realista de mantenibilidad |
| 9 | 9.5 | Capítulo sobresaliente en CSRF: explica muy bien el ataque, cuándo proteger y cómo personalizar repositorio/handler de token en escenarios reales |
