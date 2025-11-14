package com.example.proyectoFinal.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stores")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_url")
    private String storeUrl;

    @Column(name = "country")
    private String country;
}

