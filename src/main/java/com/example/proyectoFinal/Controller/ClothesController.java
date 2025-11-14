package com.example.proyectoFinal.Controller;

import com.example.proyectoFinal.Dto.ClothesRequest.ClotheRequest;
import com.example.proyectoFinal.Dto.ClothesRequest.CreateClotheRequest;
import com.example.proyectoFinal.Dto.ClothesRequest.UpdateClotheRequest;
import com.example.proyectoFinal.Dto.ResponseBase;
import com.example.proyectoFinal.Service.ClotheService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clothes")
@AllArgsConstructor
public class ClothesController {

    private final ClotheService clotheService;

    @PostMapping("/create")
    public ResponseBase create(@RequestBody CreateClotheRequest request) {
        return clotheService.createClothe(request);
    }

    @GetMapping("/list")
    public ResponseBase getAll() {
        return clotheService.getAllClothes();
    }

    @PutMapping("/{id}")
    public ResponseBase update(@PathVariable UUID id, @RequestBody UpdateClotheRequest request) {
        return clotheService.updateClothe(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseBase delete(@PathVariable UUID id) {
        return clotheService.deleteClothe(id);
    }
}

