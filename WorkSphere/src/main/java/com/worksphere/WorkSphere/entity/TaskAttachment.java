package com.worksphere.WorkSphere.entity;

import com.worksphere.WorkSphere.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskAttachment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    private String fileName;

    private String filePath;

    private Long fileSize;

    private String contentType;
}