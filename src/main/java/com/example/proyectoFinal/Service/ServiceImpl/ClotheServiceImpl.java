package com.example.proyectoFinal.Service.ServiceImpl;

import com.example.proyectoFinal.Dto.ClothesRequest.CreateClotheRequest;
import com.example.proyectoFinal.Dto.ResponseBase;
import com.example.proyectoFinal.Dto.ClothesRequest.UpdateClotheRequest;
import com.example.proyectoFinal.Entity.CategoryEntity;
import com.example.proyectoFinal.Entity.ClotheEntity;
import com.example.proyectoFinal.Entity.StoreEntity;
import com.example.proyectoFinal.Repository.CategoryRepository;
import com.example.proyectoFinal.Repository.ClotheRepository;
import com.example.proyectoFinal.Repository.StoreRepository;
import com.example.proyectoFinal.Service.ClotheService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClotheServiceImpl implements ClotheService {

    private final ClotheRepository clotheRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    // 🔹 Crear nueva prenda
    @Override
    public ResponseBase createClothe(CreateClotheRequest request) {
        try {

            CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            StoreEntity store = storeRepository.findById(request.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));


            // Validar que no exista una prenda igual
            Optional<ClotheEntity> existingClothe = clotheRepository.findByClotheNameAndStoreId(
                    request.getClotheName(), store);

            if (existingClothe.isPresent()) {
                return new ResponseBase(409,
                        "Ya existe una prenda con ese nombre en la misma tienda",
                        Optional.empty());
            }

            ClotheEntity clothe = new ClotheEntity();
            clothe.setClotheName(request.getClotheName());
            clothe.setClotheType(request.getClotheType());
            clothe.setBrand(request.getBrand());
            clothe.setClotheCountry(request.getClotheCountry());
            clothe.setPrice(request.getPrice());
            clothe.setImageUrl(request.getImageUrl());
            clothe.setCategoryId(category);
            clothe.setStoreId(store);

            clotheRepository.save(clothe);

            return new ResponseBase(201, "Prenda creada correctamente", Optional.of(clothe));
        } catch (Exception e) {
            return new ResponseBase(500, "Error al crear la prenda: " + e.getMessage(), Optional.empty());
        }
    }

    // 🔹 Listar todas las prendas
    @Override
    public ResponseBase getAllClothes() {
        try {
            List<ClotheEntity> clothes = clotheRepository.findAll();

            if (clothes.isEmpty()) {
                return new ResponseBase(204, "No hay prendas registradas", Optional.empty());
            }

            return new ResponseBase(200, "Lista de prendas obtenida correctamente", Optional.of(clothes));
        } catch (Exception e) {
            return new ResponseBase(500, "Error al obtener las prendas: " + e.getMessage(), Optional.empty());
        }
    }

    // 🔹 Obtener prenda por ID
    @Override
    public ResponseBase getClotheById(UUID clotheId) {
        try {
            Optional<ClotheEntity> clothe = clotheRepository.findById(clotheId);

            if (clothe.isEmpty()) {
                return new ResponseBase(404, "Prenda no encontrada", Optional.empty());
            }

            return new ResponseBase(200, "Prenda encontrada correctamente", Optional.of(clothe));
        } catch (Exception e) {
            return new ResponseBase(500, "Error al buscar la prenda: " + e.getMessage(), Optional.empty());
        }
    }

    // 🔹 Actualizar prenda
    @Override
    public ResponseBase updateClothe(UUID clotheId, UpdateClotheRequest request) {
        try {
            ClotheEntity clothe = clotheRepository.findById(clotheId)
                    .orElseThrow(() -> new RuntimeException("Prenda no encontrada"));

            // Buscar categoría y tienda si se envían
            CategoryEntity category = null;
            if (request.getCategoryId() != null) {
                category = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            }

            StoreEntity store = null;
            if (request.getStoreId() != null) {
                store = storeRepository.findById(request.getStoreId())
                        .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));
            }

            // Validar duplicado si cambia nombre o tienda
            String newName = request.getClotheName() != null ? request.getClotheName() : clothe.getClotheName();
            StoreEntity newStore = (store != null ? store : clothe.getStoreId());

            Optional<ClotheEntity> existingClothe  = clotheRepository
                    .findByClotheNameAndStoreId(newName, newStore);

            if (existingClothe.isPresent() && !existingClothe.get().getClotheId().equals(clotheId)) {
                return new ResponseBase(409,
                        "Ya existe otra prenda con ese nombre en la misma tienda",
                        Optional.empty());
            }

            // Actualizar campos
            if (request.getClotheName() != null) clothe.setClotheName(request.getClotheName());
            if (request.getClotheType() != null) clothe.setClotheType(request.getClotheType());
            if (request.getBrand() != null) clothe.setBrand(request.getBrand());
            if (request.getClotheCountry() != null) clothe.setClotheCountry(request.getClotheCountry());
            if (request.getPrice() != null) clothe.setPrice(request.getPrice());
            if (request.getImageUrl() != null) clothe.setImageUrl(request.getImageUrl());
            if (category != null) clothe.setCategoryId(category);
            if (store != null) clothe.setStoreId(store);

            clotheRepository.save(clothe);
            return new ResponseBase(200, "Prenda actualizada correctamente", Optional.of(clothe));

        } catch (Exception e) {
            return new ResponseBase(500, "Error al actualizar la prenda: " + e.getMessage(), Optional.empty());
        }
    }


    // 🔹 Eliminar prenda
    @Override
    public ResponseBase deleteClothe(UUID clotheId) {
        try {
            Optional<ClotheEntity> clothe = clotheRepository.findById(clotheId);

            if (clothe.isEmpty()) {
                return new ResponseBase(404, "Prenda no encontrada", Optional.empty());
            }

            clotheRepository.delete(clothe.get());
            return new ResponseBase(200, "Prenda eliminada correctamente", Optional.empty());
        } catch (Exception e) {
            return new ResponseBase(500, "Error al eliminar la prenda: " + e.getMessage(), Optional.empty());
        }
    }
}
