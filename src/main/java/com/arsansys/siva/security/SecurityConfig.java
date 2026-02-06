package com.arsansys.siva.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.arsansys.siva.security.filters.JwtAuthenticationFilter;
import com.arsansys.siva.security.filters.JwtAutorizationFilter;
import com.arsansys.siva.security.jwt.JwtUtils;
import com.arsansys.siva.service.UserService;

import java.util.Arrays;

/**
 * Clase de configuración de seguridad.
 * <p>
 * Configura la cadena de filtros de seguridad, la gestión de autenticación,
 * la codificación de contraseñas y la configuración CORS para la aplicación.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtAutorizationFilter jwtAutorizationFilter;

    @Autowired
    @Lazy
    UserService userService;

    /**
     * Configura la cadena de filtros de seguridad.
     *
     * @param httpSecurity          Objeto HttpSecurity para la configuración HTTP.
     * @param authenticationManager Gestor de autenticación.
     * @return SecurityFilterChain Cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre algún error durante la configuración.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager)
            throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils, userService);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        jwtAuthenticationFilter.setRequiresAuthenticationRequestMatcher(request -> {
            String path = request.getServletPath();
            return "/login".equals(path) && !"/logout".equals(path);
        });

        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/logout", "/", "/api", "/createUser", "/vendedor/producto/getAll",
                            "/vendedor/producto/getById/*", "/api/binary-image/*",
                            "/api/images/*", "/resorces/static/**", "/api/stripe/**", "/api/upload/**",
                            "/api/images/lowRes/**", "/getUserByEmail/**", "/getUserByUsername/**",
                            "/admin/categoria/getById/**", "/admin/categoria/getAll")
                            .permitAll();
                    auth.anyRequest().authenticated();
                    // auth.anyRequest().permitAll();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAutorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Bean que proporciona un codificador de contraseñas BCrypt.
     *
     * @return BCryptPasswordEncoder Codificador de contraseñas BCrypt.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Bean que proporciona un gestor de autenticación.
     *
     * @param httpSecurity    Objeto HttpSecurity.
     * @param passwordEncoder Codificador de contraseñas.
     * @return AuthenticationManager Gestor de autenticación.
     * @throws Exception Si ocurre algún error durante la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Bean de configuración CORS.
     * <p>
     * Permite solicitudes desde el origen especificado y métodos HTTP definidos.
     *
     * @return CorsConfigurationSource Configuración CORS.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Bean para el proveedor de autenticación Dao.
     * <p>
     * Configura el proveedor que valida usuarios con UserDetailsService y
     * PasswordEncoder.
     *
     * @return DaoAuthenticationProvider Proveedor de autenticación configurado.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
