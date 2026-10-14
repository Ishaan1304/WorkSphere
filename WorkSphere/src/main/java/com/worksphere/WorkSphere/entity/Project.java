package com.worksphere.WorkSphere.entity;

import com.worksphere.WorkSphere.common.AuditableEntity;
import com.worksphere.WorkSphere.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends AuditableEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectMember> members = new ArrayList<>();
}