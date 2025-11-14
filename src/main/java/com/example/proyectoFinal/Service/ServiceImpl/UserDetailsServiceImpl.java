package com.example.proyectoFinal.Service.ServiceImpl;

import com.example.proyectoFinal.Entity.UserEntity;
import com.example.proyectoFinal.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userOpt = userRepository.findByEmail(username);

        if(userOpt.isEmpty()){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        UserEntity userEntity = userOpt.get();

        Set<GrantedAuthority> roles = new HashSet<>();
        SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority(userEntity.getRoleEntity().getRoleName());
        roles.add(roleUser);
        System.out.println("Usuario :" + userEntity.getEmail());
        System.out.println("Rol :" + userEntity.getRoleEntity().getRoleName());
        return new User(userEntity.getEmail(), userEntity.getPassword(), roles);
    }
}
