package com.example.proyectoFinal.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ResponseBase {

    private int codigo;
    private String mensaje;
    private Optional<Object> data;

    public ResponseBase(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.data = null;
    }


}
