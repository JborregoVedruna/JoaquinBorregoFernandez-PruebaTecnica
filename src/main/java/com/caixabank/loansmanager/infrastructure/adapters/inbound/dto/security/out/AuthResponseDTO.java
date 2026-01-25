package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la respuesta a una autenticación exitosa.
 *
 * <p>{@code @Schema}: Documentación de OpenAPI. {@code @Data}: Métodos boilerplate.
 * {@code @Builder}: Soporte para el patrón Builder. {@code @AllArgsConstructor}: Constructor
 * completo. {@code @NoArgsConstructor}: Constructor vacío.
 */
@Schema(
    description = "Represents an authentication response",
    example = Examples.AUTH_RESPONSE_SAMPLE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

  /** Tipo de token (siempre "Bearer"). */
  @Schema(description = "The type of the token", example = "Bearer")
  @JsonProperty("token_type")
  final String tokenType = "Bearer";

  /** Token de acceso JWT generado. */
  @Schema(description = "The access token", example = Examples.RAW_ACCESS_TOKEN)
  @JsonProperty("access_token")
  String accessToken;

  /** Tiempo de expiración del token en segundos. */
  @Schema(description = "The expiration time in seconds", example = Examples.RAW_EXPIRES_IN)
  @JsonProperty("expires_in")
  Long expiresIn;

  /** Token de refresco para renovar el acceso. */
  @Schema(description = "The refresh token", example = Examples.RAW_REFRESH_TOKEN)
  @JsonProperty("refresh_token")
  String refreshToken;

  /** Permisos o ámbitos otorgados al token. */
  @Schema(description = "The scopes of the token", example = Examples.RAW_SCOPE)
  @JsonProperty("scope")
  String scope;
}
