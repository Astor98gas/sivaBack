package com.arsansys.siva.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.arsansys.siva.model.enums.ERol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un rol de usuario.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "rols")
public class RolEntity {

    /**
     * Identificador único del rol.
     */
    @Id
    private String id;

    /**
     * Nombre del rol.
     */
    @Builder.Default
    private ERol name = ERol.USER;
}
