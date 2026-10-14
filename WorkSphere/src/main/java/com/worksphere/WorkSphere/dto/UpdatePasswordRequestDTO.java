package com.worksphere.WorkSphere.dto;

import lombok.Data;

@Data
public class UpdatePasswordRequestDTO {

    private String oldPassword;

    private String newPassword;
}