package com.worksphere.WorkSphere.dto;

import com.worksphere.WorkSphere.enums.ProjectStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProjectResponseDTO {

    private Long id;

    private String name;

    private String description;

    private ProjectStatus status;

    private LocalDate startDate;

    private LocalDate endDate;
}