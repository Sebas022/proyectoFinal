package com.example.proyectoFinal.Repository;

import com.example.proyectoFinal.Entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {
    Optional<StoreEntity> findById(Integer storeId);
}
