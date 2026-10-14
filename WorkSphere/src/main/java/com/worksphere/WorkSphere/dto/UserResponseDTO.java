package com.worksphere.WorkSphere.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String profilePicture;
    private String status;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}