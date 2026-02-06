package com.arsansys.siva.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un usuario de la plataforma.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class UserEntity {

    /**
     * Identificador único del usuario.
     */
    @Id
    private String id;

    /**
     * Nombre de usuario único.
     */
    @Size(max = 50)
    @Indexed(unique = true)
    private String username;

    /**
     * Correo electrónico del usuario.
     */
    @NotBlank
    @Indexed(unique = true)
    private String email;

    /**
     * Contraseña cifrada del usuario.
     */
    @NotBlank
    @Size(max = 500)
    private String password;

    /**
     * Indica si el usuario está activo.
     */
    @NotBlank
    @Builder.Default
    private Boolean active = true;

    /**
     * Rol asignado al usuario.
     */
    private RolEntity rol;

    /**
     * Token de Google asociado (opcional).
     */
    private String googleToken;

    /**
     * Descripción del usuario.
     */
    @Size(max = 1000)
    private String description;

    /**
     * URL de la imagen de perfil.
     */
    private String profileImage;

}
