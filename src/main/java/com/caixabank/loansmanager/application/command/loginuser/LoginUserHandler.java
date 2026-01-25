package com.caixabank.loansmanager.application.command.loginuser;

import com.caixabank.loansmanager.domain.model.AccessToken;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import com.caixabank.loansmanager.domain.ports.out.AuthenticationManagerI;
import com.caixabank.loansmanager.domain.ports.out.JWTProvider;
import com.caixabank.loansmanager.domain.ports.out.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Manejador encargado de procesar la autenticación de usuarios y generación de tokens.
 *
 * <p>Implementa {@link RequestHandler} para procesar peticiones de tipo {@link LoginUserRequest}.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs. {@code @Service}: Define esta clase como un
 * servicio de Spring. {@code @AllArgsConstructor}: Genera el constructor para inyectar
 * dependencias.
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoginUserHandler implements RequestHandler<LoginUserRequest, LoginUserResponse> {

  /** Repositorio para acceder a los datos de usuario persistidos. */
  private final UserJpaRepository userJpaRepository;

  /** Interfaz para gestionar la autenticación de seguridad. */
  private final AuthenticationManagerI authenticationManager;

  /** Proveedor para la generación y gestión de tokens JWT. */
  private final JWTProvider jwtProvider;

  /**
   * Procesa la solicitud de inicio de sesión.
   *
   * <p>1. Registra el inicio del proceso de login. 2. Autentica al usuario usando el gestor de
   * autenticación. 3. Recupera la información completa del usuario desde el repositorio. 4. Genera
   * tokens de acceso y de refresco mediante el proveedor JWT. 5. Devuelve una respuesta con el
   * {@link AccessToken} completo.
   *
   * @param inputRequest El objeto de solicitud con las credenciales del usuario.
   * @return {@link LoginUserResponse} que contiene el token de acceso generado.
   */
  @Override
  public LoginUserResponse handle(LoginUserRequest inputRequest) {
    log.info("Handling LoginUserRequest");
    authenticationManager.authenticate(
        inputRequest.getUser().getUsername(), inputRequest.getUser().getPassword());
    UserModel user = userJpaRepository.findByUsername(inputRequest.getUser().getUsername());
    return new LoginUserResponse(
        new AccessToken(
            jwtProvider.generateAccessToken(user),
            jwtProvider.getAccessTokenExpiresIn(),
            jwtProvider.generateRefreshToken(user),
            user.getScope()));
  }

  /**
   * Especifica el tipo de solicitud que este manejador procesa.
   *
   * @return La clase {@link LoginUserRequest}.
   */
  @Override
  public Class<LoginUserRequest> getRequestType() {
    return LoginUserRequest.class;
  }
}
