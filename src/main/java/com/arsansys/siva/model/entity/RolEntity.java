package com.arsansys.siva.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.arsansys.siva.model.enums.ERol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
@Document(collection = "rols")
public class RolEntity {

    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Nombre del rol.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ERol name = ERol.COMPRADOR;
}
