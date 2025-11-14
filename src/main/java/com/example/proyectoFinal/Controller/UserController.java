package com.example.proyectoFinal.Controller;

import com.example.proyectoFinal.Dto.UserRequest.LoginRequest;
import com.example.proyectoFinal.Dto.UserRequest.RegisterRequest;
import com.example.proyectoFinal.Dto.ResponseBase;
import com.example.proyectoFinal.Dto.UserRequest.UpdatePasswordRequest;
import com.example.proyectoFinal.Dto.UserRequest.UpdateUserRequest;
import com.example.proyectoFinal.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "User Controller", description =
        "Controlador usado para manejar el login, create, update y delete de usuarios")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseBase> register(@RequestBody RegisterRequest request){
        ResponseBase response = userService.registerUser(request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBase> login(@RequestBody LoginRequest request){
        ResponseBase response = userService.loginUser(request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseBase> update(@PathVariable String email, UpdateUserRequest request){
        ResponseBase response = userService.updateUser(email, request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @PutMapping("/update-password")
    public ResponseEntity<ResponseBase> updatePassword(@RequestBody UpdatePasswordRequest request) {
        ResponseBase response = userService.updatePassword(request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseBase> delete(@PathVariable UUID userId){
        ResponseBase response = userService.deleteUser(userId);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }



}
