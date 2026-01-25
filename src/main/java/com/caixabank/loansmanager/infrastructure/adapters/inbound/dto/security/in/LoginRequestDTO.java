package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO para la solicitud de inicio de sesión. */
@Schema(description = "Represents a login request", example = Examples.LOGIN_REQUEST_SAMPLE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

  /** El nombre de usuario para la autenticación. */
  @Schema(
      description = "The username of the user",
      example = Examples.RAW_USERNAME,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "username is required and must not be blank")
  @Size(min = 3, max = 45, message = "username must be between 3 and 45 characters long")
  String username;

  /** La contraseña del usuario. */
  @Schema(
      description = "The password of the user",
      example = Examples.RAW_PASSWORD,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "password is required and must not be blank")
  String password;
}
