package com.caixabank.loansmanager.application.command.registeruser;

import com.caixabank.loansmanager.domain.model.Rol;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.PasswordEncoderI;
import com.caixabank.loansmanager.domain.ports.out.UserJpaRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manejador encargado de la lógica de registro de nuevos usuarios.
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link
 * RegisterUserRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs. {@code @Service}: Indica que esta clase es un
 * servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor para la inyección de
 * dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RegisterUserHandler
    implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {

  /** Repositorio para la persistencia de datos de usuario. */
  private final UserJpaRepository userJpaRepository;

  /** Codificador para asegurar las contraseñas de los usuarios. */
  private final PasswordEncoderI passwordEncoder;

  /**
   * Procesa el registro de un nuevo usuario.
   *
   * <p>1. Registra el inicio de la solicitud de registro. 2. Asigna el rol predeterminado
   * (ROLE_CUSTOMER). 3. Establece las fechas de expiración de cuenta y credenciales (1 año). 4.
   * Habilita la cuenta y asegura que no esté bloqueada. 5. Codifica la contraseña del usuario antes
   * de guardarla. 6. Persiste el usuario en la base de datos y devuelve la respuesta.
   *
   * @param inputRequest El objeto de solicitud con los datos del nuevo usuario.
   * @return {@link RegisterUserResponse} con el usuario registrado.
   */
  @Override
  public RegisterUserResponse handle(RegisterUserRequest inputRequest) {
    log.info("Handling RegisterUserRequest");
    inputRequest.getUser().setRol(Rol.ROLE_CUSTOMER);
    inputRequest.getUser().setAccountExpirationDate(LocalDateTime.now().plusYears(1));
    inputRequest.getUser().setIsLocked(false);
    inputRequest.getUser().setCredentialsExpirationDate(LocalDateTime.now().plusYears(1));
    inputRequest.getUser().setIsEnabled(true);
    log.info("Hashing password");
    String hashedPass = passwordEncoder.encode(inputRequest.getUser().getPassword());
    inputRequest.getUser().setPassword(hashedPass);
    return new RegisterUserResponse(userJpaRepository.save(inputRequest.getUser()));
  }

  /**
   * Indica el tipo de solicitud que este manejador puede procesar.
   *
   * @return La clase {@link RegisterUserRequest}.
   */
  @Override
  public Class<RegisterUserRequest> getRequestType() {
    return RegisterUserRequest.class;
  }
}
