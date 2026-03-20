# Buenas Prácticas: Testing y Seguridad en el Flujo de Software

Este documento resume las lecciones clave de la literatura experta (JUnit in Action, Spring Security in Action, Testing Java Microservices) aplicadas al toolkit FastFlow.

## 1. La Seguridad como "Definition of Done"
No se puede considerar una tarea finalizada si sus pruebas de seguridad no están listas. La seguridad no es una etapa final; debe considerarse desde el primer diseño.

### 1.1 Pruebas de Autorización en Aislamiento
Es una mala práctica repetir la autenticación completa para probar cada regla de autorización.
- **Mock Users**: Usa `@WithMockUser` para saltarte el proceso de autenticación y enfocarte en validar si el usuario tiene los roles/permisos adecuados para el endpoint.
- **MockMvc**: Utiliza MockMvc para simular llamadas a la API sin levantar un servidor real, acelerando el feedback.

### 1.2 Pruebas de Integración con Dependencias
Tu código no vive solo. Depende de frameworks (Spring Security, Hibernate) y librerías externas.
- **Regression Testing**: Los tests deben asegurar que una actualización de versión de Spring Security no rompa tus configuraciones de CORS o CSRF.
- **Data Sources Reales**: Usa `@WithUserDetails` cuando necesites probar la integración real con tu base de datos de usuarios.

## 2. El Ciclo de Retroalimentación (Feedback Loop)
Cada `git push` debe disparar un flujo que notifique inmediatamente si algo se rompió.

| Etapa | Objetivo | Herramienta |
|-------|----------|-------------|
| **Unit Test** | Lógica de negocio aislada | JUnit 5 / Mockito |
| **Integration Test** | Conexión con DB / Frameworks | Spring Boot Test |
| **Security Test** | Autorización, CSRF, CORS | Spring Security Test |
| **Static Analysis** | Calidad de código y vulnerabilidades | SonarQube / Lint |

## 3. Prácticas Recomendadas (Checklist)
- [ ] ¿Los tests fallan si intento acceder sin autenticación (401 Unauthorized)?
- [ ] ¿Los tests validan que un usuario con rol 'USER' no puede acceder a rutas de 'ADMIN' (403 Forbidden)?
- [ ] ¿Se prueban las integraciones con librerías externas después de una actualización?
- [ ] ¿Se utiliza `@WithMockUser` para pruebas rápidas de autorización?
- [ ] ¿El pipeline de Jenkins detiene el despliegue si un test de seguridad falla?

---
*Basado en "Spring Security in Action" por Laurentiu Spilca y "JUnit in Action" por Cătălin Tudose.*
