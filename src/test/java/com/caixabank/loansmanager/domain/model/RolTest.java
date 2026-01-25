package com.caixabank.loansmanager.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RolTest {

  @Test
  void getScopesByRol_ShouldReturnCorrectScopes() {
    assertEquals("read write", Rol.ROLE_CUSTOMER.getScopesByRol());
    assertEquals("read write", Rol.ROLE_MANAGER.getScopesByRol());
    assertEquals("read write admin", Rol.ROLE_SYSTEM.getScopesByRol());
  }
}
