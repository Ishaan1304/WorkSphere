package com.worksphere.WorkSphere.dto;

import lombok.Data;

@Data
public class UpdateUserRequestDTO {

    private String name;

    private String bio;

    private String profilePicture;

    private String status;
}