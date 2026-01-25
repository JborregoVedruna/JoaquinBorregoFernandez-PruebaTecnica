package com.caixabank.loansmanager.domain.model;

// Se añade el prefijo "ROLE_" como buena práctica de Spring Security.
/** Enumerado que define los distintos roles de usuario dentro del sistema. */
public enum Rol {
  /** Rol para clientes que solicitan préstamos. */
  ROLE_CUSTOMER,
  /** Rol para gestores que aprueban o rechazan solicitudes. */
  ROLE_MANAGER,
  /** Rol para procesos automáticos o administradores del sistema. */
  ROLE_SYSTEM;

  /**
   * Obtiene los permisos (scopes) asociados a cada rol para la generación de tokens.
   *
   * @return Una cadena de texto con los permisos separados por espacios.
   */
  public String getScopesByRol() {
    switch (this) {
      case ROLE_CUSTOMER:
        return "read write";
      case ROLE_MANAGER:
        return "read write";
      case ROLE_SYSTEM:
        return "read write admin";
      default:
        return "";
    }
  }
}
