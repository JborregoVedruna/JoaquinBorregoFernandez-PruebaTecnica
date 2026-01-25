package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.domain.validations.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO para la solicitud de registro de un nuevo usuario. */
@Schema(description = "Represents a register request", example = Examples.REGISTER_REQUEST_SAMPLE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

  /** El nombre de usuario deseado. */
  @Schema(
      description = "The username of the user",
      example = Examples.RAW_USERNAME,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "username is required and must not be blank")
  @Size(min = 3, max = 45, message = "username must be between 3 and 45 characters long")
  private String username;

  /** La contraseña elegida, debe cumplir los criterios de complejidad. */
  @Schema(
      description =
          "The password of the user. Must be at least 8 characters long, including an uppercase letter, a lowercase letter, a digit, and a special character.",
      example = Examples.RAW_PASSWORD,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "password is required and must not be blank")
  @Password
  private String password;

  /** El DNI del usuario para el registro. */
  @Schema(
      description = "The DNI of the user",
      example = Examples.RAW_DNI,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "userDni is required and must not be blank")
  @Size(min = 9, max = 9, message = "userDni must be exactly 9 characters long")
  private String userDni;
}
