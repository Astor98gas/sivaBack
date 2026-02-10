package com.arsansys.siva.security.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.arsansys.siva.model.entity.UserEntity;
import com.arsansys.siva.security.jwt.JwtUtils;
import com.arsansys.siva.service.UserService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de autenticación JWT.
 * <p>
 * Este filtro se encarga de procesar las solicitudes de autenticación,
 * validando las credenciales del usuario y generando un token JWT en caso de
 * éxito.
 * También gestiona la integración con Google Token y añade cabeceras CORS.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtils jwtUtils;
    private UserService userService;

    /**
     * Constructor del filtro de autenticación JWT.
     *
     * @param jwtUtils    Utilidades para la gestión de JWT.
     * @param userService Servicio de usuarios para operaciones relacionadas.
     */
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    /**
     * Intenta autenticar al usuario a partir de la petición HTTP.
     * <p>
     * Lee las credenciales del usuario desde el cuerpo de la petición,
     * añade cabeceras CORS y gestiona la autenticación con Google Token si
     * corresponde.
     *
     * @param request  Petición HTTP entrante.
     * @param response Respuesta HTTP saliente.
     * @return Objeto Authentication si la autenticación es exitosa, null si es una
     *         solicitud OPTIONS.
     * @throws AuthenticationException Si ocurre un error durante la autenticación.
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // Agregar encabezados CORS manualmente
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // Si es una solicitud OPTIONS, retornar 200 OK sin autenticar
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return null;
        }

        // Continúa con la lógica existente
        UserEntity userEntity = null;
        String username = "", password = "", googleToken = "";

        try {
            userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            username = userEntity.getUsername();
            password = userEntity.getPassword();
            googleToken = userEntity.getGoogleToken();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, password);

            if (googleToken != null && !googleToken.isEmpty()) {
                UserEntity user = userService.getUserByUsername(username);
                user.setGoogleToken(googleToken);
                if (user != null) {
                    userService.updateUser(user);
                }
            }

            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Acción a realizar en caso de autenticación exitosa.
     * <p>
     * Genera un token JWT, lo añade a la respuesta y devuelve información relevante
     * del usuario.
     *
     * @param request    Petición HTTP.
     * @param response   Respuesta HTTP.
     * @param chain      Cadena de filtros.
     * @param authResult Resultado de la autenticación.
     * @throws IOException      Si ocurre un error de entrada/salida.
     * @throws ServletException Si ocurre un error en el servlet.
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.generateAccesToken(user.getUsername());

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("message", "Autenticacion Correcta");
        httpResponse.put("username", user.getUsername());
        httpResponse.put("idUser", userService.getUserByUsername(user.getUsername()).getId());

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
    }
}
