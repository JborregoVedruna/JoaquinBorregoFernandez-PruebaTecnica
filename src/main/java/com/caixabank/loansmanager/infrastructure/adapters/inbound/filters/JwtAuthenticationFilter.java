package com.caixabank.loansmanager.infrastructure.adapters.inbound.filters;

import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.out.JWTProvider;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Filtro de seguridad que intercepta todas las peticiones para validar la presencia y validez de un
 * JSON Web Token (JWT) de ACCESO en la cabecera Authorization. Si el Access Token es válido,
 * establece la autenticación en el SecurityContext, incluyendo las authorities (roles) cargadas
 * dinámicamente desde la base de datos.
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  /** Servicio que maneja la lógica de creación, validación y extracción de datos del JWT. */
  private final JWTProvider jwtProvider;

  /** Componente de Spring Security para cargar los detalles del usuario a partir del 'username'. */
  private final UserDetailsService userDetailsService;

  /** Conversor para transformar modelos de dominio en DTOs de entrada/salida. */
  private final InboundConverter inboundConverter;

  /** Conversor para transformar entidades de persistencia en modelos de dominio. */
  private final OutboundConverter outboundConverter;

  /** Resolver para delegar excepciones al controlador global. */
  private final HandlerExceptionResolver resolver;

  public JwtAuthenticationFilter(
      JWTProvider jwtProvider,
      UserDetailsService userDetailsService,
      InboundConverter inboundConverter,
      OutboundConverter outboundConverter,
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.jwtProvider = jwtProvider;
    this.userDetailsService = userDetailsService;
    this.inboundConverter = inboundConverter;
    this.outboundConverter = outboundConverter;
    this.resolver = resolver;
  }

  /** Prefijo de la cabecera de autorización */
  private static final String BEARER_PREFIX = "Bearer ";

  /**
   * Lógica principal del filtro que se ejecuta en cada solicitud.
   *
   * @param request La solicitud HTTP entrante.
   * @param response La respuesta HTTP saliente.
   * @param filterChain La cadena de filtros para invocar al siguiente filtro.
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String token = getTokenFromRequest(request);
    final String username;

    // 1. Verificación Inicial del Token de Acceso
    // Si no hay token en la cabecera, se omite la autenticación y se pasa al
    // siguiente filtro.
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      // 2. Extracción del Username
      // Intenta obtener el nombre de usuario (subject) del JWT. Si el token es
      // inválido
      // (expirado, mal firmado, corrupto), esta llamada lanzará una excepción.
      username = jwtProvider.getUsernameFromAccessToken(token);
    } catch (Exception e) {
      log.warn("Error processing JWT: {}", e.getMessage());
      resolver.resolveException(request, response, null, e);
      return;
    }

    // 3. Verificación de Autenticación
    // Solo procedemos si se pudo extraer un 'username' y NO hay una autenticación
    // ya existente en el SecurityContext (evitando sobrescribir autenticaciones
    // previas).
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      // 4. Carga de Detalles del Usuario
      // Se utiliza el UserDetailsService para cargar los datos completos del usuario
      // (incluidos roles/autoridades).
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      UserModel userModel = outboundConverter.toUserModel((UserEntity) userDetails);
      UserDTO principal = inboundConverter.toUserDTO(userModel);

      // 5. Validación Final del Access Token.
      if (jwtProvider.isAccessTokenValid(token, userModel)) {

        // 6. Creación del Objeto de Autenticación
        // Se crea un token de autenticación que representa al usuario actual.
        // La llamada a userDetails.getAuthorities() recupera los roles dinámicos (ej:
        // ROLE_ADMIN).
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        // 7. Configuración de Detalles de la Solicitud
        // Se añade información adicional de la petición (como IP remota) al objeto de
        // autenticación.
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 8. Establecimiento en el Contexto de Seguridad
        // Spring Security considera al usuario como 'autenticado' y 'autorizado' con
        // sus roles.
        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("Usuario autenticado exitosamente: {}", username);
      }
    }

    // 9. Continuación de la Cadena
    // Pasa el control al siguiente filtro en la cadena o al DispatcherServlet (si
    // no quedan más filtros).
    filterChain.doFilter(request, response);
  }

  /**
   * Método auxiliar para extraer el token JWT de la cabecera 'Authorization'.
   *
   * @param request La solicitud HTTP.
   * @return El token JWT sin el prefijo "Bearer ", o null si no se encuentra.
   */
  private String getTokenFromRequest(HttpServletRequest request) {
    // Obtiene el valor de la cabecera de autorización.
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // Verifica si la cabecera existe, tiene texto y comienza con el prefijo "Bearer
    // ".
    if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
      return authHeader.substring(BEARER_PREFIX.length());
    }

    return null;
  }
}
