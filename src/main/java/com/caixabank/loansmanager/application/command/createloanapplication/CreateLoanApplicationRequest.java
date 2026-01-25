package com.caixabank.loansmanager.application.command.createloanapplication;

import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.in.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase que representa la solicitud para crear una nueva solicitud de préstamo.
 *
 * <p>{@code @Data}: Anotación de Lombok que genera automáticamente getters, setters, toString,
 * equals y hashCode. {@code @AllArgsConstructor}: Genera un constructor con todos los campos de la
 * clase.
 */
@Data
@AllArgsConstructor
public class CreateLoanApplicationRequest implements Request<CreateLoanApplicationResponse> {

  /** Modelo que contiene la información del usuario que realiza la solicitud. */
  private UserModel userModel;

  /** Modelo que contiene los detalles de la solicitud de préstamo a crear. */
  private LoanApplicationModel loanApplication;
}
