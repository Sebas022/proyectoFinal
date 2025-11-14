package com.example.proyectoFinal.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;
    @Column(name = "user_name")
    private String userName;
    private String email;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_country")
    private String userCountry;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
}
