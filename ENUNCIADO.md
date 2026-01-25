# Enunciado: API REST de Gestión de Préstamos

Una entidad bancaria desea desarrollar una **API REST** sencilla para gestionar solicitudes de préstamos personales. Esta API permitirá a los clientes enviar sus solicitudes, a los gestores revisarlas y modificar su estado, y al sistema consultar el historial de solicitudes.

## Funcionalidades Principales

1. **Creación** de una solicitud de préstamo.
2. **Modificación del estado** de una solicitud de préstamo.
3. **Consulta** de las solicitudes existentes.

---

## Requisitos Funcionales

1. **Datos de la solicitud:**
   - Nombre del solicitante.
   - Importe solicitado.
   - Divisa.
   - Documento identificativo (DNI, NIE...).
   - Fecha de creación.

2. **Consultas:** Se debe permitir consultar un listado de solicitudes o una solicitud en específico.

3. **Flujo de estados:**
   Se debe permitir modificar el estado de una solicitud siguiendo esta lógica:
   - `Pendiente` ➡️ `Aprobada` o `Rechazada`.
   - `Aprobada` ➡️ `Cancelada`.

> **Nota:** No es necesario persistir en base de datos; puedes usar almacenamiento en memoria.

---

## Requerimientos Mínimos

- **Lenguaje:** Versión Java 11 o superior.
- **Framework:** Utilizar Spring.
- **Librerías:** Se permite el uso de otras librerías adicionales.

---

## Entrega

Sube tu proyecto a un repositorio GIT público en **GitHub** o **GitLab**.

El archivo `README.md` debe incluir:

1. Instrucciones para ejecutar el proyecto.
2. Breve descripción de la arquitectura y decisiones técnicas tomadas.
3. Indica qué mejoras o extensiones implementarías con más tiempo (tanto funcionales como técnicas/arquitecturales).

Comparte el enlace del repositorio al finalizar a la dirección de email facilitada para entregar la prueba.

**¡Mucha suerte!**
