package com.arsansys.siva.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.arsansys.siva.model.entity.UserEntity;
import com.arsansys.siva.model.entity.jwt.JwtResponse;;

@Service
public interface UserService {

    /**
     * Obtiene la lista de todos los usuarios.
     * 
     * @return Lista de entidades de usuario.
     */
    abstract List<UserEntity> getUsers();

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id ID del usuario.
     * @return Entidad de usuario encontrada o null si no existe.
     */
    abstract UserEntity getUserById(String id);

    /**
     * Obtiene un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario.
     * @return Entidad de usuario encontrada o null si no existe.
     */
    abstract UserEntity getUserByUsername(String username);

    /**
     * Crea un nuevo usuario.
     * 
     * @param userEntity Entidad de usuario a crear.
     * @return Respuesta JWT con la información del usuario creado.
     */
    abstract JwtResponse createUser(UserEntity userEntity);

    /**
     * Obtiene un usuario por su correo electrónico.
     * 
     * @param email Correo electrónico del usuario.
     * @return Entidad de usuario encontrada o null si no existe.
     */
    abstract UserEntity getByEmail(String email);

    /**
     * Actualiza los datos de un usuario existente.
     * 
     * @param userEntity Entidad de usuario con los datos actualizados.
     */
    abstract void updateUser(UserEntity userEntity);

}
