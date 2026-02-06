package com.arsansys.siva.repository.mongo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.arsansys.siva.model.entity.UserEntity;

/**
 * Repositorio para la entidad UserEntity.
 * Proporciona operaciones CRUD y consultas personalizadas sobre usuarios en
 * MongoDB.
 */
@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario
     * @return Usuario si existe
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Busca un usuario por su ID.
     * 
     * @param id ID del usuario
     * @return Usuario si existe
     */
    Optional<UserEntity> findById(String id);

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param email Correo electrónico
     * @return Usuario si existe
     */
    Optional<UserEntity> findByEmail(String email);

}
