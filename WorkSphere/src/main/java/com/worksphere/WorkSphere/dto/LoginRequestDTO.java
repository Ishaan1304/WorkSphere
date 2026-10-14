package com.worksphere.WorkSphere.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}