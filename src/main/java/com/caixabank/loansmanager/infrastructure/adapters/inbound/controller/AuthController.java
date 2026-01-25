package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller;

import com.caixabank.loansmanager.config.apidoc.annotation.GeneralApiDoc;
import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.LoginRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RefreshRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RegisterRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.AuthResponseDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.UserRegisteredDTO;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Interfaz que define los endpoints para la autenticación y gestión de sesiones de usuario.
 *
 * <p>{@code @Tag}: Define la agrupación de estas operaciones para la documentación de OpenAPI.
 * {@code @Validated}: Habilita la validación de argumentos en los métodos. {@code @RequestMapping}:
 * Define la ruta base para todos los endpoints de este controlador.
 */
@Tag(name = "Auth", description = "Auth operations")
@Validated
@RequestMapping("/api/v1/auth")
public interface AuthController {

  /**
   * Endpoint para registrar un nuevo usuario en el sistema.
   *
   * @param request DTO con la información necesaria para el registro.
   * @return {@link ResponseEntity} con los datos del usuario registrado y status 201 (CREATED).
   */
  @Operation(summary = "Register an user", description = "Register an user in the database")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "User registered successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserRegisteredDTO.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "User or DNI already exists",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProblemDetail.class))
            })
      })
  @GeneralApiDoc
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "The user to insert",
      required = true,
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = RegisterRequestDTO.class),
              examples = {
                @ExampleObject(
                    name = "RegisterRequestSample",
                    description = "A sample user",
                    value = Examples.REGISTER_REQUEST_SAMPLE)
              }))
  @PostMapping("/register")
  ResponseEntity<UserRegisteredDTO> register(@Valid @RequestBody RegisterRequestDTO request);

  /**
   * Endpoint para autenticar un usuario y obtener tokens de acceso.
   *
   * @param request DTO con las credenciales (usuario y contraseña).
   * @return {@link ResponseEntity} con el token de acceso generado y status 200 (OK).
   */
  @Operation(summary = "Login an user", description = "Login an user in the database")
  @ResponseStatus(HttpStatus.OK)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User logged successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AuthResponseDTO.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description = "User or password invalid",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProblemDetail.class))
            })
      })
  @GeneralApiDoc
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "The user to log in",
      required = true,
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = LoginRequestDTO.class),
              examples = {
                @ExampleObject(
                    name = "LoginRequestSample",
                    description = "A sample user",
                    value = Examples.LOGIN_REQUEST_SAMPLE)
              }))
  @PostMapping("/login")
  ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request);

  /**
   * Endpoint para obtener la información del usuario actualmente autenticado.
   *
   * @param userLogueado El DTO del usuario extraído del contexto de seguridad.
   * @return {@link ResponseEntity} con los detalles del usuario actual.
   */
  @Operation(summary = "Get the current logged user", description = "Get the current logged user")
  @ResponseStatus(HttpStatus.OK)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User found successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDTO.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProblemDetail.class))
            })
      })
  @GeneralApiDoc
  @GetMapping("/me")
  ResponseEntity<UserDTO> me(@AuthenticationPrincipal UserDTO userLogueado);

  /**
   * Endpoint para renovar el token de acceso utilizando un token de refresco.
   *
   * @param request DTO que contiene el Refresh Token.
   * @return {@link ResponseEntity} con el nuevo token de acceso generado.
   */
  @Operation(summary = "Refresh token", description = "Refresh token")
  @ResponseStatus(HttpStatus.OK)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token refreshed successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AuthResponseDTO.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Token invalid",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProblemDetail.class))
            })
      })
  @GeneralApiDoc
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description = "The token to refresh",
      required = true,
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = RefreshRequestDTO.class),
              examples = {
                @ExampleObject(
                    name = "RefreshRequestSample",
                    description = "A sample token",
                    value = Examples.REFRESH_REQUEST_SAMPLE)
              }))
  @PostMapping("/refresh")
  ResponseEntity<AuthResponseDTO> refreshToken(@Valid @RequestBody RefreshRequestDTO request);
}
