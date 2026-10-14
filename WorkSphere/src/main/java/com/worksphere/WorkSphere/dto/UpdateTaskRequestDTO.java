package com.worksphere.WorkSphere.dto;

import com.worksphere.WorkSphere.enums.TaskPriority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTaskRequestDTO {

    private String title;

    private String description;

    private TaskPriority priority;

    private LocalDate dueDate;
}