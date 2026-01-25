package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import com.caixabank.loansmanager.application.command.loginuser.LoginUserRequest;
import com.caixabank.loansmanager.application.command.refreshtoken.RefreshTokenRequest;
import com.caixabank.loansmanager.application.command.registeruser.RegisterUserRequest;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.AuthController;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.LoginRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RefreshRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RegisterRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.AuthResponseDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.UserRegisteredDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación del controlador REST para la autenticación de usuarios.
 *
 * <p>{@code @Slf4j}: Habilita logs. {@code @AllArgsConstructor}: Genera constructor para inyección
 * de dependencias. {@code @CrossOrigin}: Permite peticiones CORS. {@code @RestController}: Define
 * esta clase como controlador REST de Spring.
 */
@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
public class AuthControllerImpl implements AuthController {

  /** Conversor para transformar entre DTOs de entrada/salida y modelos de dominio. */
  private final InboundConverter inboundConverter;

  /** Mediador para despachar las solicitudes a sus manejadores correspondientes. */
  private final Mediator mediator;

  /**
   * {@inheritDoc}
   *
   * <p>Procesa el registro convirtiendo el DTO a modelo y despachando la solicitud al mediador.
   */
  @Override
  public ResponseEntity<UserRegisteredDTO> register(@Valid RegisterRequestDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            inboundConverter.toUserRegisteredDTO(
                mediator
                    .dispatch(
                        new RegisterUserRequest(inboundConverter.registerToUserModel(request)))
                    .getUser()));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Gestiona el inicio de sesión despachando la solicitud de login al mediador.
   */
  @Override
  public ResponseEntity<AuthResponseDTO> login(@Valid LoginRequestDTO request) {
    return ResponseEntity.ok(
        inboundConverter.toAuthResponseDTO(
            mediator
                .dispatch(new LoginUserRequest(inboundConverter.loginToUserModel(request)))
                .getAccessToken()));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Retorna la información del usuario principal autenticado.
   */
  @Override
  public ResponseEntity<UserDTO> me(UserDTO userLogueado) {
    return ResponseEntity.ok(userLogueado);
  }

  /**
   * {@inheritDoc}
   *
   * <p>Solicita la renovación del token de acceso a través del mediador.
   */
  @Override
  public ResponseEntity<AuthResponseDTO> refreshToken(@Valid RefreshRequestDTO request) {
    return ResponseEntity.ok(
        inboundConverter.toAuthResponseDTO(
            mediator
                .dispatch(new RefreshTokenRequest(inboundConverter.toAccessToken(request)))
                .getAccessToken()));
  }
}
