package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.caixabank.loansmanager.config.apidoc.annotation.GeneralApiDoc;
import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanStatusDto;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Manager", description = "Managerial operations for reviewing and managing loan applications")
@Validated
@RequestMapping("/api/v1/manager")
public interface ManagerController {

        @Operation(summary = "Get loan application by id", description = "Return the loan application with the given id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the loan application", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LoanApplicationOutput.class))
                        })
        })
        @GeneralApiDoc
        @Parameters(value = {
                        @Parameter(name = "uuid", description = "Uuid of the loan application to search", required = true, schema = @Schema(type = "uuid"), example = Examples.LOAN_APPLICATION_UUID_SAMPLE)
        })
        @ResponseStatus(HttpStatus.OK)
        @GetMapping("/{uuid}")
        public ResponseEntity<LoanApplicationOutput> getLoanApplicationById(@PathVariable UUID uuid);

        @Operation(summary = "Edit loan application status by id", description = "Edit the status of the loan application with the given id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Loan application status updated successfully", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = LoanApplicationOutput.class))
                        })
        })
        @GeneralApiDoc
        @Parameters(value = {
                        @Parameter(name = "uuid", description = "Uuid of the loan application to update", required = true, schema = @Schema(type = "uuid"), example = Examples.LOAN_APPLICATION_UUID_SAMPLE)
        })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The new status for the loan application", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanStatusDto.class)))
        @PatchMapping("/{uuid}")
        public ResponseEntity<LoanApplicationOutput> updateLoanApplicationStatus(@PathVariable UUID uuid,
                        @Valid @RequestBody LoanStatusDto loanStatusDto);
}
