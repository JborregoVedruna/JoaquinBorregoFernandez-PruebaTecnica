package com.caixabank.loansmanager.application.mediator;

import com.caixabank.loansmanager.domain.ports.in.Request;

public interface Mediator {
    // Despacha la Request al RequestHandler correspondiente y devuelve
    // el output.
    <O, I extends Request<O>> O dispatch(I inputRequest);
}
