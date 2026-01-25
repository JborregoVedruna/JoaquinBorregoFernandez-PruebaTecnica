package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.caixabank.loansmanager.domain.exceptions.UserNotFoundException;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories.UserRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Clase de prueba unitaria para {@link UserJpaRepositoryImpl}.
 *
 * <p>Valida la persistencia de usuarios y las operaciones de búsqueda personalizada.
 */
@ExtendWith(MockitoExtension.class)
class UserJpaRepositoryImplTest {

  /** Mock del conversor outbound. */
  @Mock private OutboundConverter outboundConverter;

  /** Mock del repositorio JPA de Spring Data. */
  @Mock private UserRepository userRepository;

  /** Instancia del repositorio bajo prueba. */
  @InjectMocks private UserJpaRepositoryImpl userJpaRepository;

  /** Verifica que el flujo de guardado devuelva el modelo de usuario correcto. */
  @Test
  void save_ShouldReturnUserModel() {
    UserModel userModel = new UserModel();
    UserEntity userEntity = new UserEntity();

    when(outboundConverter.toUserEntity(userModel)).thenReturn(userEntity);
    when(userRepository.save(userEntity)).thenReturn(userEntity);
    when(outboundConverter.toUserModel(userEntity)).thenReturn(userModel);

    UserModel result = userJpaRepository.save(userModel);

    assertNotNull(result);
    verify(userRepository).save(userEntity);
  }

  @Test
  void findAll_ShouldReturnPageModel() {
    PageableModel pageableModel = new PageableModel();
    Pageable pageable = Pageable.unpaged();
    Page<UserEntity> page = new PageImpl<>(Collections.emptyList());
    PageModel<UserModel> pageModel = new PageModel<>();

    when(outboundConverter.toPageable(pageableModel)).thenReturn(pageable);
    when(userRepository.findAll(pageable)).thenReturn(page);
    when(outboundConverter.toUserModelPage(page)).thenReturn(pageModel);

    PageModel<UserModel> result = userJpaRepository.findAll(pageableModel);

    assertNotNull(result);
    verify(userRepository).findAll(pageable);
  }

  @Test
  void findById_ShouldReturnUserModel_WhenFound() {
    UUID uuid = UUID.randomUUID();
    UserEntity userEntity = new UserEntity();
    UserModel userModel = new UserModel();

    when(userRepository.findById(uuid)).thenReturn(Optional.of(userEntity));
    when(outboundConverter.toUserModel(userEntity)).thenReturn(userModel);

    UserModel result = userJpaRepository.findById(uuid);

    assertNotNull(result);
  }

  @Test
  void findById_ShouldThrowException_WhenNotFound() {
    UUID uuid = UUID.randomUUID();
    when(userRepository.findById(uuid)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userJpaRepository.findById(uuid));
  }

  @Test
  void findByUsername_ShouldReturnUserModel_WhenFound() {
    String username = "testuser";
    UserEntity userEntity = new UserEntity();
    UserModel userModel = new UserModel();

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
    when(outboundConverter.toUserModel(userEntity)).thenReturn(userModel);

    UserModel result = userJpaRepository.findByUsername(username);

    assertNotNull(result);
  }

  @Test
  void findByUsername_ShouldThrowException_WhenNotFound() {
    String username = "testuser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userJpaRepository.findByUsername(username));
  }
}
