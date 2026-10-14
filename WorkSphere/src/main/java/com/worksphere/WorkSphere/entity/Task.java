package com.worksphere.WorkSphere.entity;

import com.worksphere.WorkSphere.common.AuditableEntity;
import java.util.*;
import com.worksphere.WorkSphere.enums.TaskPriority;
import com.worksphere.WorkSphere.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate dueDate;

    private LocalDateTime completedAt;
    
    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TaskComment> comments = new ArrayList<>();
}