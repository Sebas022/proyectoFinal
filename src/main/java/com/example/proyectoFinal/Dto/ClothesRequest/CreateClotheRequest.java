package com.example.proyectoFinal.Dto.ClothesRequest;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class CreateClotheRequest {
    private String clotheName;      // Nombre de la prenda
    private String clotheType;      // Tipo de prenda (camisa, pantalón, etc.)
    private String brand;           // Marca
    private String clotheCountry;   // País de origen
    private BigDecimal price;       // Precio
    private String imageUrl;        // Imagen de la prenda
    private Integer categoryId;     // FK a categories
    private Integer storeId;        // FK a stores
}
