package com.example.proyectoFinal.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface JwtService {

    String generateToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
    String extractEmail(String token);
}
