package com.example.proyectoFinal.Dto.ClothesRequest;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class ClotheRequest {
    private String clotheName;
    private String clotheType;
    private String brand;
    private String storeName;
    private String storeUrl;
    private String clotheCountry;
    private BigDecimal price;
    private String imageUrl;
    private Integer categoryId;
    private Integer storeId;
}
