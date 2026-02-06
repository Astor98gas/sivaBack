package com.arsansys.siva.security.jwt;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.arsansys.siva.model.entity.JwtEntity;
import com.arsansys.siva.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * Utilidades para la gestión de tokens JWT.
 * <p>
 * Permite generar, validar, extraer información y anular tokens JWT.
 */
@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    @Autowired
    private JwtService jwtService;

    /**
     * Genera un token de acceso JWT para el usuario especificado.
     *
     * @param username Nombre de usuario.
     * @return Token JWT generado.
     */
    // Generar token acceso
    public String generateAccesToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey())
                .compact();
    }

    /**
     * Valida si un token JWT es válido.
     *
     * @param token Token JWT a validar.
     * @return true si el token es válido, false en caso contrario.
     */
    // Validar token acceso
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignatureKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            if (jwtService.findByToken(token) != null && !jwtService.findByToken(token).getIsValid()) {
                log.error("Token invalido1: ".concat(token));
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("Token invalido2: ".concat(e.getMessage()));
            return false;
        }
    }

    /**
     * Obtiene el nombre de usuario a partir de un token JWT.
     *
     * @param token Token JWT.
     * @return Nombre de usuario extraído del token.
     */
    // Obtener username del token
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Obtiene un claim (información) específico del token JWT.
     *
     * @param token           Token JWT.
     * @param claimsTFunction Función para extraer el claim.
     * @param <T>             Tipo de dato del claim.
     * @return Valor del claim extraído.
     */
    // Obtener un claim(informacion) de token
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    /**
     * Extrae todos los claims (información) del token JWT.
     *
     * @param token Token JWT.
     * @return Claims extraídos del token.
     */
    // Obtener todos los claims(informacion) token
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignatureKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("Error extracting claims from token: " + e.getMessage());
            throw new RuntimeException("Invalid token", e);
        }
    }

    /**
     * Obtiene la clave secreta para firmar y verificar tokens JWT.
     *
     * @return Clave secreta.
     */
    // Obtener firma token
    public SecretKey getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Invalida un token JWT, marcándolo como no válido en la base de datos.
     *
     * @param jwtTokenString Token JWT a invalidar.
     */
    public void invalidateToken(String jwtTokenString) {
        try {
            JwtEntity jwtToken = new JwtEntity(); // Create a new instance instead of null
            jwtToken.setToken(jwtTokenString);
            jwtToken.setIsValid(false);
            Date expirationDate = getExpirationDateFromToken(jwtTokenString);
            jwtToken.setExpirationDate(expirationDate);
            jwtToken.setUsername(getUsernameFromToken(jwtTokenString));

            jwtService.save(jwtToken);
        } catch (Exception e) {
            log.error("Error al invalidar el token: " + e.getMessage());
        }
    }

    private Date getExpirationDateFromToken(String jwtTokenString) {
        Claims claims = extractAllClaims(jwtTokenString);
        try {
            return claims.getExpiration();
        } catch (Exception e) {
            log.error("Error al obtener la fecha de expiración del token: " + e.getMessage());
            return null;
        }
    }

}
