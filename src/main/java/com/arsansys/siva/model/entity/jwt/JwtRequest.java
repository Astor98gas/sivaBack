package com.arsansys.siva.model.entity.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Solicitud de autenticación JWT.
 * Contiene las credenciales del usuario o el token de Google.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    /**
     * Nombre de usuario.
     */
    String username;
    /**
     * Contraseña del usuario.
     */
    String password;
    /**
     * Token de autenticación de Google.
     */
    String googleToken;

}
