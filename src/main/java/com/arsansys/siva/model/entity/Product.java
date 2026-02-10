package com.arsansys.siva.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Entidad que representa un Producto en la base de datos de MongoDB.
 * 
 * Esta clase mapea la colección 'products' en la base de datos MongoDB
 * y contiene la información básica de un producto disponible en el catálogo.
 * 
 * Anotaciones:
 * - @Document: Mapea esta clase a la colección 'products' en MongoDB
 * - @Data: Genera automáticamente getters, setters, equals, hashCode y toString
 * - @Builder: Proporciona un patrón builder para crear instancias
 * - @NoArgsConstructor: Genera un constructor sin argumentos
 * - @AllArgsConstructor: Genera un constructor con todos los argumentos
 * 
 * @author Sistema SIVA
 * @version 1.0
 * @since 2026
 */
@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    /**
     * Identificador único del producto.
     * Generado automáticamente por MongoDB.
     */
    @Id
    private String id;

    /**
     * Nombre del producto.
     * Campo obligatorio que describe el nombre o título del producto.
     */
    private String name;

    /**
     * Descripción detallada del producto.
     * Contiene información adicional sobre las características del producto.
     */
    private String description;

    /**
     * Precio del producto en unidad monetaria.
     * Valor decimal que representa el costo del producto.
     */
    private Double price;

    /**
     * Cantidad disponible en inventario.
     * Cantidad de unidades del producto disponibles para venta.
     */
    private Integer stock;

    /**
     * Categoría a la que pertenece el producto.
     * Clasificación utilizada para organizar y filtrar productos.
     */
    private String category;

    /**
     * Fecha y hora de creación del registro del producto.
     * Se establece automáticamente cuando se crea el producto.
     */
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de la última actualización del producto.
     * Se actualiza automáticamente cada vez que se modifica el producto.
     */
    private LocalDateTime updatedAt;
}
