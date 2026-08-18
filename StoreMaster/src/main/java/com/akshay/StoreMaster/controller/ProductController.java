package com.akshay.StoreMaster.controller;

import com.akshay.StoreMaster.dto.ProductRequestDTO;
import com.akshay.StoreMaster.dto.ProductResponseDTO;
import com.akshay.StoreMaster.repository.ProductRepository;
import com.akshay.StoreMaster.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/storemaster/product")
public class ProductController {

    private final ProductService productService;

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<List<ProductResponseDTO>> add(@Valid @RequestBody List<ProductRequestDTO> dtoList){
        List<ProductResponseDTO> product = productService.addProduct(dtoList);
        log.info("Product added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long productId,@Valid @RequestBody ProductRequestDTO productRequestDTO){
        ProductResponseDTO updateProduct = productService.updateProduct(productId, productRequestDTO);
        log.info("Product updated successfully. Id:{}", productId);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> delete(@PathVariable Long productId){
         productService.deleteProduct(productId);
        log.info("Product deleted Successfully ID:{}",productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllProduct")
    public List<ProductResponseDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/get/{productId}")
    public ProductResponseDTO getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }
}
