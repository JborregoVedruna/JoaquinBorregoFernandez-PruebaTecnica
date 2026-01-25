package com.caixabank.loansmanager.infrastructure.adapters.outbound.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Clase de prueba unitaria para {@link JWTProviderImpl}.
 *
 * <p>Verifica la creación, validación y extracción de información de tokens JWT tanto para acceso
 * como para refresco.
 */
@ExtendWith(MockitoExtension.class)
class JWTProviderImplTest {

  /** Mock del conversor outbound. */
  @Mock private OutboundConverter outboundConverter;

  /** Proveedor JWT bajo prueba. */
  @InjectMocks private JWTProviderImpl jwtProvider;

  private final String secretKey =
      "VGhpcyBpcyBhIHZlcnkgbG9uZyBzZWNyZXQga2V5IGZvciB0ZXN0aW5nIHB1cnBvc2Vz"; // Must be
  // long
  // enough
  // for
  // HS256
  private final Long expiration = 3600000L; // 1 hour

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(jwtProvider, "accessTokenSecretKey", secretKey);
    ReflectionTestUtils.setField(jwtProvider, "accessTokenExpiration", expiration);
    ReflectionTestUtils.setField(jwtProvider, "refreshTokenSecretKey", secretKey);
    ReflectionTestUtils.setField(jwtProvider, "refreshTokenExpiration", expiration);
  }

  @Test
  void getKey_ShouldReturnBytes() {
    byte[] keyBytes = jwtProvider.getKey(secretKey);
    assertNotNull(keyBytes);
    assertTrue(keyBytes.length > 0);
  }

  @Test
  void generateAccessToken_ShouldReturnToken() {
    UserModel user = new UserModel();
    user.setUsername("testuser");
    user.setUserDni("12345678A");

    String token = jwtProvider.generateAccessToken(user);

    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  void generateRefreshToken_ShouldReturnToken() {
    UserModel user = new UserModel();
    user.setUsername("testuser");

    String token = jwtProvider.generateRefreshToken(user);

    assertNotNull(token);
    assertFalse(token.isEmpty());
  }

  @Test
  void getUsernameFromAccessToken_ShouldReturnUsername() {
    UserModel user = new UserModel();
    user.setUsername("testuser");
    user.setUserDni("12345678A");
    String token = jwtProvider.generateAccessToken(user);

    String username = jwtProvider.getUsernameFromAccessToken(token);

    assertEquals("testuser", username);
  }

  @Test
  void getUsernameFromRefreshToken_ShouldReturnUsername() {
    UserModel user = new UserModel();
    user.setUsername("testuser");
    String token = jwtProvider.generateRefreshToken(user);

    String username = jwtProvider.getUsernameFromRefreshToken(token);

    assertEquals("testuser", username);
  }

  @Test
  void isAccessTokenValid_ShouldReturnFalse_WhenUsernameMismatches() {
    UserModel user = new UserModel();
    user.setUsername("testuser");
    user.setUserDni("12345678A");

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("wronguser");

    String token = jwtProvider.generateAccessToken(user);

    when(outboundConverter.toUserEntity(user)).thenReturn(userEntity);

    assertFalse(jwtProvider.isAccessTokenValid(token, user));
  }

  @Test
  void isAccessTokenValid_ShouldThrowException_WhenTokenIsExpired() {
    // Set small expiration to test expired token
    ReflectionTestUtils.setField(jwtProvider, "accessTokenExpiration", -1000L);

    UserModel user = new UserModel();
    user.setUsername("testuser");
    user.setUserDni("12345678A");

    String token = jwtProvider.generateAccessToken(user);

    assertThrows(ExpiredJwtException.class, () -> jwtProvider.isAccessTokenValid(token, user));
  }

  @Test
  void isRefreshTokenValid_ShouldReturnFalse_WhenUsernameMismatches() {
    UserModel user = new UserModel();
    user.setUsername("testuser");

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("wronguser");

    String token = jwtProvider.generateRefreshToken(user);

    when(outboundConverter.toUserEntity(user)).thenReturn(userEntity);

    assertFalse(jwtProvider.isRefreshTokenValid(token, user));
  }

  @Test
  void isRefreshTokenValid_ShouldThrowException_WhenTokenIsExpired() {
    ReflectionTestUtils.setField(jwtProvider, "refreshTokenExpiration", -1000L);

    UserModel user = new UserModel();
    user.setUsername("testuser");

    String token = jwtProvider.generateRefreshToken(user);

    assertThrows(ExpiredJwtException.class, () -> jwtProvider.isRefreshTokenValid(token, user));
  }

  @Test
  void isAccessTokenValid_ShouldReturnTrue_WhenTokenIsValid() {
    UserModel user = new UserModel();
    user.setUsername("testuser");
    user.setUserDni("12345678A");

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("testuser");

    String token = jwtProvider.generateAccessToken(user);

    when(outboundConverter.toUserEntity(user)).thenReturn(userEntity);

    assertTrue(jwtProvider.isAccessTokenValid(token, user));
  }

  @Test
  void isRefreshTokenValid_ShouldReturnTrue_WhenTokenIsValid() {
    UserModel user = new UserModel();
    user.setUsername("testuser");

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("testuser");

    String token = jwtProvider.generateRefreshToken(user);

    when(outboundConverter.toUserEntity(user)).thenReturn(userEntity);

    assertTrue(jwtProvider.isRefreshTokenValid(token, user));
  }

  @Test
  void getAccessTokenExpiresIn_ShouldReturnCorrectValue() {
    assertEquals(expiration / 1000, jwtProvider.getAccessTokenExpiresIn());
  }

  @Test
  void getRefreshTokenExpiresIn_ShouldReturnCorrectValue() {
    assertEquals(expiration / 1000, jwtProvider.getRefreshTokenExpiresIn());
  }

  @Test
  void getAllClaims_ShouldReturnClaimsMap() {
    UserModel user = new UserModel();
    user.setUsername("testuser");
    user.setUserDni("12345678A");
    String token = jwtProvider.generateAccessToken(user);

    Map<String, Object> claims = jwtProvider.getAllClaims(token, secretKey);

    assertNotNull(claims);
    assertEquals("testuser", claims.get("sub"));
    assertEquals("12345678A", claims.get("dni"));
  }
}
