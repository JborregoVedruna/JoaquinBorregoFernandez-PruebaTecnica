package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.domain.model.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

/**
 * DTO que representa la respuesta tras el registro exitoso de un usuario.
 *
 * <p>{@code @Data}: Genera los métodos estándar de acceso.
 */
@Schema(
    description = "Represents a user registration response",
    example = Examples.USER_REGISTERED_RESPONSE_SAMPLE)
@Data
public class UserRegisteredDTO {

  /** Identificador único del usuario registrado. */
  @Schema(description = "The uuid of the user", example = Examples.RAW_LOAN_UUID)
  private UUID userUuid;

  /** Nombre de usuario del nuevo cliente. */
  @Schema(description = "The username of the user", example = Examples.RAW_USERNAME)
  private String username;

  /** DNI del usuario registrado. */
  @Schema(description = "The DNI of the user", example = Examples.RAW_DNI)
  private String userDni;

  /** Rol asignado durante el registro. */
  @Schema(description = "The rol of the user", example = "ROLE_CUSTOMER")
  private Rol rol;
}
