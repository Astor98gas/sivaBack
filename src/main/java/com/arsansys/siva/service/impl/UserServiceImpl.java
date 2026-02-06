package com.arsansys.siva.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arsansys.siva.model.entity.UserEntity;
import com.arsansys.siva.model.entity.jwt.JwtResponse;
import com.arsansys.siva.repository.mongo.UserRepository;
import com.arsansys.siva.security.jwt.JwtUtils;
import com.arsansys.siva.service.UserService;


/**
 * Implementación del servicio para la gestión de usuarios.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Obtiene todos los usuarios.
     * 
     * @return Lista de entidades de usuario.
     */
    @Override
    public List<UserEntity> getUsers() {
        userRepository.findAll();
        return userRepository.findAll();
    }

    /**
     * Crea un nuevo usuario y retorna el token JWT.
     * 
     * @param userEntity Entidad de usuario a crear.
     * @return Respuesta con el token JWT y el ID del usuario.
     */
    public JwtResponse createUser(UserEntity userEntity) {
        try {
            // Check if the user already exists
            if (userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
                throw new RuntimeException("User already exists");
            }
            // Hash the password before saving
            String hashedPassword = passwordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(hashedPassword);
            userRepository.save(userEntity);

            // Generate JWT token
            String token = jwtUtils.generateAccesToken(userEntity.getUsername());

            // Authenticate user in the security context
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userEntity.getUsername());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userEntity.getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Return token in response
            return new JwtResponse(token, userEntity.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    /**
     * Obtiene un usuario por su identificador.
     * 
     * @param id Identificador del usuario.
     * @return Entidad de usuario encontrada o null si no existe.
     */
    @Override
    public UserEntity getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario.
     * @return Entidad de usuario encontrada o null si no existe.
     */
    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Obtiene un usuario por su correo electrónico.
     * 
     * @param email Correo electrónico.
     * @return Entidad de usuario encontrada o null si no existe.
     */
    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Actualiza un usuario existente.
     * 
     * @param userEntity Entidad de usuario a actualizar.
     */
    @Override
    public void updateUser(UserEntity userEntity) {
        try {
            // Check if the user exists
            if (!userRepository.existsById(userEntity.getId())) {
                throw new RuntimeException("User not found");
            }

            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

}
