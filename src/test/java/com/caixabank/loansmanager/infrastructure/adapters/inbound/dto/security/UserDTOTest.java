package com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security;

import static org.junit.jupiter.api.Assertions.*;

import com.caixabank.loansmanager.domain.model.Rol;
import java.util.Collection;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

/**
 * Clase de prueba unitaria para {@link UserDTO}.
 *
 * <p>Verifica el comportamiento de la implementación de UserDetails y los métodos de objeto
 * (equals, hashCode).
 */
class UserDTOTest {

  /** Verifica que getAuthorities devuelva el rol correcto cuando no es nulo. */
  @Test
  void getAuthorities_ShouldReturnRoleWhenRoleIsNotNull() {
    UserDTO userDto = new UserDTO();
    userDto.setRol(Rol.ROLE_CUSTOMER);

    Collection<? extends GrantedAuthority> authorities = userDto.getAuthorities();

    assertNotNull(authorities);
    assertEquals(1, authorities.size());
    assertEquals("ROLE_CUSTOMER", authorities.iterator().next().getAuthority());
  }

  /** Verifica que getAuthorities devuelva una colección vacía cuando el rol es nulo. */
  @Test
  void getAuthorities_ShouldReturnEmptyWhenRoleIsNull() {
    UserDTO userDto = new UserDTO();
    userDto.setRol(null);

    Collection<? extends GrantedAuthority> authorities = userDto.getAuthorities();

    assertNotNull(authorities);
    assertTrue(authorities.isEmpty());
  }

  /** Verifica el comportamiento por defecto de los métodos de UserDetails en este DTO. */
  @Test
  void userDetailsMethods_ShouldReturnNullOrFalse() {
    UserDTO userDto = new UserDTO();
    assertNull(userDto.getPassword());
    assertTrue(userDto.isAccountNonExpired());
    assertTrue(userDto.isAccountNonLocked());
    assertTrue(userDto.isCredentialsNonExpired());
    assertTrue(userDto.isEnabled());
  }

  /** Verifica la consistencia de equals, hashCode y toString. */
  @Test
  void equalsAndHashCode_ShouldBeConsistent() {
    UserDTO d1 = new UserDTO();
    UUID uuid = UUID.randomUUID();
    d1.setUserUuid(uuid);
    UserDTO d2 = new UserDTO();
    d2.setUserUuid(uuid);
    UserDTO d3 = new UserDTO();
    d3.setUserUuid(UUID.randomUUID());

    assertEquals(d1, d2);
    assertNotEquals(d1, d3);
    assertEquals(d1.hashCode(), d2.hashCode());
    assertNotNull(d1.toString());
  }
}
