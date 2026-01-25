package com.caixabank.loansmanager.config.apidoc.schema;

/**
 * Clase de utilidad que contiene constantes de ejemplo ({@code String})
 * utilizadas para la documentación de la API (OpenAPI/Swagger UI).
 *
 * Estas constantes proporcionan valores de muestra para los parámetros de las
 * rutas y estructuras JSON completas para peticiones (requests) y respuestas
 * (responses).
 */
public class Examples {

    // -----------------------------------------------------------------------------------------------
    // INDIVIDUAL VALUES (RAW) - For field-level @Schema examples
    // -----------------------------------------------------------------------------------------------

    public static final String RAW_LOAN_UUID = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
    public static final String RAW_APPLICANT_DNI = "12345678Z";
    public static final String RAW_APPLICANT_NAME = "Alejandro Garcia";
    public static final String RAW_AMOUNT = "15000.00";
    public static final String RAW_CURRENCY = "EUR";
    public static final String RAW_STATUS_PENDING = "PENDING";
    public static final String RAW_STATUS_APPROVED = "APPROVED";
    public static final String RAW_STATUS_REJECTED = "REJECTED";
    public static final String RAW_STATUS_CANCELLED = "CANCELLED";
    public static final String RAW_CREATED_DATE = "2026-01-24T12:00:00";

    // -----------------------------------------------------------------------------------------------
    // LOAN APPLICATION - PATH PARAMETERS
    // -----------------------------------------------------------------------------------------------

    /**
     * UUID de ejemplo para una solicitud de préstamo.
     * Valor: {@value #LOAN_APPLICATION_UUID_SAMPLE}
     */
    public static final String LOAN_APPLICATION_UUID_SAMPLE = RAW_LOAN_UUID;

    /**
     * DNI de ejemplo para un solicitante.
     * Valor: {@value #APPLICANT_DNI_SAMPLE}
     */
    public static final String APPLICANT_DNI_SAMPLE = RAW_APPLICANT_DNI;

    /**
     * Nombre de solicitante de ejemplo.
     * Valor: {@value #APPLICANT_NAME_SAMPLE}
     */
    public static final String APPLICANT_NAME_SAMPLE = RAW_APPLICANT_NAME;

    // -----------------------------------------------------------------------------------------------
    // LOAN APPLICATION - REQUEST BODIES
    // -----------------------------------------------------------------------------------------------

    /**
     * Cuerpo JSON de ejemplo para crear (POST) una nueva solicitud de préstamo.
     * Endpoint: POST /api/v1/customer/
     */
    public static final String LOAN_APPLICATION_INPUT_SAMPLE = """
            {
                "applicantName": "Lucia Fernandez",
                "requestedAmount": 1200,
                "currency": "GBP",
                "applicantDni": "23456789D"
            }
            """;

    /**
     * Cuerpo JSON de ejemplo para actualizar el estado de una solicitud (PATCH).
     * Endpoint: PATCH /api/v1/manager/{uuid}
     */
    public static final String LOAN_STATUS_UPDATE_SAMPLE = """
            {
                "status": "APPROVED"
            }
            """;

    /**
     * Cuerpo JSON de ejemplo para actualizar el estado a CANCELLED.
     */
    public static final String LOAN_STATUS_CANCELLED_SAMPLE = """
            {
                "status": "CANCELLED"
            }
            """;

    /**
     * Cuerpo JSON de ejemplo para actualizar el estado a REJECTED.
     */
    public static final String LOAN_STATUS_REJECTED_SAMPLE = """
            {
                "status": "REJECTED"
            }
            """;

    // -----------------------------------------------------------------------------------------------
    // LOAN APPLICATION - RESPONSE BODIES
    // -----------------------------------------------------------------------------------------------

    /**
     * Cuerpo JSON de ejemplo para la respuesta (Response) de una única solicitud
     * de préstamo, incluyendo todos los campos.
     */
    public static final String LOAN_APPLICATION_OUTPUT_SAMPLE = """
            {
                "uuid": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                "applicantName": "Alejandro Garcia",
                "requestedAmount": 15000.00,
                "currency": "EUR",
                "applicantDni": "12345678Z",
                "createdDate": "2026-01-24T12:00:00",
                "status": "PENDING"
            }
            """;

    /**
     * Cuerpo JSON de ejemplo para la respuesta de una solicitud aprobada.
     */
    public static final String LOAN_APPLICATION_APPROVED_SAMPLE = """
            {
                "uuid": "72e19c0a-3d2b-4f9e-8c4d-6192a5b8c3d1",
                "applicantName": "Maria Lopez",
                "requestedAmount": 4500.50,
                "currency": "USD",
                "applicantDni": "87654321X",
                "createdDate": "2026-01-24T12:05:00",
                "status": "APPROVED"
            }
            """;

    // -----------------------------------------------------------------------------------------------
    // PAGEABLE - REQUEST/RESPONSE
    // -----------------------------------------------------------------------------------------------

    /**
     * Cuerpo JSON de ejemplo para el objeto {@code Pageable} utilizado en las
     * peticiones.
     * Define la página, el tamaño y el campo de ordenamiento.
     */
    public static final String PAGEABLE_SAMPLE = """
            {
                "page": 0,
                "size": 10,
                "sort": "createdDate,desc"
            }
            """;

    /**
     * Cuerpo JSON de ejemplo para la respuesta (Response) que contiene una página
     * ({@code Page}) de resultados de solicitudes de préstamo.
     */
    public static final String LOAN_APPLICATION_PAGE_SAMPLE = """
            {
                "content": [
                    {
                        "uuid": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                        "applicantName": "Alejandro Garcia",
                        "requestedAmount": 15000.00,
                        "currency": "EUR",
                        "applicantDni": "12345678Z",
                        "createdDate": "2026-01-24T12:00:00",
                        "status": "PENDING"
                    },
                    {
                        "uuid": "72e19c0a-3d2b-4f9e-8c4d-6192a5b8c3d1",
                        "applicantName": "Maria Lopez",
                        "requestedAmount": 4500.50,
                        "currency": "USD",
                        "applicantDni": "87654321X",
                        "createdDate": "2026-01-24T12:05:00",
                        "status": "APPROVED"
                    }
                ],
                "pageable": {
                    "pageNumber": 0,
                    "pageSize": 10,
                    "sort": {
                        "empty": false,
                        "unsorted": false,
                        "sorted": true
                    },
                    "offset": 0,
                    "unpaged": false,
                    "paged": true
                },
                "last": true,
                "totalElements": 5,
                "totalPages": 1,
                "first": true,
                "size": 10,
                "number": 0,
                "sort": {
                    "empty": false,
                    "unsorted": false,
                    "sorted": true
                },
                "numberOfElements": 5,
                "empty": false
            }
            """;

    // -----------------------------------------------------------------------------------------------
    // LOAN STATUS - VALID VALUES
    // -----------------------------------------------------------------------------------------------

    /**
     * Lista de estados válidos para una solicitud de préstamo.
     */
    public static final String LOAN_STATUS_VALUES = "PENDING, APPROVED, REJECTED, CANCELLED";
}
