package com.worksphere.WorkSphere.dto;

import com.worksphere.WorkSphere.enums.TaskStatus;
import lombok.Data;

@Data
public class UpdateTaskStatusRequestDTO {

    private TaskStatus status;
}