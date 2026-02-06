package com.arsansys.siva.model.entity.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Respuesta de autenticación JWT.
 * Contiene el token generado y el identificador del usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    /**
     * Token JWT generado.
     */
    String token;
    /**
     * Identificador del usuario autenticado.
     */
    String idUser;

}