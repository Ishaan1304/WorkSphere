package com.worksphere.WorkSphere.dto;

import lombok.Data;

@Data
public class AddTaskCommentRequestDTO {

    private Long userId;

    private String comment;
}