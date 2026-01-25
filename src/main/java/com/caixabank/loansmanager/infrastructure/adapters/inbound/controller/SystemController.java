package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller;

import com.caixabank.loansmanager.config.apidoc.annotation.GeneralApiDoc;
import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interfaz que define las operaciones de nivel de sistema para la visualización global de
 * préstamos.
 *
 * <p>{@code @Tag}: Agrupación OpenAPI. {@code @Validated}: Validación de argumentos.
 * {@code @RequestMapping}: Ruta base para operaciones de sistema. {@code @PreAuthorize}: Solo
 * ejecutable por ROLE_SYSTEM.
 */
@Tag(name = "System", description = "Operations for system-level management of loan applications")
@Validated
@RequestMapping("/api/v1/system")
@PreAuthorize("hasRole('SYSTEM')")
public interface SystemController {

  /**
   * Endpoint para obtener todas las solicitudes de préstamo existentes de forma paginada.
   *
   * @param pageable Parámetros de paginación (page, size, sort).
   * @return {@link ResponseEntity} con la página de solicitudes y status 200 (OK).
   */
  @Operation(
      summary = "Get all loan applications",
      operationId = "01_getAllLoanApplications",
      description = "Return all loan applications in paginated format")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved loan applications",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Page.class),
                  examples = {
                    @ExampleObject(
                        name = "Loan Application Page Sample",
                        description = "A sample loan application page",
                        value = Examples.LOAN_APPLICATION_PAGE_SAMPLE)
                  })
            })
      })
  @GeneralApiDoc
  @Parameters(
      value = {
        @Parameter(
            name = "pageable",
            description = "Pageable parameters",
            required = false,
            schema = @Schema(implementation = Pageable.class),
            example = Examples.PAGEABLE_SAMPLE)
      })
  @GetMapping("/")
  public ResponseEntity<Page<LoanApplicationOutput>> getAllLoanApplications(Pageable pageable);
}
