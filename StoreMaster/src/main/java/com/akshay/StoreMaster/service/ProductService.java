package com.akshay.StoreMaster.service;

import com.akshay.StoreMaster.dto.ProductRequestDTO;
import com.akshay.StoreMaster.dto.ProductResponseDTO;
import com.akshay.StoreMaster.entity.Product;
import com.akshay.StoreMaster.exception.ProductNotFoundException;
import com.akshay.StoreMaster.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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
    public List<ProductResponseDTO> addProduct(List<ProductRequestDTO> dtoList) {
        List<ProductResponseDTO> listOfProductsToAdd = new ArrayList<>();

        for (ProductRequestDTO productRequestDTO : dtoList) {
            if (productRequestDTO.getName() == null || productRequestDTO.getName().isBlank()) {
                throw new IllegalArgumentException("Name cannot blank");
            } else if (productRequestDTO.getPrice() == null || productRequestDTO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Price should be positive");
            } else if (productRequestDTO.getStock_quantity() < 0) {
                throw new IllegalArgumentException("Quantity should be positive");
            }
                Product product = new Product();
                product.setName(productRequestDTO.getName());
                product.setPrice(productRequestDTO.getPrice());
                product.setDescription(productRequestDTO.getDescription());
                product.setStockQuantity(productRequestDTO.getStock_quantity());
                product.setCategory(productRequestDTO.getCategory());
                product.setCreatedAt(LocalDateTime.now());
                Product savedProduct = productRepository.save(product);

                ProductResponseDTO responseDTO = new ProductResponseDTO();
                responseDTO.setProduct_ID(savedProduct.getId());
                responseDTO.setName(savedProduct.getName());
                responseDTO.setPrice(savedProduct.getPrice());
                responseDTO.setDescription(savedProduct.getDescription());
                responseDTO.setCategory(savedProduct.getCategory());
                responseDTO.setStock_quantity(savedProduct.getStockQuantity());
                listOfProductsToAdd.add(responseDTO);
        }
        return listOfProductsToAdd;
    }

    //This method updates an existing product’s details after validating input, saving changes to the database, and returning updated product information
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Optional<Product> checkProduct = productRepository.findById(productId);
        if (checkProduct.isEmpty()) {
            throw new ProductNotFoundException(String.format("Product Not found: ID=%d" , productId));
        }

        Product product = checkProduct.get();

        if (productRequestDTO.getName() != null || !productRequestDTO.getName().isBlank()) {
            product.setName(productRequestDTO.getName());
        }
        if (productRequestDTO.getPrice() == null && productRequestDTO.getPrice().compareTo(BigDecimal.ZERO) >= 0) {
            product.setPrice(productRequestDTO.getPrice());
        }
        if (productRequestDTO.getDescription() != null) {
            product.setDescription(productRequestDTO.getDescription());
        }
        if (productRequestDTO.getStock_quantity() >= 0) {
            product.setStockQuantity(productRequestDTO.getStock_quantity());
        }
        if (productRequestDTO.getCategory() != null) {
            product.setCategory(productRequestDTO.getCategory());
        }
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setProduct_ID(savedProduct.getId());
        responseDTO.setName(savedProduct.getName());
        responseDTO.setPrice(savedProduct.getPrice());
        responseDTO.setDescription(savedProduct.getDescription());
        responseDTO.setCategory(savedProduct.getCategory());
        responseDTO.setStock_quantity(savedProduct.getStockQuantity());
        return responseDTO;
    }

    public void deleteProduct(Long productId){
        Optional<Product> checkProduct = productRepository.findById(productId);
        if (checkProduct.isEmpty()){
            throw new ProductNotFoundException(
                    String.format("Product Not found: ID=%d" , productId));
        }
        checkProduct.ifPresent(productRepository::delete);
    }

    public List<ProductResponseDTO> getAllProducts(){
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream().map(product -> {

            ProductResponseDTO responseDTO = new ProductResponseDTO();

            responseDTO.setProduct_ID(product.getId());
            responseDTO.setPrice(product.getPrice());
            responseDTO.setStock_quantity(product.getStockQuantity());
            responseDTO.setName(product.getName());
            responseDTO.setDescription(product.getDescription());
            responseDTO.setStock_quantity(product.getStockQuantity());
            responseDTO.setCategory(product.getCategory());
            return responseDTO;
        }).collect(Collectors.toList());
    }

    public ProductResponseDTO getProduct(Long productId){
       Optional<Product> product = productRepository.findById(productId);

       if (product.isEmpty()){
           throw new ProductNotFoundException(
                   String.format("Product Not found: ID=%d" , productId));
       }

       Product foundProduct = product.get();
       ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setProduct_ID(foundProduct.getId());
        responseDTO.setName(foundProduct.getName());
        responseDTO.setPrice(foundProduct.getPrice());
        responseDTO.setDescription(foundProduct.getDescription());
        responseDTO.setCategory(foundProduct.getCategory());
        responseDTO.setStock_quantity(foundProduct.getStockQuantity());
        return responseDTO;
    }
}
