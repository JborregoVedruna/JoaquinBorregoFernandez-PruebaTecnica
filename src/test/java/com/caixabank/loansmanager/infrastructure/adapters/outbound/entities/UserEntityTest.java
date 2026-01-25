package com.caixabank.loansmanager.infrastructure.adapters.outbound.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.caixabank.loansmanager.domain.model.Rol;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Clase de prueba unitaria para {@link UserEntity}.
 *
 * <p>Verifica que la entidad de persistencia se comporte correctamente como un objeto Data y como
 * implementación de UserDetails para Spring Security.
 */
class UserEntityTest {

  /** Verifica que el método getAuthorities devuelva el rol correcto cuando este no es nulo. */
  @Test
  void getAuthorities_ShouldReturnRoleWhenRoleIsNotNull() {
    UserEntity userEntity = new UserEntity();
    userEntity.setRol(Rol.ROLE_CUSTOMER);

    Collection<? extends GrantedAuthority> authorities = userEntity.getAuthorities();

    assertNotNull(authorities);
    assertFalse(authorities.isEmpty());
    assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
  }

  /** Verifica que el método getAuthorities devuelva una colección vacía cuando el rol es nulo. */
  @Test
  void getAuthorities_ShouldReturnEmptyWhenRoleIsNull() {
    UserEntity userEntity = new UserEntity();
    userEntity.setRol(null);

    Collection<? extends GrantedAuthority> authorities = userEntity.getAuthorities();

    assertNotNull(authorities);
    assertTrue(authorities.isEmpty());
  }

  /** Verifica los métodos de la interfaz UserDetails y los getters/setters básicos. */
  @Test
  void userDetailsMethods_ShouldReturnTrueOrExpectedValues() {
    UserEntity userEntity = new UserEntity();
    userEntity.setIsEnabled(true);
    userEntity.setIsLocked(false);
    userEntity.setAccountExpirationDate(LocalDateTime.now().plusDays(1));
    userEntity.setCredentialsExpirationDate(LocalDateTime.now().plusDays(1));
    userEntity.setUserDni("12345678A");
    userEntity.setUsername("testuser");
    userEntity.setPassword("password");
    UUID uuid = UUID.randomUUID();
    userEntity.setUserUuid(uuid);

    assertTrue(userEntity.isEnabled());
    assertTrue(userEntity.isAccountNonLocked());
    assertTrue(userEntity.isAccountNonExpired());
    assertTrue(userEntity.isCredentialsNonExpired());
    assertEquals("testuser", userEntity.getUsername());
    assertEquals("password", userEntity.getPassword());
    assertEquals(uuid, userEntity.getUserUuid());
    assertEquals("12345678A", userEntity.getUserDni());
    userEntity.setRol(Rol.ROLE_CUSTOMER);
    assertEquals(Rol.ROLE_CUSTOMER, userEntity.getRol());
  }

  /** Verifica que isAccountNonExpired devuelva true si la fecha de expiración es nula. */
  @Test
  void isAccountNonExpired_ShouldReturnTrueIfDateNull() {
    UserEntity userEntity = new UserEntity();
    userEntity.setAccountExpirationDate(null);
    assertTrue(userEntity.isAccountNonExpired());
  }

  /** Verifica que isAccountNonExpired devuelva false si la cuenta ha expirado (fecha pasada). */
  @Test
  void isAccountNonExpired_ShouldReturnFalseIfDateInPast() {
    UserEntity userEntity = new UserEntity();
    userEntity.setAccountExpirationDate(LocalDateTime.now().minusDays(1));
    assertFalse(userEntity.isAccountNonExpired());
  }

  /** Verifica que isCredentialsNonExpired devuelva true si la fecha de expiración es nula. */
  @Test
  void isCredentialsNonExpired_ShouldReturnTrueIfDateNull() {
    UserEntity userEntity = new UserEntity();
    userEntity.setCredentialsExpirationDate(null);
    assertTrue(userEntity.isCredentialsNonExpired());
  }

  /**
   * Verifica que isCredentialsNonExpired devuelva false si las credenciales han expirado (fecha
   * pasada).
   */
  @Test
  void isCredentialsNonExpired_ShouldReturnFalseIfDateInPast() {
    UserEntity userEntity = new UserEntity();
    userEntity.setCredentialsExpirationDate(LocalDateTime.now().minusDays(1));
    assertFalse(userEntity.isCredentialsNonExpired());
  }

  /** Verifica que isAccountNonLocked refleje el estado inverso del campo isLocked. */
  @Test
  void isAccountNonLocked_ShouldReturnOppositeOfIsLocked() {
    UserEntity userEntity = new UserEntity();
    userEntity.setIsLocked(true);
    assertFalse(userEntity.isAccountNonLocked());
    userEntity.setIsLocked(false);
    assertTrue(userEntity.isAccountNonLocked());
  }

  /** Verifica la consistencia de los métodos equals y hashCode. */
  @Test
  void equalsAndHashCode_ShouldBeConsistent() {
    UserEntity u1 = new UserEntity();
    u1.setUsername("u1");
    UserEntity u2 = new UserEntity();
    u2.setUsername("u1");
    UserEntity u3 = new UserEntity();
    u3.setUsername("u3");

    assertEquals(u1, u2);
    assertNotEquals(u1, u3);
    assertEquals(u1.hashCode(), u2.hashCode());
  }
}
