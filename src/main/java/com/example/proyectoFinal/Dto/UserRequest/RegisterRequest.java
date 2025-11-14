package com.example.proyectoFinal.Dto.UserRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String email;
    private String userName;
    private String password;
}
