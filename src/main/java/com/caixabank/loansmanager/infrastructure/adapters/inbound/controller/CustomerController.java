package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.caixabank.loansmanager.config.apidoc.annotation.GeneralApiDoc;
import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Customer", description = "Customer operations for loan application submission")
@Validated
@RequestMapping("/api/v1/customer")
public interface CustomerController {

        @Operation(summary = "Register a loan application", description = "Register a loan application in the database")
        @ResponseStatus(HttpStatus.CREATED)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Loan application registered successfully", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LoanApplicationOutput.class))
                        }),
                        @ApiResponse(responseCode = "409", description = "Loan application already exists", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        })
        })
        @GeneralApiDoc
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The loan application to insert", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanApplicationInput.class), examples = {
                        @ExampleObject(name = "LoanApplicationSample", description = "A sample loan application", value = Examples.LOAN_APPLICATION_INPUT_SAMPLE) }))
        @PostMapping("/")
        public ResponseEntity<LoanApplicationOutput> registerALoanApplication(
                        @Valid @RequestBody LoanApplicationInput loanApplicationInput);
}
