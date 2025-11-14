package com.example.proyectoFinal.Dto.UserRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String userName;
    private String email;
    private String profileImage;
}
