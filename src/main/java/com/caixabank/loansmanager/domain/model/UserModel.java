package com.caixabank.loansmanager.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;

/**
 * Modelo de dominio que representa a un usuario del sistema.
 *
 * <p>{@code @Data}: Anotación de Lombok para generar métodos de acceso y utilidad.
 */
@Data
public class UserModel implements Serializable {
  private static final long serialVersionUID = 1L;

  /** Identificador único universal (UUID) del usuario. */
  private UUID userUuid;

  /** Nombre de usuario para la autenticación. */
  private String username;

  /** Contraseña del usuario (debe almacenarse codificada). */
  private String password;

  /** Documento Nacional de Identidad del usuario. */
  private String userDni;

  /** Rol asignado para determinar permisos. */
  private Rol rol;

  /** Fecha en la que expira la cuenta del usuario. */
  private LocalDateTime accountExpirationDate;

  /** Indica si la cuenta del usuario está bloqueada. */
  private Boolean isLocked;

  /** Fecha en la que expira la contraseña del usuario. */
  private LocalDateTime credentialsExpirationDate;

  /** Indica si el usuario está habilitado para acceder al sistema. */
  private Boolean isEnabled;

  /** Listado de todas las solicitudes de préstamo realizadas por este usuario. */
  List<LoanApplicationModel> loanApplications;

  /**
   * Obtiene los permisos (scopes) asociados al rol actual del usuario.
   *
   * @return Los permisos correspondientes según el rol.
   */
  public String getScope() {
    return rol.getScopesByRol();
  }
}
