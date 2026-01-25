package com.caixabank.loansmanager.infrastructure.adapters.inbound.filters;

import static org.mockito.Mockito.*;

import com.caixabank.loansmanager.domain.model.Rol;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.out.JWTProvider;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Clase de prueba unitaria para {@link JwtAuthenticationFilter}.
 *
 * <p>Evalúa la interceptación de peticiones HTTP, validación de tokens JWT y establecimiento del
 * contexto de seguridad de Spring.
 */
@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

  /** Mock del proveedor de tokens JWT. */
  @Mock private JWTProvider jwtProvider;

  /** Mock del servicio de detalles de usuario. */
  @Mock private UserDetailsService userDetailsService;

  /** Mock del conversor inbound. */
  @Mock private InboundConverter inboundConverter;

  /** Mock del conversor outbound. */
  @Mock private OutboundConverter outboundConverter;

  /** Mock de la petición HTTP. */
  @Mock private HttpServletRequest request;

  /** Mock de la respuesta HTTP. */
  @Mock private HttpServletResponse response;

  /** Mock de la cadena de filtros. */
  @Mock private FilterChain filterChain;

  /** Filtro bajo prueba con mocks inyectados. */
  @InjectMocks private JwtAuthenticationFilter filter;

  /**
   * Prueba que el filtro autentique correctamente al usuario cuando se proporciona un token válido.
   */
  @Test
  void doFilterInternal_WithValidToken_ShouldAuthenticate() throws Exception {
    SecurityContextHolder.clearContext();
    when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
    when(jwtProvider.getUsernameFromAccessToken("valid-token")).thenReturn("user");

    UserEntity entity = new UserEntity();
    UserModel model = new UserModel();
    UserDTO dto = new UserDTO();
    dto.setRol(Rol.ROLE_CUSTOMER);

    when(userDetailsService.loadUserByUsername("user")).thenReturn(entity);
    when(outboundConverter.toUserModel(entity)).thenReturn(model);
    when(inboundConverter.toUserDTO(model)).thenReturn(dto);
    when(jwtProvider.isAccessTokenValid("valid-token", model)).thenReturn(true);

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assert SecurityContextHolder.getContext().getAuthentication() != null;
  }

  /** Prueba que el filtro no autentique cuando no se proporciona token en la cabecera. */
  @Test
  void doFilterInternal_WithNoToken_ShouldNotAuthenticate() throws Exception {
    SecurityContextHolder.clearContext();
    when(request.getHeader("Authorization")).thenReturn(null);

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assert SecurityContextHolder.getContext().getAuthentication() == null;
  }

  /** Prueba que el filtro no autentique cuando el token proporcionado es inválido por JWT. */
  @Test
  void doFilterInternal_WithInvalidToken_ShouldNotAuthenticate() throws Exception {
    SecurityContextHolder.clearContext();
    when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
    when(jwtProvider.getUsernameFromAccessToken("invalid-token"))
        .thenThrow(new JwtException("Invalid"));

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assert SecurityContextHolder.getContext().getAuthentication() == null;
  }

  /** Prueba la gestión de excepciones genéricas durante la validación del token. */
  @Test
  void doFilterInternal_WithGenericException_ShouldNotAuthenticate() throws Exception {
    SecurityContextHolder.clearContext();
    when(request.getHeader("Authorization")).thenReturn("Bearer error-token");
    when(jwtProvider.getUsernameFromAccessToken("error-token"))
        .thenThrow(new RuntimeException("Error"));

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assert SecurityContextHolder.getContext().getAuthentication() == null;
  }

  /** Prueba que el filtro no sobrescriba una autenticación ya existente en el contexto. */
  @Test
  void doFilterInternal_WithExistingAuthentication_ShouldNotOverwrite() throws Exception {
    SecurityContextHolder.clearContext();
    SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));
    when(request.getHeader("Authorization")).thenReturn("Bearer token");
    when(jwtProvider.getUsernameFromAccessToken("token")).thenReturn("user");

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(userDetailsService, never()).loadUserByUsername(anyString());
  }

  /** Prueba que el filtro no autentique si el Access Token no es válido según el proveedor. */
  @Test
  void doFilterInternal_WithInvalidAccessToken_ShouldNotAuthenticate() throws Exception {
    SecurityContextHolder.clearContext();
    when(request.getHeader("Authorization")).thenReturn("Bearer token");
    when(jwtProvider.getUsernameFromAccessToken("token")).thenReturn("user");

    UserEntity entity = new UserEntity();
    UserModel model = new UserModel();

    when(userDetailsService.loadUserByUsername("user")).thenReturn(entity);
    when(outboundConverter.toUserModel(entity)).thenReturn(model);
    when(inboundConverter.toUserDTO(model)).thenReturn(new UserDTO());
    when(jwtProvider.isAccessTokenValid("token", model)).thenReturn(false);

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assert SecurityContextHolder.getContext().getAuthentication() == null;
  }
}
