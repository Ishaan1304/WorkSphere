package com.worksphere.WorkSphere.entity;

import com.worksphere.WorkSphere.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskComment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;
}