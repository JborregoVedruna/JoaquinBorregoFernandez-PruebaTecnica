package com.caixabank.loansmanager.application.mediator;

import com.caixabank.loansmanager.domain.ports.in.Request;

/**
 * Interfaz que define el patrón Mediator para desacoplar los emisores de solicitudes de sus
 * manejadores.
 *
 * <p>El mediador se encarga de dirigir cada {@link Request} al {@link RequestHandler} apropiado.
 */
public interface Mediator {
  /**
   * Despacha una solicitud al manejador correspondiente y devuelve el resultado.
   *
   * @param <O> El tipo de la respuesta (Output).
   * @param <I> El tipo de la solicitud (Input), que debe implementar {@link Request}.
   * @param inputRequest El objeto de solicitud a procesar.
   * @return El resultado del procesamiento de la solicitud.
   */
  <O, I extends Request<O>> O dispatch(I inputRequest);
}
