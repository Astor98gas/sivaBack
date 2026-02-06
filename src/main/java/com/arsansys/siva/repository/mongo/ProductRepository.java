package com.arsansys.siva.repository.mongo;

import com.arsansys.siva.model.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
    Optional<Product> findByName(String name);
    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
