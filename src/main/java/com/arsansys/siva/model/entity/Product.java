package com.arsansys.siva.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    
    @Id
    private String id;
    
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
