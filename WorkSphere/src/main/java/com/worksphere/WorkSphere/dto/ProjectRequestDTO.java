package com.worksphere.WorkSphere.dto;

import com.worksphere.WorkSphere.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectRequestDTO {

    private String name;

    private String description;

    private ProjectStatus status;

    private LocalDate startDate;

    private LocalDate endDate;
}