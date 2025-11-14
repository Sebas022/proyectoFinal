package com.example.proyectoFinal.Service;

import com.example.proyectoFinal.Dto.ClothesRequest.ClotheRequest;
import com.example.proyectoFinal.Dto.ClothesRequest.CreateClotheRequest;
import com.example.proyectoFinal.Dto.ClothesRequest.UpdateClotheRequest;
import com.example.proyectoFinal.Dto.ResponseBase;

import java.util.UUID;

public interface ClotheService {
    ResponseBase createClothe(CreateClotheRequest request);
    ResponseBase getAllClothes();
    ResponseBase updateClothe(UUID clotheId, UpdateClotheRequest request);

    // 🔹 Obtener prenda por ID
    ResponseBase getClotheById(UUID clotheId);
    ResponseBase deleteClothe(UUID clotheId);
}
