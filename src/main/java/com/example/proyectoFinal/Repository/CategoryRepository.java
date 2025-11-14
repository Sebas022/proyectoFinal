package com.example.proyectoFinal.Repository;


import com.example.proyectoFinal.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findById(Integer categoryId);
}
