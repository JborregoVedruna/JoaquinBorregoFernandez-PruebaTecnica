package com.caixabank.loansmanager.infrastructure.adapters.outbound.repositories;

import com.caixabank.loansmanager.infrastructure.adapters.outbound.entities.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio de Spring Data JPA para la entidad {@link UserEntity}. */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  /**
   * Busca un usuario por su nombre de usuario.
   *
   * @param username Nombre de usuario a buscar.
   * @return Un {@link Optional} con el usuario encontrado o vacío.
   */
  Optional<UserEntity> findByUsername(String username);
}
