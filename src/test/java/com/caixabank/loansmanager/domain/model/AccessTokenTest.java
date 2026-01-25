package com.caixabank.loansmanager.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccessTokenTest {
  @Test
  void testAccessTokenProperties() {
    AccessToken token = new AccessToken("access", 3600L, "refresh", "read");
    assertEquals("access", token.getAccessToken());
    assertEquals(3600L, token.getExpiresIn());
    assertEquals("refresh", token.getRefreshToken());
    assertEquals("read", token.getScope());

    AccessToken empty = new AccessToken();
    assertNull(empty.getAccessToken());
  }

  @Test
  void equalsAndHashCode_ShouldBeConsistent() {
    AccessToken t1 = new AccessToken("a", 1L, "r", "s");
    AccessToken t2 = new AccessToken("a", 1L, "r", "s");
    AccessToken t3 = new AccessToken("b", 1L, "r", "s");

    assertEquals(t1, t2);
    assertNotEquals(t1, t3);
    assertNotEquals(t1, null);
    assertEquals(t1.hashCode(), t2.hashCode());
    assertNotNull(t1.toString());
  }
}
