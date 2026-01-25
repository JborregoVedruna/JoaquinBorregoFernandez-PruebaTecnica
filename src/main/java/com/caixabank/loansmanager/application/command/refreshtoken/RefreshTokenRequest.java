package com.caixabank.loansmanager.application.command.refreshtoken;

import com.caixabank.loansmanager.domain.model.AccessToken;
import com.caixabank.loansmanager.domain.ports.in.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para refrescar un token de acceso.
 *
 * <p>{@code @Data}: Anotación de Lombok para métodos de acceso y utilidad.
 * {@code @AllArgsConstructor}: Genera un constructor con todos los campos.
 */
@Data
@AllArgsConstructor
public class RefreshTokenRequest implements Request<RefreshTokenResponse> {

  /** El objeto AccessToken que contiene el Refresh Token necesario para la operación. */
  private AccessToken accessToken;
}
