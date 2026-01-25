package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO para la solicitud de renovación de tokens (refresh token). */
@Schema(description = "Represents a refresh request", example = Examples.REFRESH_REQUEST_SAMPLE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequestDTO {
  /** El Refresh Token que se desea canjear por un nuevo Access Token. */
  @Schema(
      description = "The refresh token to be exchanged",
      example = Examples.RAW_REFRESH_TOKEN,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "refreshToken is required and must not be blank")
  String refreshToken;
}
