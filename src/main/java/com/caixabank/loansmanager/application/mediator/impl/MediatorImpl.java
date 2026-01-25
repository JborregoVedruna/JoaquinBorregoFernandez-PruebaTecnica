package com.caixabank.loansmanager.application.mediator.impl;

import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.domain.ports.in.Request;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementación concreta del mediador que utiliza un mapa de manejadores inyectados por Spring.
 *
 * <p>{@code @Slf4j}: Habilita el registro de logs. {@code @Component}: Marca esta clase como un
 * componente gestionado por Spring. {@code @Data}: Genera métodos de utilidad de Lombok.
 */
@Slf4j
@Component
@Data
public class MediatorImpl implements Mediator {

  /** Mapa interno que asocia cada clase de solicitud con su manejador correspondiente. */
  private final Map<Class<?>, RequestHandler<?, ?>> requestHandlerMap;

  /**
   * Constructor que inicializa el mediador registrando todos los RequestHandlers disponibles.
   *
   * @param requestHandlers Lista de todos los manejadores encontrados en el contexto de Spring.
   */
  public MediatorImpl(List<RequestHandler<?, ?>> requestHandlers) {
    requestHandlerMap =
        requestHandlers.stream()
            .collect(Collectors.toMap(RequestHandler::getRequestType, Function.identity()));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Localiza el manejador registrado para el tipo de solicitud recibida y ejecuta su lógica.
   *
   * @throws UnsupportedOperationException Si no se encuentra un manejador para el tipo de
   *     solicitud.
   */
  @Override
  public <O, I extends Request<O>> O dispatch(I inputRequest) {
    log.info("Dispatching request: {}", inputRequest);
    RequestHandler<I, O> requestHandler =
        (RequestHandler<I, O>) requestHandlerMap.get(inputRequest.getClass());
    log.info("Found handler: {}", requestHandler);
    if (requestHandler == null) {
      throw new UnsupportedOperationException(
          "No handler found for request type: " + inputRequest.getClass());
    }
    log.info("Dispatching request: {}", inputRequest);
    return requestHandler.handle(inputRequest);
  }
}
