package com.example.proyectoFinal.Service;

import com.example.proyectoFinal.Dto.UserRequest.LoginRequest;
import com.example.proyectoFinal.Dto.UserRequest.RegisterRequest;
import com.example.proyectoFinal.Dto.ResponseBase;
import com.example.proyectoFinal.Dto.UserRequest.UpdatePasswordRequest;
import com.example.proyectoFinal.Dto.UserRequest.UpdateUserRequest;

import java.util.UUID;


public interface UserService {

    ResponseBase registerUser(RegisterRequest registerRequest);
    ResponseBase loginUser(LoginRequest loginRequest);
    ResponseBase updateUser(String email, UpdateUserRequest updateRequest);
    ResponseBase updatePassword(UpdatePasswordRequest updatePasswordRequest);
    ResponseBase deleteUser(UUID userId);
}
