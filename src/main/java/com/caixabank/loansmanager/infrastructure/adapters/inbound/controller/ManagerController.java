package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller;

import com.caixabank.loansmanager.config.apidoc.annotation.GeneralApiDoc;
import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanStatusDto;
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
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Interfaz que define las operaciones de gestión disponibles para los usuarios con rol MANAGER.
 *
 * <p>{@code @Tag}: Agrupación OpenAPI. {@code @Validated}: Habilita validaciones.
 * {@code @RequestMapping}: Ruta base para gestión. {@code @PreAuthorize}: Solo accesible por
 * ROLE_MANAGER.
 */
@Tag(
    name = "Manager",
    description = "Managerial operations for reviewing and managing loan applications")
@Validated
@RequestMapping("/api/v1/manager")
@PreAuthorize("hasRole('MANAGER')")
public interface ManagerController {

  /**
   * Endpoint para consultar una solicitud de préstamo por su identificador único.
   *
   * @param uuid Identificador único universal de la solicitud.
   * @return {@link ResponseEntity} con los detalles de la solicitud y status 200 (OK).
   */
  @Operation(
      summary = "Get loan application by id",
      description = "Return the loan application with the given id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the loan application",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LoanApplicationOutput.class))
            })
      })
  @GeneralApiDoc
  @Parameters(
      value = {
        @Parameter(
            name = "uuid",
            description = "Uuid of the loan application to search",
            required = true,
            schema = @Schema(type = "uuid"),
            example = Examples.LOAN_APPLICATION_UUID_SAMPLE)
      })
  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{uuid}")
  public ResponseEntity<LoanApplicationOutput> getLoanApplicationById(@PathVariable UUID uuid);

  /**
   * Endpoint para actualizar el estado de una solicitud de préstamo (ej. Aprobar o Rechazar).
   *
   * @param uuid El identificador de la solicitud a modificar.
   * @param loanStatusDto DTO que contiene el nuevo estado.
   * @return {@link ResponseEntity} con la solicitud actualizada y status 200 (OK).
   */
  @Operation(
      summary = "Edit loan application status by id",
      description = "Edit the status of the loan application with the given id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Loan application status updated successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LoanApplicationOutput.class))
            })
      })
  @GeneralApiDoc
  @Parameters(
      value = {
        @Parameter(
            name = "uuid",
            description = "Uuid of the loan application to update",
            required = true,
            schema = @Schema(type = "uuid"),
            example = Examples.LOAN_APPLICATION_UUID_SAMPLE)
      })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "The new status for the loan application",
      required = true,
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = LoanStatusDto.class)))
  @PatchMapping("/{uuid}")
  public ResponseEntity<LoanApplicationOutput> updateLoanApplicationStatus(
      @PathVariable UUID uuid, @Valid @RequestBody LoanStatusDto loanStatusDto);

      @Operation(
      summary = "Get all loan applications with a specific status",
      description = "Return all loan applications with a specific status in paginated format")
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
                        name = "Loan Application PENDING Page Sample",
                        description = "A sample loan application page with PENDING status",
                        value = Examples.LOAN_APPLICATION_PAGE_SAMPLE)
                  })
            })
      })
  @GeneralApiDoc
  @Parameters(
    value = {
        @Parameter(
            name = "status",
            description = "Status of the loan applications to search",
            required = true,
            schema = @Schema(type = "status"),
            example = Examples.LOAN_APPLICATION_UUID_SAMPLE),
        @Parameter(
            name = "pageable",
            description = "Pageable parameters",
            required = false,
            schema = @Schema(implementation = Pageable.class),
            example = Examples.PAGEABLE_SAMPLE)
      })
  @GetMapping("/status/{status}")
  public ResponseEntity<Page<LoanApplicationOutput>> getLoanApplicationByStatus(
      @PathVariable String status, Pageable pageable);
}
