package com.arsansys.siva.service;

import com.arsansys.siva.model.dto.ProductDTO;
import com.arsansys.siva.model.dto.ProductRequest;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductRequest request);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductsByCategory(String category);
    ProductDTO updateProduct(String id, ProductRequest request);
    void deleteProduct(String id);
}
