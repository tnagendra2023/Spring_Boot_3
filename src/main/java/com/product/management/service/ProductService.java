package com.product.management.service;

import com.product.management.dto.ProductDTO;
import com.product.management.entity.Product;
import com.product.management.repository.ProductRepository;
import com.product.management.utils.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    public static final String NO_PRODUCT_DETAILS_FOUND = "No product details found for the given productId: ";
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = ProductMapper.mapToProduct(productDTO);
        Product newProduct = productRepository.save(product);
        return ProductMapper.mapToProductDTO(newProduct);
    }

    public ProductDTO getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return ProductMapper.mapToProductDTO(product);
        } else {
            throw new EntityNotFoundException(NO_PRODUCT_DETAILS_FOUND + productId);
        }
    }

    public List<ProductDTO> getAllProductDetails() {
        return productRepository.findAll().stream()
                .map(ProductMapper::mapToProductDTO)
                .toList();
    }

    public ProductDTO updateProductDetails(Long productId, ProductDTO productDTO) {
        Product existinProduct;
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                existinProduct = optionalProduct.get();
                existinProduct.setProductName(productDTO.getProductName());
                existinProduct.setDate(Date.valueOf(LocalDate.now()));
                existinProduct.setPrice(productDTO.getPrice());
                existinProduct.setStatus(productDTO.getStatus());
                productRepository.save(existinProduct);
                return ProductMapper.mapToProductDTO(existinProduct);
            } else {
                throw new EntityNotFoundException(NO_PRODUCT_DETAILS_FOUND + productId);
            }
        }


    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(productId);
        } else {
            throw new EntityNotFoundException(NO_PRODUCT_DETAILS_FOUND + productId);
        }
    }
}
