package com.worksphere.WorkSphere.dto;

import com.worksphere.WorkSphere.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {

    private String name;

    private String email;

    private String password;

    private String bio;

    private String profilePicture;

    private String status;

    private Set<Role> roles;
}