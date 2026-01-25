# Loans Manager API

Este proyecto es una API REST para la gestión de solicitudes de préstamos, construida siguiendo principios robustos de ingeniería de software y arquitecturas más actuales.

## ✨ Características Principales

- 🏗️ **Arquitectura Hexagonal**: Desacoplamiento total entre dominio e infraestructura.
- ⚡ **Alto Rendimiento**: Caché de segundo nivel orientada a optimizar lecturas frecuentes.
- 🧪 **Calidad Garantizada**: Cobertura de tests unitarios superior al 85%.
- 🗄️ **Control de Versiones de BD**: Gestión evolutiva y automatizada del esquema de datos.
- 🤖 **CI/CD Automatizado**: Pipelines de GitHub Actions para validación de código, tests y publicación técnica.
- 🐳 **Dockerización Eficiente**: Uso de _Multi-stage builds_ y _Layered JARs_ para optimizar el peso y la velocidad de despliegue.
- 📖 **Documentación Viva**: Swagger UI integrado para pruebas rápidas.

## 🚀 Instrucciones para Ejecutar el Proyecto

### Requisitos Previos

- Java 17 o superior.
- Maven 3.8 o superior.
- Docker (Opcional).

### Ejecución Local

1.  **Clonar el repositorio** y situarse en la raíz del proyecto.
2.  **Compilar y ejecutar**:
    ```bash
    ./mvnw spring-boot:run
    ```

### Ejecución con Docker (Alternativa)

Si prefieres no instalar Java/Maven localmente, puedes usar Docker:

1.  **Descargar la imagen** desde el registro de GitHub:
    ```bash
    docker pull docker pull ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:master
    ```
2.  **Ejecutar el contenedor**:
    ```bash
    docker run -p 8080:8080 docker pull ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:master
    ```

### Pasos para probar la API independientemente de su ejecución

3.  **Probar la API**:
    - Tienes ejemplos de peticiones listos para usar en [requests.http](file:///c:/Users/admin/Desktop/context/pruebatech/requests.http).
4.  **Documentación API (Swagger/OpenAPI)**:
    - Una vez en ejecución, tienes la documentación Swagger en el siguiente enlace: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
5.  **Consola de Base de Datos (H2)**:
    - Acceso en: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - JDBC URL: `jdbc:h2:mem:caixabank` | Usuario: `sa` | Password: (vacío)

---

## 🏗️ Arquitectura y Decisiones Técnicas

El proyecto ha sido diseñado bajo los siguientes pilares para asegurar mantenibilidad, escalabilidad y una estructura sólida:

### 1. Arquitectura Hexagonal (Ports & Adapters)

Se ha implementado una Arquitectura Hexagonal para aislar completamente la lógica de negocio (Dominio) de las dependencias externas (Infraestructura). Esto permite que el núcleo de la aplicación sea independiente de frameworks, bases de datos o interfaces de usuario, facilitando enormemente la realización de tests y la evolución tecnológica futura.

- **Domain**: El corazón del sistema, donde residen los modelos de negocio, reglas de validación y "Puertos" (interfaces) que definen las necesidades del dominio.
- **Infrastructure**: Contiene las implementaciones técnicas de los puertos (Adaptadores), como Spring Data JPA para persistencia, Spring Security para seguridad y los controladores REST.
- **Application**: Capa de orquestación que gestiona el flujo de datos entre los adaptadores y el dominio, implementando los casos de uso específicos.

### 2. Patrón CQRS (Command Query Responsibility Segregation)

Para optimizar la gestión de datos, se ha aplicado el patrón CQRS, separando las operaciones que modifican el estado del sistema de aquellas que simplemente lo consultan. Esta separación permite escalar cada tipo de operación de forma independiente y simplifica la lógica de los servicios:

- **Commands**: Encargados de las mutaciones de estado, como la creación de una solicitud de préstamo o el registro de un nuevo usuario.
- **Queries**: Optimizadas para la lectura y recuperación de información, como el listado de solicitudes o la consulta de una solicitud específica por ID.

### 3. Patrón Mediator

Se ha implementado un `Mediator` personalizado para lograr un desacoplamiento total entre la capa de presentación (Controllers) y los casos de uso (Application). Los controladores no invocan servicios directamente; en su lugar, envían un objeto de petición (`Request`) al mediador, el cual localiza y ejecuta el `RequestHandler` correspondiente. Esto reduce la complejidad de los controladores y permite añadir nuevos casos de uso sin modificar el código existente.

### 4. Consistencia de Datos y Manejo de Errores

Para garantizar la integridad de la información y una experiencia de usuario consistente, el proyecto utiliza:

- **Hibernate Validation**: Uso de anotaciones Bean Validation (@NotNull, @Min, @Pattern) para validar los DTOs de entrada en la frontera de la API.
- **ControllerAdvice**: Implementación de un manejador global de excepciones que captura errores de negocio y validación, transformándolos en respuestas JSON estandarizadas y amigables para el cliente.

### 5. Ejecución Ágil: H2, Caché y Flyway

Siguiendo los requisitos del enunciado para facilitar una prueba rápida y sin dependencias externas pesadas:

- **H2 Database**: Base de datos SQL en memoria que permite levantar el entorno sin configurar servidores externos.
- **Caché en Memoria**: Uso de la abstracción de caché de Spring para optimizar lecturas frecuentes y mejorar el tiempo de respuesta.
- **Flyway**: Herramienta de migración que asegura que el esquema de la base de datos H2 se cree y evolucione de forma consistente y automática en cada arranque.

### 6. Calidad de Código, Control de versiones,CI/CD Automatizado y Contenerización

La excelencia técnica se mantiene mediante herramientas integradas en el ciclo de vida de Maven y una pipeline de GitHub Actions:

- **JaCoCo**: Generación de informes de cobertura, configurado para garantizar que el código crítico mantenga una cobertura superior al 85%.
- **Git, Github y Gitflow**: Control de versiones y flujo de trabajo para el desarrollo de software.
- **GitHub Actions**: Automatización total que ejecuta los tests, verifica el formato y construye una imagen Docker optimizada cada vez que se sube código a ramas principales o de release.
- **Docker**: Uso de _Multi-stage builds_ y _Layered JARs_ para generar imágenes ligeras y seguras, listas para producción.

### 7. Otras Decisiones Técnicas

- **Lombok**: Para reducir el código repetitivo (_boilerplate_) en entidades y DTOs.
- **MapStruct**: Mapeadores automáticos de alto rendimiento para transferir datos entre capas sin exponer el modelo interno.
- **Swagger**: Documentación de la API.
- **Spring Actuator**: Endpoint de monitoreo para supervisar el estado y métricas de salud de la aplicación.

---

## 📈 Mejoras y Extensiones Futuras

Con el objetivo de llevar esta API a un nivel productivo de alta disponibilidad y escalabilidad, se proponen las siguientes líneas de evolución:

### Técnicas / Arquitecturales

- **Spring Security y JWT**: Implementación de seguridad para la autenticación y autorización segura mediante tokens.
- **Spotless**: Integración de la herramienta para mantener un formato de código uniforme y seguir estándares de Clean Code.
- **SonarQube & Checkmarx (Análisis de Código y Seguridad)**: Integración de herramientas SAST (Static Application Security Testing) en la pipeline de CI/CD para detectar proactivamente vulnerabilidades críticas, deuda técnica y asegurar el cumplimiento de estándares de calidad (Clean Code).
- **Redis (Caché Distribuida)**: Sustitución de la caché en memoria por un cluster de Redis. Esto permitiría mantener la consistencia de la caché en arquitecturas de microservicios con múltiples instancias elásticas y mejorar los tiempos de respuesta en lecturas masivas.
- **Persistencia en Producción (MySQL/PostgreSQL)**: Migración de H2 a una base de datos relacional robusta. Se implementaría mediante perfiles de Spring (`application-prod.yaml`) para asegurar la persistencia, integridad referencial avanzada y capacidades de backup/recovery.
- **Stack de Observabilidad (LGTM Stack)**: Explotación completa de los datos de **Spring Actuator** integrándolos con Grafana (visualización), Prometheus (métricas), Loki (logs centralizados) y Tempo (trazabilidad distribuida) para una monitorización 360º en tiempo real.
- **Arquitectura de Eventos con Kafka (Auditoría Desacoplada)**: Implementación de un bus de eventos para capturar de forma inmutable cada cambio de estado en las solicitudes. Esto permitiría alimentar un microservicio de auditoría externo o un _Data Lake_ sin impactar en la latencia de las transacciones principales, garantizando la trazabilidad histórica total.
- **Infraestructura como Código (Terraform & Azure)**: Definición de toda la infraestructura necesaria en la nube de Azure mediante Terraform (Providers, Resource Groups, Container Apps, SQL Databases) para permitir despliegues repetibles, seguros y versionados.

### Funcionales

- **Añadir la entidad Usuario**: Punto de partida necesario para implementar un sistema de seguridad y control de acceso robusto.
- **Sistema de Auditoría Completo**: Implementación de un log de auditoría detallado que registre qué usuario cambió qué campo y en qué momento, permitiendo una trazabilidad total del ciclo de vida de cada préstamo.
- **Notificaciones Multi-canal**: Integración de servicios externos (para emails, SMS o webhooks) para notificar automáticamente a los clientes sobre cambios en el estado de sus solicitudes en tiempo real.
- **Robustecimiento de Spring Security**: Implementación de políticas de seguridad corporativas, como el bloqueo automático de cuentas tras N intentos fallidos, auditoría de tokens JWT activos (Blacklisting) y soporte para autenticación Multi-Factor (MFA).
- **Motor de Reglas de Negocio**: Integración de un motor de reglas para automatizar la aprobación o denegación de préstamos basada en múltiples criterios configurables dinámicamente sin necesidad de desplegar nuevo código.
