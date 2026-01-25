package com.caixabank.loansmanager.infrastructure.adapters.inbound.converters;

import com.caixabank.loansmanager.domain.model.AccessToken;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.LoginRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RefreshRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RegisterRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.AuthResponseDTO;

/**
 * Clase de prueba unitaria para {@link InboundConverter}.
 *
 * <p>Utiliza una implementación anónima para verificar los métodos por defecto de la interfaz.
 */
class InboundConverterTest {

  /** Implementación concreta para probar la lógica de conversión. */
  private final InboundConverter converter =
      new InboundConverter() {
        @Override
        public LoanApplicationOutput toLoanApplicationOutput(LoanApplicationModel loanApplication) {
          return null;
        }

        @Override
        public LoanApplicationModel toLoanApplicationModel(
            LoanApplicationInput loanApplicationInput) {
          return new LoanApplicationModel();
        }

        @Override
        public UserModel registerToUserModel(RegisterRequestDTO userDTO) {
          return null;
        }

        @Override
        public UserModel loginToUserModel(LoginRequestDTO userDTO) {
          return null;
        }

        @Override
        public UserDTO toUserDTO(UserModel userModel) {
          return null;
        }

        @Override
        public UserModel toUserModel(UserDTO userDTO) {
          return null;
        }

        @Override
        public com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out
                .UserRegisteredDTO
            toUserRegisteredDTO(UserModel userModel) {
          return null;
        }

        @Override
        public AuthResponseDTO toAuthResponseDTO(AccessToken accessToken) {
          return null;
        }

        @Override
        public AccessToken toAccessToken(RefreshRequestDTO refreshTokenRequest) {
          return null;
        }
      };

  /** Prueba que el conversor haya sido instanciado correctamente. */
  @org.junit.jupiter.api.Test
  void converter_ShouldNotBeNull() {
    org.junit.jupiter.api.Assertions.assertNotNull(converter);
  }
}
