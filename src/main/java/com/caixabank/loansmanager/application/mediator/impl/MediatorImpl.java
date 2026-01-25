package com.caixabank.loansmanager.application.mediator.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.caixabank.loansmanager.application.mediator.Mediator;
import com.caixabank.loansmanager.domain.ports.in.Request;
import com.caixabank.loansmanager.domain.ports.in.RequestHandler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Data
public class MediatorImpl implements Mediator {

    // Mapa que asocia cada tipo de Request con su RequestHandler correspondiente.
    final Map<Class<?>, RequestHandler<?, ?>> requestHandlerMap;

    // El constructor recibe una lista de RequestHandlers y construye el mapa.
    public MediatorImpl(List<RequestHandler<?, ?>> requestHandlers) {
        requestHandlerMap = requestHandlers.stream()
                .collect(Collectors.toMap(RequestHandler::getRequestType, Function.identity()));
    }

    @Override
    public <O, I extends Request<O>> O dispatch(I inputRequest) {
        log.info("Dispatching request: {}", inputRequest);
        RequestHandler<I, O> requestHandler = (RequestHandler<I, O>) requestHandlerMap.get(inputRequest.getClass());
        log.info("Found handler: {}", requestHandler);
        if (requestHandler == null) {
            throw new UnsupportedOperationException("No handler found for request type: " + inputRequest.getClass());

        }
        log.info("Dispatching request: {}", inputRequest);
        return requestHandler.handle(inputRequest);
    }

}
