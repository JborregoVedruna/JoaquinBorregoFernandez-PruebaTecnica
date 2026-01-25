package com.caixabank.loansmanager.application.mediator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.ports.in.Request;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Clase de prueba unitaria para {@link MediatorImpl}.
 *
 * <p>Verifica el correcto despacho de peticiones a sus respectivos manejadores.
 */
@ExtendWith(MockitoExtension.class)
class MediatorImplTest {

  /** Instancia del mediador bajo prueba. */
  private MediatorImpl mediator;

  /** Verifica que el mediador delegue la petición al manejador adecuado si este está registrado. */
  @Test
  void dispatch_ShouldDelegateToHandler_WhenHandlerExists() {
    // Mock Setup
    TestRequest request = new TestRequest();
    TestResponse response = new TestResponse();

    RequestHandler<TestRequest, TestResponse> handler = mock(RequestHandler.class);
    when(handler.getRequestType()).thenReturn(TestRequest.class);
    when(handler.handle(request)).thenReturn(response);

    // Initialize Mediator with list of handlers
    mediator = new MediatorImpl(Collections.singletonList(handler));

    // Execute
    TestResponse result = mediator.dispatch(request);

    // Verify
    assertEquals(response, result);
  }

  /**
   * Prueba que el mediador lance una excepción cuando no se encuentra un manejador para el tipo de
   * petición.
   */
  @Test
  void dispatch_ShouldThrowException_WhenHandlerNotFound() {
    TestRequest request = new TestRequest();
    // Empty handler list
    mediator = new MediatorImpl(Collections.emptyList());

    assertThrows(UnsupportedOperationException.class, () -> mediator.dispatch(request));
  }

  static class TestRequest implements Request<TestResponse> {}

  static class TestResponse {}
}
