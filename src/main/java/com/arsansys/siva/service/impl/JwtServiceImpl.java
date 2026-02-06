package com.arsansys.siva.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arsansys.siva.model.entity.JwtEntity;
import com.arsansys.siva.repository.mongo.JwtRepository;
import com.arsansys.siva.service.JwtService;


/**
 * Implementación del servicio para la gestión de tokens JWT.
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private JwtRepository jwtRepository;

    /**
     * Busca un token JWT por nombre de usuario.
     * 
     * @param username Nombre de usuario.
     * @return Entidad JWT encontrada.
     */
    @Override
    public JwtEntity findByUsername(String username) {
        return jwtRepository.findByUsername(username);
    }

    /**
     * Busca un token JWT por el propio token.
     * 
     * @param token Token JWT.
     * @return Entidad JWT encontrada.
     */
    @Override
    public JwtEntity findByToken(String token) {
        return jwtRepository.findByToken(token);
    }

    /**
     * Guarda una entidad JWT en la base de datos.
     * 
     * @param jwtToken Entidad JWT a guardar.
     */
    @Override
    public void save(JwtEntity jwtToken) {
        jwtRepository.save(jwtToken);
    }
}
