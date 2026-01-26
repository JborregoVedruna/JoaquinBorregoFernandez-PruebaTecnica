package com.caixabank.loansmanager.infrastructure.adapters.outbound.entities;

import com.caixabank.loansmanager.domain.model.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entidad de persistencia que representa a un usuario en el sistema.
 *
 * <p>Implementa {@link UserDetails} para integrarse directamente con Spring Security.
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

  /** Identificador único del usuario. */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "user_uuid", nullable = false)
  private UUID userUuid;

  /** Nombre de usuario único para acceso. */
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  /** Contraseña codificada. */
  @Column(name = "password", nullable = false, columnDefinition = "CHAR(60)")
  private String password;

  /** Documento Nacional de Identidad único. */
  @Column(name = "user_dni", nullable = false, unique = true)
  private String userDni;

  /** Rol asignado al usuario. */
  @Column(name = "rol", nullable = false)
  private Rol rol;

  /** Fecha de expiración de la cuenta. */
  @Column(name = "account_expiration_date", nullable = false)
  private LocalDateTime accountExpirationDate;

  /** Indica si la cuenta está bloqueada administrativamente. */
  @Column(name = "is_locked", nullable = false)
  private Boolean isLocked;

  /** Fecha de expiración de las credenciales. */
  @Column(name = "credentials_expiration_date", nullable = false)
  private LocalDateTime credentialsExpirationDate;

  /** Indica si el usuario está habilitado para operar. */
  @Column(name = "is_enabled", nullable = false)
  private Boolean isEnabled;

  /** Lista de solicitudes de préstamo asociadas al usuario. */
  @OneToMany(mappedBy = "user")
  private List<LoanApplicationEntity> loanApplications;

  /**
   * Devuelve las autoridades (roles) concedidas al usuario. La autoridad se construye a partir del
   * nombre del rol de la base de datos.
   *
   * <p>NOTA: Por convención de Spring Security, los nombres de roles deben ir prefijados con
   * 'ROLE_'. Si el rolName es 'ADMIN', la autoridad será 'ROLE_ADMIN'.
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
   * Indica si la cuenta del usuario ha expirado.
   *
   * @return true si la cuenta es válida (nunca expira en esta implementación).
   */
  @Override
  public boolean isAccountNonExpired() {
    return this.accountExpirationDate == null
        || this.accountExpirationDate.isAfter(LocalDateTime.now());
  }

  /**
   * Indica si la cuenta del usuario está bloqueada.
   *
   * @return true si la cuenta no está bloqueada (nunca se bloquea en esta implementación).
   */
  @Override
  public boolean isAccountNonLocked() {
    return !this.isLocked;
  }

  /**
   * Indica si las credenciales (contraseña) del usuario han expirado.
   *
   * @return true si las credenciales son válidas (nunca expiran en esta implementación).
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsExpirationDate == null
        || this.credentialsExpirationDate.isAfter(LocalDateTime.now());
  }

  /**
   * Indica si el usuario está habilitado o deshabilitado.
   *
   * @return true si el usuario está habilitado (siempre habilitado en esta implementación).
   */
  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }
}
