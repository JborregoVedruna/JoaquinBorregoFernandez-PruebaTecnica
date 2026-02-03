package com.caixabank.loansmanager.infrastructure.adapters.inbound.converters;

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
import com.caixabank.loansmanager.infrastructure.adapters.inbound.dto.security.out.UserRegisteredDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Interfaz Mapper para la conversión entre los DTOs de la capa de infraestructura (inbound) y los
 * modelos de dominio.
 *
 * <p>Utiliza MapStruct para la generación automática de las implementaciones de conversión.
 */
@Mapper(componentModel = "spring")
public interface InboundConverter {

  /**
   * Convierte un modelo de solicitud de préstamo en su DTO de salida para las APIs.
   *
   * @param loanApplication Modelo de dominio de la solicitud.
   * @return DTO de salida con datos formateados para el cliente.
   */
  @Mapping(target = "applicantName", source = "userModel.username")
  @Mapping(target = "applicantDni", source = "userModel.userDni")
  LoanApplicationOutput toLoanApplicationOutput(LoanApplicationModel loanApplication);

  /**
   * Convierte un DTO de entrada de solicitud de préstamo en su modelo de dominio.
   *
   * @param loanApplicationInput DTO con los datos de entrada.
   * @return Modelo de dominio inicializado.
   */
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "userModel", ignore = true)
  LoanApplicationModel toLoanApplicationModel(LoanApplicationInput loanApplicationInput);

  /**
   * Convierte un DTO de seguridad de usuario en el modelo de dominio UserModel.
   *
   * @param userDTO DTO del usuario autenticado.
   * @return El modelo de dominio correspondiente.
   */
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "accountExpirationDate", ignore = true)
  @Mapping(target = "isLocked", ignore = true)
  @Mapping(target = "credentialsExpirationDate", ignore = true)
  @Mapping(target = "isEnabled", ignore = true)
  @Mapping(target = "loanApplications", ignore = true)
  UserModel toUserModel(UserDTO userDTO);

  /**
   * Convierte un DTO de registro en un modelo de usuario para ser procesado por el dominio.
   *
   * @param userDTO DTO con datos de registro.
   * @return El modelo de usuario resultante.
   */
  @Mapping(target = "userUuid", ignore = true)
  @Mapping(target = "rol", ignore = true)
  @Mapping(target = "accountExpirationDate", ignore = true)
  @Mapping(target = "isLocked", ignore = true)
  @Mapping(target = "credentialsExpirationDate", ignore = true)
  @Mapping(target = "isEnabled", ignore = true)
  @Mapping(target = "loanApplications", ignore = true)
  UserModel registerToUserModel(RegisterRequestDTO userDTO);

  /**
   * Convierte un DTO de inicio de sesión en un modelo de usuario.
   *
   * @param userDTO DTO con credenciales.
   * @return El modelo de usuario.
   */
  @Mapping(target = "userUuid", ignore = true)
  @Mapping(target = "userDni", ignore = true)
  @Mapping(target = "rol", ignore = true)
  @Mapping(target = "accountExpirationDate", ignore = true)
  @Mapping(target = "isLocked", ignore = true)
  @Mapping(target = "credentialsExpirationDate", ignore = true)
  @Mapping(target = "isEnabled", ignore = true)
  @Mapping(target = "loanApplications", ignore = true)
  UserModel loginToUserModel(LoginRequestDTO userDTO);

  /**
   * Convierte el modelo de usuario al DTO utilizado por Spring Security.
   *
   * @param userModel El modelo de dominio.
   * @return El DTO de seguridad.
   */
  @Mapping(target = "authorities", ignore = true)
  UserDTO toUserDTO(UserModel userModel);

  /**
   * Convierte un modelo de usuario recién registrado al DTO de salida.
   *
   * @param userModel El modelo del usuario.
   * @return DTO con información del registro.
   */
  UserRegisteredDTO toUserRegisteredDTO(UserModel userModel);

  /**
   * Convierte un objeto de token de dominio a su DTO de respuesta para la API.
   *
   * @param accessToken El token de dominio.
   * @return DTO de respuesta de autenticación.
   */
  AuthResponseDTO toAuthResponseDTO(AccessToken accessToken);

  /**
   * Extrae el Refresh Token de una solicitud para convertirlo en un objeto de dominio.
   *
   * @param refreshTokenRequest DTO de la solicitud de refresco.
   * @return Modelo de dominio AccessToken con el Refresh Token.
   */
  @Mapping(target = "accessToken", ignore = true)
  @Mapping(target = "expiresIn", ignore = true)
  @Mapping(target = "scope", ignore = true)
  AccessToken toAccessToken(RefreshRequestDTO refreshTokenRequest);

  /**
   * Convierte la información de paginación de Spring Data al modelo de dominio.
   *
   * @param pageable El objeto de paginación de Spring.
   * @return El modelo de paginación del dominio.
   */
  public default PageableModel toPageableModel(Pageable pageable) {
    return new PageableModel(
        pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString());
  }

  /**
   * Convierte una página de modelos de dominio en una página de DTOs de Spring Data.
   *
   * @param pageModel La página de modelos del dominio.
   * @return La página de DTOs preparada para la respuesta REST.
   */
  public default Page<LoanApplicationOutput> toLoanApplicationOutputPage(
      PageModel<LoanApplicationModel> pageModel, PageableModel pageableModel) {
    PageRequest pr;
    if (pageableModel.getSort().equals("UNSORTED")) {
      pr = PageRequest.of(pageModel.getNumber(), pageModel.getSize());
    } else {
      String[] sort = pageableModel.getSort().split(": ");
      pr =
          PageRequest.of(
              pageableModel.getPage(),
              pageableModel.getSize(),
              sort[1].equals("ASC") ? Sort.by(sort[0]).ascending() : Sort.by(sort[0]).descending());
    }

    return new PageImpl<>(
        pageModel.getContent().stream().map(this::toLoanApplicationOutput).toList(),
        pr,
        pageModel.getTotalElements());
  }
}
