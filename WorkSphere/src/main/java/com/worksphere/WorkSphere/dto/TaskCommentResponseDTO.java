package com.worksphere.WorkSphere.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskCommentResponseDTO {

    private Long id;

    private Long taskId;

    private Long userId;

    private String userName;

    private String comment;

    private LocalDateTime createdAt;
}