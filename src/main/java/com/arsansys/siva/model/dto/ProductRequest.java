package com.arsansys.siva.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    
    @NotBlank(message = "El nombre es requerido")
    private String name;
    
    private String description;
    
    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser positivo")
    private Double price;
    
    @NotNull(message = "El stock es requerido")
    @Positive(message = "El stock debe ser positivo")
    private Integer stock;
    
    @NotBlank(message = "La categoría es requerida")
    private String category;
}
