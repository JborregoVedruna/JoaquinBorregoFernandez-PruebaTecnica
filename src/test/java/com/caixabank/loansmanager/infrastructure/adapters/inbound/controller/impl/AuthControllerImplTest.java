package com.caixabank.loansmanager.infrastructure.adapters.inbound.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.application.command.loginuser.LoginUserRequest;
import com.caixabank.loansmanager.application.command.loginuser.LoginUserResponse;
import com.caixabank.loansmanager.application.command.refreshtoken.RefreshTokenRequest;
import com.caixabank.loansmanager.application.command.refreshtoken.RefreshTokenResponse;
import com.caixabank.loansmanager.application.command.registeruser.RegisterUserRequest;
import com.caixabank.loansmanager.application.command.registeruser.RegisterUserResponse;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.domain.model.AccessToken;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.converters.InboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.LoginRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RefreshRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RegisterRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.AuthResponseDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.UserRegisteredDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Clase de prueba unitaria para {@link AuthControllerImpl}.
 *
 * <p>Verifica la integración de los flujos de autenticación, registro y gestión de sesiones.
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerImplTest {

  /** Mock del conversor inbound. */
  @Mock private InboundConverter inboundConverter;

  /** Mock del mediador de la aplicación. */
  @Mock private Mediator mediator;

  /** Instancia del controlador bajo prueba. */
  @InjectMocks private AuthControllerImpl controller;

  /** Prueba que el registro de usuario devuelva un estado 201 (CREATED). */
  @Test
  void register_ShouldReturnCreated() {
    RegisterRequestDTO dto = new RegisterRequestDTO();
    UserModel model = new UserModel();
    RegisterUserResponse response = new RegisterUserResponse(model);
    UserRegisteredDTO output = new UserRegisteredDTO();

    when(inboundConverter.registerToUserModel(dto)).thenReturn(model);
    when(mediator.dispatch(any(RegisterUserRequest.class))).thenReturn(response);
    when(inboundConverter.toUserRegisteredDTO(model)).thenReturn(output);

    ResponseEntity<UserRegisteredDTO> result = controller.register(dto);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals(output, result.getBody());
  }

  /** Prueba que el inicio de sesión devuelva un estado 200 (OK). */
  @Test
  void login_ShouldReturnOk() {
    LoginRequestDTO dto = new LoginRequestDTO();
    UserModel model = new UserModel();
    AccessToken token = new AccessToken();
    LoginUserResponse response = new LoginUserResponse(token);
    AuthResponseDTO output = new AuthResponseDTO();

    when(inboundConverter.loginToUserModel(dto)).thenReturn(model);
    when(mediator.dispatch(any(LoginUserRequest.class))).thenReturn(response);
    when(inboundConverter.toAuthResponseDTO(token)).thenReturn(output);

    ResponseEntity<AuthResponseDTO> result = controller.login(dto);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(output, result.getBody());
  }

  /** Prueba que la renovación de tokens devuelva un estado 200 (OK). */
  @Test
  void refresh_ShouldReturnOk() {
    RefreshRequestDTO dto = new RefreshRequestDTO();
    AccessToken token = new AccessToken();
    RefreshTokenResponse response = new RefreshTokenResponse(token);
    AuthResponseDTO output = new AuthResponseDTO();

    when(inboundConverter.toAccessToken(dto)).thenReturn(token);
    when(mediator.dispatch(any(RefreshTokenRequest.class))).thenReturn(response);
    when(inboundConverter.toAuthResponseDTO(token)).thenReturn(output);

    ResponseEntity<AuthResponseDTO> result = controller.refreshToken(dto);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(output, result.getBody());
  }

  /** Prueba que el endpoint de información del usuario actual devuelva un estado 200 (OK). */
  @Test
  void me_ShouldReturnOk() {
    UserDTO userDto = new UserDTO();
    ResponseEntity<UserDTO> result = controller.me(userDto);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(userDto, result.getBody());
  }
}
