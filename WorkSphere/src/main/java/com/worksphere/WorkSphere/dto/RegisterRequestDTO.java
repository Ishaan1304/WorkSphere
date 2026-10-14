package com.worksphere.WorkSphere.dto;

import com.worksphere.WorkSphere.enums.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String name;

    private String email;

    private String password;

    private Role role;
}