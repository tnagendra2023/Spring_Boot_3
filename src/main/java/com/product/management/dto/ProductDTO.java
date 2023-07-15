package com.product.management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.product.management.validation.ValidateProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @Schema(hidden = true)
    private Long productId;

    @Schema(example = "phone")
    private String productName;

    @Schema(example = "5000")
    private double price;

    @Schema(example = "active")
    @ValidateProductStatus
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(hidden = true)
    private Date date=new Date(System.currentTimeMillis());
}
