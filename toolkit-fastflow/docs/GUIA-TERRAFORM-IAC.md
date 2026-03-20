# Guía de Infrastructure as Code con Terraform (Capa 19 de FastFlow)

En FastFlow, la infraestructura no se instala, se despliega mediante código. **Terraform** es nuestra herramienta principal para aplicar el paradigma de **Infrastructure as Code (IaC)**.

## 1. ¿Qué es Infrastructure as Code (IaC)?
Es la gestión y aprovisionamiento de infraestructura (redes, máquinas virtuales, balanceadores, bases de datos) a través de archivos de configuración legibles, en lugar de procesos manuales.
- **Repetibilidad**: Puedes crear 10 entornos idénticos con un solo comando.
- **Auto-documentación**: El código de Terraform ES la documentación de tu arquitectura.
- **Control de Versiones**: Al igual que el código de la aplicación, tu infraestructura vive en Git.

## 2. Terraform: El Orquestador Universal
FastFlow utiliza Terraform porque es agnóstico a la nube (AWS, Azure, Google Cloud, On-premise).
- **HCL (HashiCorp Configuration Language)**: Un lenguaje declarativo simple para describir recursos.
- **State File (`.tfstate`)**: Terraform mantiene un registro del estado real de tu infraestructura para saber qué cambios aplicar.

## 3. Flujo de Trabajo FastFlow con Terraform
1. **Write**: Escribir los archivos `.tf` describiendo el estado deseado (ej: una VPC con 4 subnets).
2. **Init**: Descargar los plugins necesarios para la nube elegida.
3. **Plan**: Ver una previsualización de lo que Terraform va a crear, modificar o destruir (Dry run).
4. **Apply**: Ejecutar los cambios reales en el proveedor de nube.

## 4. Mejores Prácticas de FastFlow
- **Modularidad**: No escribas un solo archivo gigante. Divide tu infraestructura en módulos (Red, Base de Datos, Cómputo).
- **Variables**: Usa variables para que tu código sea portable entre entornos (Desarrollo, QA, Producción).
- **Remote State**: En equipos, guarda el archivo de estado en un lugar centralizado (como un bucket S3) con bloqueo para evitar conflictos.

## 5. Seguridad en IaC
- **Least Privilege**: Terraform debe usar credenciales con los permisos mínimos necesarios.
- **Secret Management**: NUNCA guardes contraseñas o llaves API en los archivos `.tf`. Usa variables de entorno o un gestor de secretos (como AWS Secrets Manager).

---
*La infraestructura manual es frágil. La infraestructura como código es resiliente y escalable.*
