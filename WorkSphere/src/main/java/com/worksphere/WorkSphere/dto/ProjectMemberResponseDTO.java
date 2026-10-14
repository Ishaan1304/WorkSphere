package com.worksphere.WorkSphere.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectMemberResponseDTO {

    private Long id;

    private Long projectId;

    private String projectName;

    private Long userId;

    private String userName;

    private String userEmail;

    private String memberRole;

    private Boolean isActive;
}