package com.akshay.StoreMaster.service;

import com.akshay.StoreMaster.dto.ProductRequestDTO;
import com.akshay.StoreMaster.dto.ProductResponseDTO;
import com.akshay.StoreMaster.entity.Product;
import com.akshay.StoreMaster.exception.ProductNotFoundException;
import com.akshay.StoreMaster.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //This method validates product input, saves the product to the database, and returns the saved product details as a response DTO
    @Transactional
    public List<ProductResponseDTO> addProduct(List<ProductRequestDTO> dtoList) {
        dtoList.forEach(this::validateForCreate);

        List<Product> productsToSave = dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        List<Product> savedProducts = productRepository.saveAll(productsToSave);

        log.info("Created {} products successfully", savedProducts.size());
        return savedProducts.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    //This method updates an existing product’s details after validating input, saving changes to the database, and returning updated product information
    @Transactional
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO dto) {
        Product product = getProductOrThrow(productId);

        if (dto.getName() != null && !dto.getName().isBlank()) {
            product.setName(dto.getName());
        }
        if (dto.getPrice() != null && dto.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getStock_quantity() >= 0) {
            product.setStockQuantity(dto.getStock_quantity());
        }
        if (dto.getCategory() != null) {
            product.setCategory(dto.getCategory());
        }

        Product saved = productRepository.save(product);
        log.info("Updated product ID={}", productId);
        return toResponseDTO(saved);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = getProductOrThrow(productId);
        productRepository.delete(product);
        log.info("Deleted product ID={}", productId);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO getProduct(Long productId){
        return toResponseDTO(getProductOrThrow(productId));
    }

    private Product getProductOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format("Product Not Found: ID=%d", productId)));
    }

    private void validateForCreate(ProductRequestDTO dtc) {
        if (dtc.getName() == null || dtc.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (dtc.getPrice() == null || dtc.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price should be positive");
        }
        if (dtc.getStock_quantity() < 0) {
            throw new IllegalArgumentException("Quantity should be positive");
        }
    }

    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStock_quantity());
        product.setCategory(dto.getCategory());
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProduct_ID(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setStock_quantity(product.getStockQuantity());
        return dto;
    }
}
