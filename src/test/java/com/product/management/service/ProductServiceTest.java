package com.product.management.service;

import com.product.management.dto.ProductDTO;
import com.product.management.entity.Product;
import com.product.management.repository.ProductRepository;
import com.product.management.utils.ProductEnum;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product=new Product();
        product.setProductId(1L);
        product.setProductName("phone");
        product.setPrice(50000.0);
        product.setStatus(ProductEnum.ACTIVE.name());
        product.setDate(Date.valueOf(LocalDate.now()));

        productDTO=new ProductDTO();
        productDTO.setProductName("phone");
        productDTO.setPrice(50000.0);
        productDTO.setStatus(ProductEnum.ACTIVE.name());
    }

    @Test
    void shouldAddNewProduct() {
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        ProductDTO newProductDTO = productService.addProduct(productDTO);
        Assertions.assertEquals("phone",newProductDTO.getProductName());
        Assertions.assertEquals(1L,newProductDTO.getProductId());
        Assertions.assertEquals(50000.0,newProductDTO.getPrice());
        verify(productRepository, times(1)).save(Mockito.any(Product.class));

    }

    @Test
    void getProductById() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        ProductDTO productById = productService.getProductById(1L);
        Assertions.assertEquals(1L,productById.getProductId());
        Assertions.assertEquals("phone",productById.getProductName());
        Assertions.assertEquals(50000.0,productById.getPrice());
        Assertions.assertEquals(ProductEnum.ACTIVE.name(),productById.getStatus());
        verify(productRepository, times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenNoProductExistForProductId() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void getAllProductDetails() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<ProductDTO> productDTOList = productService.getAllProductDetails();
        Assertions.assertEquals(1,productDTOList.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateProductDetails() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        ProductDTO updatedProductDetails = productService.updateProductDetails(1L, productDTO);
        Assertions.assertEquals(1L,updatedProductDetails.getProductId());
        Assertions.assertEquals("phone",updatedProductDetails.getProductName());
        Assertions.assertEquals(50000.0,updatedProductDetails.getPrice());
        Assertions.assertEquals(ProductEnum.ACTIVE.name(),updatedProductDetails.getStatus());
        verify(productRepository, times(1)).findById(Mockito.anyLong());
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }


    @Test
    void shouldThrowEntityNotFoundException_ForUpdateProductDetailsWhenThereIsNoProductExist() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.updateProductDetails(1L,productDTO));
    }

    @Test
    void deleteProduct() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        // Configure the behavior of the deleteById method
        doNothing().when(productRepository).deleteById(Mockito.anyLong());
        productService.deleteProduct(1L);
        // Verify that the deleteById method was called
        verify(productRepository, times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void shouldThrowEntityNotFoundException_ForDeleteProductWhenThereIsNoProductExist() {
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(1L));
    }
}