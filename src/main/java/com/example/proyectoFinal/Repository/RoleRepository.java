package com.example.proyectoFinal.Repository;

import com.example.proyectoFinal.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository <RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleName(String roleName);

}
