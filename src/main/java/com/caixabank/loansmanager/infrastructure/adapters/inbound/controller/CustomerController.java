package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller;

import com.caixabank.loansmanager.config.apidoc.annotation.GeneralApiDoc;
import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Interfaz que define las operaciones disponibles para los clientes en relación a sus préstamos.
 *
 * <p>{@code @Tag}: Agrupación para la documentación de OpenAPI. {@code @Validated}: Habilita
 * validaciones. {@code @RequestMapping}: Ruta base para operaciones de cliente.
 * {@code @PreAuthorize}: Restringe el acceso solo a usuarios con el rol CUSTOMER.
 */
@Tag(name = "Customer", description = "Customer operations for loan application submission")
@Validated
@RequestMapping("/api/v1/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public interface CustomerController {

  /**
   * Endpoint para que un cliente registre una nueva solicitud de préstamo.
   *
   * @param userLogueado El usuario autenticado que realiza la solicitud.
   * @param loanApplicationInput DTO con los detalles del préstamo (importe y moneda).
   * @return {@link ResponseEntity} con la solicitud de préstamo creada y status 201 (CREATED).
   */
  @Operation(
      summary = "Register a loan application",
      description = "Register a loan application in the database")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Loan application registered successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LoanApplicationOutput.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Loan application already exists",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProblemDetail.class))
            })
      })
  @GeneralApiDoc
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "The loan application to insert",
      required = true,
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = LoanApplicationInput.class),
              examples = {
                @ExampleObject(
                    name = "LoanApplicationSample",
                    description = "A sample loan application",
                    value = Examples.LOAN_APPLICATION_INPUT_SAMPLE)
              }))
  @PostMapping("/")
  public ResponseEntity<LoanApplicationOutput> registerALoanApplication(
      @Valid @AuthenticationPrincipal UserDTO userLogueado,
      @Valid @RequestBody LoanApplicationInput loanApplicationInput);
}
