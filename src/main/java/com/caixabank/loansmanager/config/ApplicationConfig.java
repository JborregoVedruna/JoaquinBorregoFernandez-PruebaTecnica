package com.caixabank.loansmanager.config;

import com.caixabank.loansmanager.domain.exceptions.UserNotFoundException;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories.UserRepository;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Loans Manager",
            version = "v1",
            description = "A loans manager for a caixabank technical interview",
            contact = @Contact(name = "Joaquin Borrego Fernandez", email = "juakylc14@gmail.com"),
            license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/2.0")))
@Configuration
@AllArgsConstructor
public class ApplicationConfig {

  /** Repositorio utilizado para acceder a los datos de los usuarios. */
  private final UserRepository userRepo;

  /**
   * Define el Bean de AuthenticationManager.
   *
   * <p>AuthenticationManager es el componente principal de Spring Security que delega la
   * autenticación a los AuthenticationProviders.
   *
   * @param config La configuración de autenticación proporcionada por Spring Security.
   * @return El gestor de autenticación configurado.
   * @throws Exception Si ocurre un error al obtener el gestor de autenticación.
   */
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * Define el Bean de AuthenticationProvider.
   *
   * <p>Configura un DaoAuthenticationProvider, que es el encargado de buscar los detalles del
   * usuario a través del UserDetailsService y de verificar la contraseña usando el PasswordEncoder.
   *
   * @param config La configuración de autenticación (aunque no se utiliza directamente en este
   *     método, se mantiene para consistencia si se requiere en el futuro).
   * @return El proveedor de autenticación configurado.
   */
  @Bean
  AuthenticationProvider authenticationProvider(AuthenticationConfiguration config) {
    // Crea un proveedor que usa nuestro UserDetailsService personalizado
    DaoAuthenticationProvider authenticationProvider =
        new DaoAuthenticationProvider(userDetailService());

    // Asigna el codificador de contraseñas
    authenticationProvider.setPasswordEncoder(passwordEncoder());

    return authenticationProvider;
  }

  /**
   * Define el Bean de UserDetailsService.
   *
   * <p>Este es un contrato de Spring Security que define cómo se carga la información del usuario
   * (incluyendo roles y contraseña codificada) a partir de un nombre de usuario.
   *
   * <p>Aquí se implementa buscando al usuario por nombre de usuario en el UserRepository.
   *
   * @return Una implementación de UserDetailsService.
   * @throws UserNotFoundException Si el usuario no es encontrado.
   */
  @Bean
  UserDetailsService userDetailService() {
    return username ->
        userRepo
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  /**
   * Define el Bean de PasswordEncoder.
   *
   * <p>Especifica qué algoritmo se utilizará para codificar y verificar las contraseñas.
   * BCryptPasswordEncoder es el estándar recomendado por Spring Security.
   *
   * @return Una instancia de BCryptPasswordEncoder.
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * @param observationRegistry
   * @return
   */
  @Bean
  ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
    return new ObservedAspect(observationRegistry);
  }
}
