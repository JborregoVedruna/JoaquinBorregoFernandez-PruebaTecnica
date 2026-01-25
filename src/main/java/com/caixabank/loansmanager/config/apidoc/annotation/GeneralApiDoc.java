package com.caixabank.loansmanager.config.apidoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.ProblemDetail;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Custom annotations wrapper for all endpoints
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Body Parameters are invalid", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized: the request requires user authentication", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        }),
        @ApiResponse(responseCode = "403", description = "Forbidden: the server understood the request, but is refusing to fulfill it", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        }),
        @ApiResponse(responseCode = "404", description = "Resource not found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        }),
})
public @interface GeneralApiDoc {

}
