package com.example.proyectoFinal.Security;

import com.example.proyectoFinal.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String email = jwtService.extractEmail(jwt);

        if(Objects.nonNull(email) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if(jwtService.validateToken(jwt, userDetails)){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),null,userDetails.getAuthorities()
            );
            securityContext.setAuthentication(auth);
            SecurityContextHolder.setContext(securityContext);
            System.out.println("Token extraído: " + email);
            System.out.println("Auth: " + SecurityContextHolder.getContext().getAuthentication());
             }
        }
        filterChain.doFilter(request , response);
    }
}
