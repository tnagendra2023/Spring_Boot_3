package com.product.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.management.dto.ProductDTO;
import com.product.management.entity.Product;
import com.product.management.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc= MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void shouldAddNewProductAndExpect200SuccessCode() throws Exception {
        // Mocking behavior
        ProductDTO productDTO = getProductDTO();
        when(productService.addProduct(Mockito.any(ProductDTO.class))).thenReturn(productDTO);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("5000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("active"));
    }

    private ProductDTO getProductDTO() {
        Product product=new Product();
        product.setProductName("phone");
        product.setPrice(5000);
        product.setStatus("active");

        ProductDTO productDTO=new ProductDTO();
        BeanUtils.copyProperties(product,productDTO);
        return productDTO;
    }

    @Test
    void shouldGetProductByIdAndExpect200SuccessCode() throws Exception {
        // Mocking behavior
        Long productId=1L;
        ProductDTO mockProductDTO = getProductDTO();
        when(productService.getProductById(productId)).thenReturn(mockProductDTO);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{productId}",productId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("5000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("active"));
    }

    @Test
    void shouldGetAllProductsAndExpect200SuccessCode() throws Exception {
        // Mocking behavior
        ProductDTO productDTO = getProductDTO();
        ProductDTO productDTO1=new ProductDTO();
        productDTO1.setProductName("book");
        productDTO1.setPrice(500);
        productDTO1.setStatus("active");
        List<ProductDTO> productDTOs = List.of(productDTO,productDTO1);
        when(productService.getAllProductDetails()).thenReturn(productDTOs);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value("5000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("active"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName").value("book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value("500.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value("active"));
    }

    @Test
    void updateProductDetails() throws Exception {
        Long productId=1L;
        //Mocking behavior
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("phone");
        productDTO.setPrice(50000);
        productDTO.setStatus("active");
        when(productService.updateProductDetails(Mockito.anyLong(), Mockito.any(ProductDTO.class))).thenReturn(productDTO);

         // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("phone"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("50000.0"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("active"));
    }

    @Test
    void deleteProductDetails() throws Exception {
        // Mocking the void method deleteProduct
        doNothing().when(productService).deleteProduct(Mockito.anyLong());
        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{productId}", 1L))
                .andExpect(status().is2xxSuccessful());
    }

}