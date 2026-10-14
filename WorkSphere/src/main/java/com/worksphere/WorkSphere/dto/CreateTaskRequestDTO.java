package com.worksphere.WorkSphere.dto;

import com.worksphere.WorkSphere.enums.TaskPriority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequestDTO {

    private Long projectId;

    private Long assignedToId;

    private String title;

    private String description;

    private TaskPriority priority;

    private LocalDate dueDate;
}