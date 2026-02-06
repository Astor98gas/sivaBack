package com.arsansys.siva.service;

import org.springframework.stereotype.Service;

import com.arsansys.siva.model.entity.JwtEntity;

@Service
public interface JwtService {

    /**
     * Busca un token JWT por nombre de usuario.
     * 
     * @param username Nombre de usuario.
     * @return Entidad JWT asociada al usuario o null si no existe.
     */
    JwtEntity findByUsername(String username);

    /**
     * Busca un token JWT por el valor del token.
     * 
     * @param token Valor del token JWT.
     * @return Entidad JWT encontrada o null si no existe.
     */
    JwtEntity findByToken(String token);

    /**
     * Guarda una entidad JWT en la base de datos.
     * 
     * @param jwtToken Entidad JWT a guardar.
     */
    void save(JwtEntity jwtToken);

}
