package com.caixabank.loansmanager.application.mediator.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import com.caixabank.loansmanager.domain.ports.in.Request;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MediatorImplTest {

    private MediatorImpl mediator;

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

    @Test
    void dispatch_ShouldThrowException_WhenHandlerNotFound() {
        TestRequest request = new TestRequest();
        // Empty handler list
        mediator = new MediatorImpl(Collections.emptyList());

        assertThrows(UnsupportedOperationException.class, () -> mediator.dispatch(request));
    }

    static class TestRequest implements Request<TestResponse> {
    }

    static class TestResponse {
    }
}
