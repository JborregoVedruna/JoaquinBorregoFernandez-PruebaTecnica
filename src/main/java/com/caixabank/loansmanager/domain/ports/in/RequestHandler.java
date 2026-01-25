package com.caixabank.loansmanager.domain.ports.in;

/**
 * Interfaz genérica para los manejadores de solicitudes.
 *
 * @param <I> El tipo de la solicitud (Input) que hereda de {@link Request}.
 * @param <O> El tipo de la respuesta (Output).
 */
public interface RequestHandler<I extends Request<O>, O> {
  /**
   * Procesa la solicitud recibida y genera una respuesta.
   *
   * @param inputRequest El objeto de solicitud a procesar.
   * @return El resultado del procesamiento de la solicitud.
   */
  O handle(I inputRequest);

  /**
   * Obtiene el tipo de clase de la solicitud que este manejador puede procesar.
   *
   * @return La clase de la solicitud.
   */
  Class<I> getRequestType();
}
