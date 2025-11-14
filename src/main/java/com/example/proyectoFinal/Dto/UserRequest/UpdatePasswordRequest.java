package com.example.proyectoFinal.Dto.UserRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {

    private String email;
    private String oldPassword;
    private String newPassword;
}
