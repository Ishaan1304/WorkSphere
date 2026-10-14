package com.worksphere.WorkSphere.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskAttachmentResponseDTO {

    private Long id;

    private Long taskId;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private String contentType;
}