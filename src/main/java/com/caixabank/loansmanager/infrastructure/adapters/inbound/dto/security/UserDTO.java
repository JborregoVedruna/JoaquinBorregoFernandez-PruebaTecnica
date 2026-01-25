package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security;

import com.caixabank.loansmanager.config.apidoc.schema.Examples;
import com.caixabank.loansmanager.domain.model.Rol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * DTO que representa a un usuario autenticado en el sistema.
 *
 * <p>Implementa {@link UserDetails} para integrarse con el ecosistema de seguridad de Spring
 * Security.
 */
@Schema(description = "Represents a user", example = Examples.USER_DTO_SAMPLE)
@Data
public class UserDTO implements UserDetails {

  /** Identificador único del usuario. */
  @Schema(description = "The uuid of the user", example = Examples.RAW_LOAN_UUID)
  private UUID userUuid;

  /** Nombre de usuario. */
  @Schema(description = "The username of the user", example = Examples.RAW_USERNAME)
  private String username;

  /** DNI del usuario. */
  @Schema(description = "The DNI of the user", example = Examples.RAW_DNI)
  private String userDni;

  /** Rol asignado al usuario. */
  @Schema(description = "The rol of the user", example = "ROLE_CUSTOMER")
  private Rol rol;

  /**
   * Devuelve las autoridades concedidas al usuario.
   *
   * @return Una colección de autoridades basadas en el rol del usuario.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Si el rol es nulo (aunque esto debería ser prevenido por la lógica de
    // negocio/BD)
    if (this.rol == null) {
      return Collections.emptyList();
    }

    // Mapea el nombre del rol a una autoridad de Spring Security
    return List.of(new SimpleGrantedAuthority(this.rol.name()));
  }

  /**
   * Devuelve la contraseña del usuario. En este DTO, se devuelve null por seguridad ya que se
   * gestiona externamente.
   *
   * @return null.
   */
  @JsonIgnore
  @Override
  public @Nullable String getPassword() {
    return null;
  }
}
