# Loans Manager API

Este proyecto es una API REST para la gestión de solicitudes de préstamos, construida siguiendo principios robustos de ingeniería de software y arquitecturas más actuales.

## ✨ Características Principales

- 🏗️ **Arquitectura Hexagonal**: Desacoplamiento total entre dominio e infraestructura.
- 🔐 **Seguridad Avanzada**: Autenticación JWT con rotación de tokens (Access & Refresh).
- ⚡ **Alto Rendimiento**: Caché de segundo nivel orientada a optimizar lecturas frecuentes.
- 🧪 **Calidad Garantizada**: Cobertura de tests unitarios superior al 85%, análisis de calidad con SonarQube y análisis de vulnerabilidades con Checkmarx.
- 📊 **Monitoreo y Observabilidad**: Grafana para dashboards de métricas y logs, Tempo para trazabilidad distribuida y Loki para centralización de logs.
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
    docker pull ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:master
    ```
2.  **Ejecutar el contenedor (última versión)**:
    ```bash
    docker run -p 8080:8080 ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:master
    ```

### 🐳 Ejecución con Infraestructura Completa (Docker Compose)

Si deseas ejecutar la API con todos sus servicios de soporte (MySQL, Redis, LGTM Stack), puedes usar el perfil `compose`. Spring Boot gestionará automáticamente el ciclo de vida de los contenedores.

1.  **Clonar el repositorio** y situarse en la raíz del proyecto.
2.  **Instalar dependencias**:
    ```bash
    ./mvnw install
    ```
3.  **Compilar y ejecutar**:
    ```bash
    mvn spring-boot:run "-Dspring-boot.run.profiles=compose"
    ```

#### 🛠️ Servicios Incluidos en el Perfil Compose:

- **Base de Datos**: MySQL 8.0 (Puerto 3307).
- **Caché**: Redis (Puerto 6379).
- **Observabilidad (LGTM Stack)**:
  - **Grafana**: [http://localhost:3001](http://localhost:3001) (Dashboards de métricas y logs).
  - **Prometheus**: [http://localhost:9090](http://localhost:9090) (Recolección de métricas).
  - **Loki**: Centralización de logs.
  - **Tempo**: Trazabilidad distribuida.
- **Análisis y Seguridad**: **SonarQube** para evaluación de calidad de código y **Checkmarx (KICS)** para detección de vulnerabilidades en infraestructura.

### Pasos para probar la API independientemente de su ejecución

3.  **Probar la API**:
    - Tienes ejemplos de peticiones listos para usar en [requests.http](./requests.http).
4.  **Documentación API (Swagger/OpenAPI)**:
    - Una vez en ejecución, tienes la documentación Swagger en el siguiente enlace: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
5.  **Consola de Base de Datos H2 (no disponible en perfil compose)**:
    - Acceso en: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - JDBC URL: `jdbc:h2:mem:caixabank` | Usuario: `sa` | Password: (vacío)
6.  **Base de Datos MySQL (disponible en perfil compose)**:
    - Acceso en: [http://localhost:3307](http://localhost:3307) (Necesario un cliente)
    - Puedes acceder mediante CloudBeaver en [http://localhost:8978/](http://localhost:8978/), teniendo en cuenta que entonces sería mysql:3306
    - Usuario: `root` | Password: `root`
7.  **Redis (disponible en perfil compose)**:
    - Acceso en: [http://localhost:6379](http://localhost:6379) (Necesario un cliente)
    - Puedes acceder mediante RedisInsight en [http://localhost:5540/](http://localhost:5540/)
8.  **SonarQube (disponible en perfil compose)**:
    - Acceso en: [http://localhost:9000](http://localhost:9000)
    - Para acceder a SonarQube, crea un nuevo proyecto local y debes configurar el sonar.token en el properties del archivo `pom.xml`
    - Ejecutar el comando mvn sonar:sonar para ver los resultados del análisis en SonarQube.
9.  **Checkmarx (disponible en perfil compose)**:
    - Al ejecutar la aplicación con el perfil compose, se ejecuta Checkmarx automáticamente y se genera el reporte en la carpeta `checkmarx-results`
10. **LGTM Stack (disponible en perfil compose)**:
    - Acceso en: [http://localhost:3001](http://localhost:3001)
    - Para acceder a LGTM Stack, usa las credenciales admin:admin.
    - Ids de los dashboards para importar:
      - 12900
      - 11378
      - 15141
      - 13639

### Otras versiones del proyecto dockerizadas

1. **ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:release-1.0.0**
2. **ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:hotfix-1.0.1**
3. **ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:release-2.0.0**
4. **ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:hotfix-2.0.1**
5. **ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:hotfix-2.0.2**
6. **ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:release-3.0.0**
7. **ghcr.io/jborregovedruna/joaquinborregofernandez-pruebatecnica:hotfix-3.0.1**

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

### 4. Seguridad con JWT y JJWT

La seguridad se gestiona mediante **Spring Security** y el uso de **JSON Web Tokens (JWT)** con la librería **JJWT**. Se ha implementado un flujo avanzado que utiliza:

- **Access Tokens**: Tokens de corta duración para autorizar peticiones API.
- **Refresh Tokens**: Tokens de larga duración para renovar el acceso sin requerir nuevas credenciales.
- Se han configurado filtros de seguridad personalizados para validar la integridad de cada token en el pipeline de peticiones.

### 5. Consistencia de Datos y Manejo de Errores

Para garantizar la integridad de la información y una experiencia de usuario consistente, el proyecto utiliza:

- **Hibernate Validation**: Uso de anotaciones Bean Validation (@NotNull, @Min, @Pattern) para validar los DTOs de entrada en la frontera de la API.
- **ControllerAdvice**: Implementación de un manejador global de excepciones que captura errores de negocio y validación, transformándolos en respuestas JSON estandarizadas y amigables para el cliente.

### 6. Ejecución Ágil: H2, Caché y Flyway

Siguiendo los requisitos del enunciado para facilitar una prueba rápida y sin dependencias externas pesadas:

- **H2 Database**: Base de datos SQL en memoria que permite levantar el entorno sin configurar servidores externos.
- **Caché en Memoria**: Uso de la abstracción de caché de Spring para optimizar lecturas frecuentes y mejorar el tiempo de respuesta.
- **Flyway**: Herramienta de migración que asegura que el esquema de la base de datos H2 se cree y evolucione de forma consistente y automática en cada arranque.

### 7. Calidad de Código, Control de versiones,CI/CD Automatizado y Contenerización

La excelencia técnica se mantiene mediante herramientas integradas en el ciclo de vida de Maven y una pipeline de GitHub Actions:

- **JaCoCo**: Generación de informes de cobertura, configurado para garantizar que el código crítico mantenga una cobertura superior al 85%.
- **Spotless**: Plugin de formateo automático que asegura un estilo de código uniforme (Google Java Format) en todo el equipo.
- **Git, Github y Gitflow**: Control de versiones y flujo de trabajo para el desarrollo de software.
- **GitHub Actions**: Automatización total que ejecuta los tests, verifica el formato y construye una imagen Docker optimizada cada vez que se sube código a ramas principales o de release.
- **Docker**: Uso de _Multi-stage builds_ y _Layered JARs_ para generar imágenes ligeras y seguras, listas para producción.

### 8. Infraestructura Avanzada y Observabilidad (Perfil `compose`)

Utilizando la librería **Spring Boot Docker Compose Support**, el proyecto es capaz de provisionar y configurar automáticamente un entorno completo con un solo comando. Al activar el perfil `compose`, se integran y sincronizan los siguientes sistemas:

- **Persistencia Robusta (MySQL 8.0)**: Sustitución de H2 por una base de datos relacional persistente, gestionada mediante migraciones automatizadas con **Flyway**.
- **Caché de Alto Rendimiento (Redis)**: Implementación de una caché distribuida para optimizar la latencia en la recuperación de datos y reducir la carga sobre la base de datos.
- **Ecosistema de Observabilidad (LGTM Stack)**: Monitorización integral 360º conectada con **Spring Actuator**:
  - **Grafana & Prometheus**: Dashboards profesionales para la visualización de métricas de salud y rendimiento.
  - **Loki & Promtail**: Arquitectura de logs centralizada para una depuración eficiente en tiempo real.
  - **Tempo**: Trazabilidad distribuida de extremo a extremo utilizando el protocolo **OTLP**.
- **Garantía de Calidad y Seguridad (SAST)**:
  - **SonarQube**: Análisis estático de código para asegurar estándares de _Clean Code_ y detección de deuda técnica.
  - **Checkmarx (KICS)**: Escaneo proactivo de seguridad en los archivos de infraestructura y configuración.

### 9. Otras Decisiones Técnicas

- **Lombok**: Para reducir el código repetitivo (_boilerplate_) en entidades y DTOs.
- **MapStruct**: Mapeadores automáticos de alto rendimiento para transferir datos entre capas sin exponer el modelo interno.
- **Swagger**: Documentación de la API.
- **Spring Actuator**: Endpoint de monitoreo para supervisar el estado y métricas de salud de la aplicación.

### 10. Complementos

- **React**: Interfaz gráfica con React para poder probar los diferentes endpoints segun Rol. Puedes encontrarla en el repositorio [https://github.com/JborregoVedruna/JoaquinBorregoFernandez-PruebaTecnicaFront](https://github.com/JborregoVedruna/JoaquinBorregoFernandez-PruebaTecnicaFront)
- **Flask**: Microservicio realizado con Python y Flask que simula una lista de deudores al estilo Asnef. Puedes encontrarla en el repositorio [https://github.com/JborregoVedruna/defaulters-list-api](https://github.com/JborregoVedruna/defaulters-list-api)
- **MCP**: Servidor MCP con Python para darle a github copilot la posibilidad de loguearse como manager, de consultar las solicitudes pendientes, consultar la lista de deudores por dni y de cambiar el estado de una solicitud. Esto nos permite realizar prompts como "Comprueba las solicitudes pendientes de loansmanager api y verifica mediante el dni de los applicant que dichos applicant no tienen deudas pendientes. A todos los que tengan deudas pendientes, cambia el estado de su loanapplication a REJECTED". Puedes encontrarla en el repositorio [https://github.com/JborregoVedruna/loanapplications-mcp](https://github.com/JborregoVedruna/loanapplications-mcp)

---

## 📈 Mejoras y Extensiones Futuras

Con el objetivo de llevar esta API a un nivel productivo de alta disponibilidad y escalabilidad, se proponen las siguientes líneas de evolución:

### Técnicas / Arquitecturales

- **Arquitectura de Eventos con Kafka (Auditoría Desacoplada)**: Implementación de un bus de eventos para capturar de forma inmutable cada cambio de estado en las solicitudes. Esto permitiría alimentar un microservicio de auditoría externo o un _Data Lake_ sin impactar en la latencia de las transacciones principales, garantizando la trazabilidad histórica total.
- **Infraestructura como Código (Terraform & Azure)**: Definición de toda la infraestructura necesaria en la nube de Azure mediante Terraform (Providers, Resource Groups, Container Apps, SQL Databases) para permitir despliegues repetibles, seguros y versionados.

### Funcionales

- **Sistema de Auditoría Completo**: Implementación de un log de auditoría detallado que registre qué usuario cambió qué campo y en qué momento, permitiendo una trazabilidad total del ciclo de vida de cada préstamo.
- **Notificaciones Multi-canal**: Integración de servicios externos (para emails, SMS o webhooks) para notificar automáticamente a los clientes sobre cambios en el estado de sus solicitudes en tiempo real.
- **Robustecimiento de Spring Security**: Implementación de políticas de seguridad corporativas, como el bloqueo automático de cuentas tras N intentos fallidos, auditoría de tokens JWT activos (Blacklisting) y soporte para autenticación Multi-Factor (MFA).
- **Motor de Reglas de Negocio**: Integración de un motor de reglas para automatizar la aprobación o denegación de préstamos basada en múltiples criterios configurables dinámicamente sin necesidad de desplegar nuevo código.
