package com.example.proyectoFinal.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "clothes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClotheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clothe_id")
    private UUID clotheId;
    @Column(name = "clothe_name")
    private String clotheName;
    @Column(name = "clothe_type")
    private String clotheType;
    private String brand;
    @Column(name = "clothe_country")
    private String clotheCountry;
    private BigDecimal price;
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity storeId;

}
