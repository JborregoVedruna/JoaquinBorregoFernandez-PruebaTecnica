package com.caixabank.loansmanager.config;

import com.caixabank.loansmanager.infrastructure.adapters.inbound.filters.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

  /** Filtro personalizado para procesar y validar JSON Web Tokens (JWT). */
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /**
   * Proveedor de autenticación configurado para la carga de usuarios y
   * codificación de contraseñas
   * (definido en ApplicationConfig).
   */
  private final AuthenticationProvider authProvider;

  /**
   * Define la cadena de filtros de seguridad (SecurityFilterChain) que
   * interceptará todas las
   * peticiones HTTP.
   *
   * <p>
   * Esta es la configuración central de la seguridad de la aplicación.
   *
   * @param http Objeto para configurar Spring Security a nivel HTTP.
   * @return La cadena de filtros de seguridad construida.
   * @throws Exception Si ocurre un error durante la configuración.
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        // 1. Deshabilita la protección CSRF (CSRF es innecesario para APIs REST sin
        // sesiones)
        .csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        // 2. Configura las reglas de autorización para las peticiones HTTP
        .authorizeHttpRequests(
            authReq -> authReq
                // Permite acceso sin autenticación a endpoints públicos y de
                // documentación
                .requestMatchers("/v3/api-docs/**")
                .permitAll() // OpenAPI/Swagger Docs
                .requestMatchers("/swagger-ui/**")
                .permitAll() // Swagger
                .requestMatchers("/swagger-ui.html")
                .permitAll() // Swagger
                .requestMatchers("/error")
                .permitAll() // Error
                .requestMatchers("/h2-console/**")
                .permitAll() // H2 Console
                .requestMatchers("/api/v1/auth/register")
                .permitAll()
                .requestMatchers("/api/v1/auth/login")
                .permitAll()
                .requestMatchers("/actuator/**")
                .permitAll()
                .requestMatchers("/public/**")
                .permitAll()
                // Cualquier otra petición requiere autenticación
                .anyRequest()
                .authenticated())
        // 3. Configura la gestión de sesiones como STATELESS
        // Esto es crucial para el uso de JWT, ya que no se almacenan estados de sesión
        // en el servidor
        .sessionManagement(
            sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 4. Asigna el proveedor de autenticación personalizado
        .authenticationProvider(authProvider)
        // 5. Agrega el filtro JWT antes del filtro estándar de autenticación por nombre
        // de usuario y contraseña
        // Esto asegura que cada petición con un JWT sea autenticada antes de llegar a
        // los recursos.
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        // Permitir el uso de Frames (indispensable para ver la interfaz de H2)
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        .build();
  }
}
