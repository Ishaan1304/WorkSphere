package com.worksphere.WorkSphere.entity;

import com.worksphere.WorkSphere.common.AuditableEntity;
import com.worksphere.WorkSphere.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AuditableEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String profilePicture;

    private String status;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private Boolean emailVerified = false;

    @Builder.Default
    private Boolean accountLocked = false;

    @Builder.Default
    private Integer failedAttempts = 0;

    @Builder.Default
    private Boolean isDeleted = false;

    private LocalDateTime lastLogin;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();
}