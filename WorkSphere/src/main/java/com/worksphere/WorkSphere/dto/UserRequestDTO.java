package com.worksphere.WorkSphere.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private String bio;
    private String profilePicture;
    private String status;
}
