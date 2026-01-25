package com.caixabank.loansmanager.infrastructure.adapters.outbound;

import com.caixabank.loansmanager.domain.exceptions.UserNotFoundException;
import com.caixabank.loansmanager.domain.model.PageModel;
import com.caixabank.loansmanager.domain.model.PageableModel;
import com.caixabank.loansmanager.domain.model.UserModel;
import com.caixabank.loansmanager.domain.ports.out.UserJpaRepository;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.converters.OutboundConverter;
import com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories.UserRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

/**
 * Implementación del puerto {@link UserJpaRepository} encargada del acceso y gestión de
 * persistencia de usuarios.
 *
 * <p>Utiliza mecanismos de caché de Spring para optimizar las consultas frecuentes.
 */
@Slf4j
@Component
@AllArgsConstructor
public class UserJpaRepositoryImpl implements UserJpaRepository {

  /** Conversor para transformar entre entidades JPA y modelos de dominio. */
  private final OutboundConverter outboundConverter;

  /** Repositorio Spring Data JPA para la entidad de usuario. */
  private final UserRepository userRepository;

  /**
   * {@inheritDoc}
   *
   * <p>Persiste el usuario y actualiza la caché.
   */
  @Override
  @Caching(
      put = @CachePut(value = "users", key = "#result.userUuid"),
      evict = @CacheEvict(value = "usersPage", allEntries = true))
  public UserModel save(UserModel user) {
    log.info("Storing user: {}", user);
    return outboundConverter.toUserModel(userRepository.save(outboundConverter.toUserEntity(user)));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Recupera una página de usuarios desde la base de datos o la caché.
   */
  @Override
  @Cacheable(value = "usersPage", key = "#pageableModel.page + '-' + #pageableModel.size")
  public PageModel<UserModel> findAll(PageableModel pageableModel) {
    log.info("Retrieving all users");
    return outboundConverter.toUserModelPage(
        userRepository.findAll(outboundConverter.toPageable(pageableModel)));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Busca un usuario por su UUID, lanzando excepción si no se encuentra.
   */
  @Override
  @Cacheable(value = "users", key = "#userUuid")
  public UserModel findById(UUID userUuid) {
    log.info("Retrieving user with id: {}", userUuid);
    return outboundConverter.toUserModel(
        userRepository
            .findById(userUuid)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userUuid)));
  }

  /**
   * {@inheritDoc}
   *
   * <p>Busca un usuario por su nombre único.
   */
  @Override
  public UserModel findByUsername(String username) {
    log.info("Retrieving user with username: {}", username);
    return outboundConverter.toUserModel(
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new UserNotFoundException("User not found with username: " + username)));
  }
}
