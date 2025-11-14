package com.example.proyectoFinal.Repository;

import com.example.proyectoFinal.Entity.ClotheEntity;
import com.example.proyectoFinal.Entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClotheRepository extends JpaRepository<ClotheEntity, UUID> {

    Optional<ClotheEntity> findByClotheNameAndStoreId(String clotheName, StoreEntity storeId);
}

