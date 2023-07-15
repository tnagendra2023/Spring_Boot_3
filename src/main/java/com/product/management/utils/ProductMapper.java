package com.product.management.utils;

import com.product.management.dto.ProductDTO;
import com.product.management.entity.Product;
import org.springframework.beans.BeanUtils;

public class ProductMapper {
    private ProductMapper(){}
    public static ProductDTO mapToProductDTO(Product product){
        ProductDTO productDTO=new ProductDTO();
        BeanUtils.copyProperties(product,productDTO);
        return productDTO;
    }

    public static Product mapToProduct(ProductDTO productDTO){
        Product product=new Product();
        BeanUtils.copyProperties(productDTO,product);
        return product;
    }
}
