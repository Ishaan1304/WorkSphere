package com.worksphere.WorkSphere.dto;

import lombok.Data;

@Data
public class AssignProjectMemberRequestDTO {

    private Long projectId;

    private Long userId;

    private String memberRole;
}