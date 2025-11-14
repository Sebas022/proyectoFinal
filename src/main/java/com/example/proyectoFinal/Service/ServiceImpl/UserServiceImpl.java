package com.example.proyectoFinal.Service.ServiceImpl;


import com.example.proyectoFinal.Dto.ResponseBase;
import com.example.proyectoFinal.Dto.UserRequest.LoginRequest;
import com.example.proyectoFinal.Dto.UserRequest.RegisterRequest;
import com.example.proyectoFinal.Dto.UserRequest.UpdatePasswordRequest;
import com.example.proyectoFinal.Dto.UserRequest.UpdateUserRequest;
import com.example.proyectoFinal.Entity.RoleEntity;
import com.example.proyectoFinal.Entity.UserEntity;
import com.example.proyectoFinal.Repository.RoleRepository;
import com.example.proyectoFinal.Repository.UserRepository;
import com.example.proyectoFinal.Service.JwtService;
import com.example.proyectoFinal.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseBase registerUser(RegisterRequest registerRequest) {
        try {
            String email = registerRequest.getEmail();

            RoleEntity roleEntity = roleRepository.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(registerRequest.getEmail());
            userEntity.setUserName(registerRequest.getUserName());
            userEntity.setPassword(new BCryptPasswordEncoder().encode(registerRequest.getPassword()));
            userEntity.setUserCountry("PERÚ");
            userEntity.setRoleEntity(roleEntity);

            userRepository.save(userEntity);

            return new ResponseBase(200,"Usuario registrado correctamente",Optional.empty());
        } catch (Exception e) {
            return new ResponseBase(500, "Error al registrar el usuario", Optional.empty());
        }
    }

    @Override
    public ResponseBase loginUser(LoginRequest loginRequest) {
        try{

            System.out.println("=== INICIO LOGIN ===");
            System.out.println("Email del request: '" + loginRequest.getEmail() + "'");
            System.out.println("Password del request: '" + loginRequest.getPassword() + "'");

            System.out.println("=== INTENTANDO AUTENTICAR ===");
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));
            System.out.println("=== AUTENTICACIÓN EXITOSA ===");
            if (auth.isAuthenticated()){
                UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
                String token = jwtService.generateToken(user);
                return new ResponseBase(200,"Login exitoso",Optional.of(token));
            }
            return new ResponseBase(401,"Credenciales incorrectas",Optional.empty());

        } catch (Exception e) {
            return new ResponseBase(500, "Error interno del servidor", Optional.empty());
        }
    }

    @Override
    public ResponseBase updateUser(String email, UpdateUserRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar email único si se intenta cambiar
        if (request.getEmail() != null && !request.getEmail().equals(email)) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email ya está en uso por otro usuario");
            }
            user.setEmail(request.getEmail());
        }

        //actualizar email si se envia
        if (request.getUserName() != null) {
            user.setUserName(request.getUserName());
        }

        //actualizar imagen si se envia
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            user.setProfileImage(request.getProfileImage());
        }

        userRepository.save(user);
        return new ResponseBase(200,"Datos actualizados correctamente",Optional.empty());
    }

    @Override
    public ResponseBase updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        try {
            UserEntity userEntity = userRepository.findByEmail(updatePasswordRequest.getEmail())
                    .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if(!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), userEntity.getPassword())){
                return new ResponseBase(401, "La contraseña actual es incorrecta", Optional.empty());
            }

            if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), userEntity.getPassword())) {
                return new ResponseBase(400, "La nueva contraseña no puede ser igual a la actual", Optional.empty());
            }

            userEntity.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

            userRepository.save(userEntity);

            return new ResponseBase(200, "Contraseña actualizada correctamente", Optional.empty());

        } catch (Exception e) {
            return new ResponseBase(500, "Error al actualizar la contraseña: " + e.getMessage(), Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteUser(UUID userId) {
        try{
            Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
            if(optionalUserEntity.isEmpty()){
                return new ResponseBase(404, "Usuario no encontrado",Optional.empty());
            }
            userRepository.deleteById(userId);
            return new ResponseBase(200,"Usuario eliminado correctamente",Optional.empty());
        } catch (Exception e) {
            return new ResponseBase(500, "Error interno del servidor", Optional.empty());
        }
    }
}
