package com.worksphere.WorkSphere.dto;

import com.worksphere.WorkSphere.enums.TaskPriority;
import com.worksphere.WorkSphere.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponseDTO {

    private Long id;

    private Long projectId;

    private String projectName;

    private Long assignedToId;

    private String assignedToName;

    private String title;

    private String description;

    private TaskPriority priority;

    private TaskStatus status;

    private LocalDate dueDate;

    private LocalDateTime completedAt;
}