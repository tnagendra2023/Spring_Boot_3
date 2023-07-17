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

    // Since productId is auto generated ,Hence to hide it from Swagger documentation use @Schema annotation with hide property
    @Schema(hidden = true)
    private Long productId;

    // To set default value in Swagger documentation use @Schema annotation with example property
    @Schema(example = "phone")
    private String productName;

    // To set default value in Swagger documentation use @Schema annotation with example property
    @Schema(example = "5000")
    private double price;

    // To set default value in Swagger documentation use @Schema annotation with example property
    @Schema(example = "active")
    @ValidateProductStatus
    private String status;

    // To hide field from Swagger documentation use @Schema annotation with hide property
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(hidden = true)
    private Date date=new Date(System.currentTimeMillis());
}
