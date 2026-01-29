package com.caixabank.loansmanager.infrastructure.adapters.inbound.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.caixabank.loansmanager.domain.model.AccessToken;
import com.caixabank.loansmanager.domain.model.LoanApplicationModel;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.input.LoanApplicationInput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.output.LoanApplicationOutput;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.UserDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.LoginRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RefreshRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.in.RegisterRequestDTO;
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.AuthResponseDTO;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/** Clase de prueba unitaria para {@link InboundConverter}. */
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
  @Test
  void converter_ShouldNotBeNull() {
    assertNotNull(converter);
  }

  @Test
  void toPageableModel_ShouldConvertCorrectly() {
    Pageable pageable = PageRequest.of(1, 20, Sort.by("name").ascending());
    PageableModel result = converter.toPageableModel(pageable);

    assertEquals(1, result.getPage());
    assertEquals(20, result.getSize());
    assertEquals("name: ASC", result.getSort());
  }

  @Test
  void toLoanApplicationOutputPage_ShouldConvertCorrectly_WithSort() {
    PageableModel pageableModel = new PageableModel(0, 10, "requestDate: DESC");
    LoanApplicationModel model = new LoanApplicationModel();
    PageModel<LoanApplicationModel> pageModel =
        new PageModel<>(Collections.singletonList(model), 1, 1, 1, 10, 0);

    Page<LoanApplicationOutput> result =
        converter.toLoanApplicationOutputPage(pageModel, pageableModel);

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(0, result.getNumber());
    assertEquals(10, result.getSize());
    assertEquals(Sort.by("requestDate").descending(), result.getSort());
  }

  @Test
  void toLoanApplicationOutputPage_ShouldConvertCorrectly_Unsorted() {
    PageableModel pageableModel = new PageableModel(0, 10, "UNSORTED");
    LoanApplicationModel model = new LoanApplicationModel();
    PageModel<LoanApplicationModel> pageModel =
        new PageModel<>(Collections.singletonList(model), 1, 1, 1, 10, 0);

    Page<LoanApplicationOutput> result =
        converter.toLoanApplicationOutputPage(pageModel, pageableModel);

    assertNotNull(result);
    assertEquals(Sort.unsorted(), result.getSort());
  }
}
