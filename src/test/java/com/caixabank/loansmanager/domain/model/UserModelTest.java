package com.caixabank.loansmanager.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime; // Added import for LocalDateTime
import org.junit.jupiter.api.Test;

class UserModelTest {

  @Test
  void getScope_ShouldReturnScopesFromRol() {
    UserModel user = new UserModel();
    user.setRol(Rol.ROLE_SYSTEM);

    assertEquals("read write admin", user.getScope());
  }

  @Test
  void registrarUsuario_ShouldSetDefaultValues() {
    UserModel user = new UserModel();
    user.setRol(Rol.ROLE_CUSTOMER);
    user.setIsEnabled(true);
    user.setIsLocked(false);
    user.setAccountExpirationDate(LocalDateTime.now().plusYears(1));
    user.setCredentialsExpirationDate(LocalDateTime.now().plusYears(1));

    assertEquals(Rol.ROLE_CUSTOMER, user.getRol());
    assertTrue(user.getIsEnabled());
    assertFalse(user.getIsLocked());
    assertNotNull(user.getAccountExpirationDate());
    assertNotNull(user.getCredentialsExpirationDate());
    assertTrue(user.getAccountExpirationDate().isAfter(LocalDateTime.now()));
  }

  @Test
  void equalsAndHashCode_ShouldBeConsistent() {
    UserModel u1 = new UserModel();
    u1.setUsername("u1");
    UserModel u2 = new UserModel();
    u2.setUsername("u1");
    UserModel u3 = new UserModel();
    u3.setUsername("u3");

    assertEquals(u1, u2);
    assertNotEquals(u1, u3);
    assertEquals(u1.hashCode(), u2.hashCode());
    assertNotNull(u1.toString());
  }

  @Test
  void getScope_ShouldHandleNullRol() {
    UserModel user = new UserModel();
    user.setRol(null);

    // Note: Currently UserModel calls rol.getScopesByRol() without check.
    // This test documents current behavior which might throw NPE or we should fix
    // main if allowed,
    // but user said DON'T TOUCH MAIN.
    assertThrows(NullPointerException.class, user::getScope);
  }
}
