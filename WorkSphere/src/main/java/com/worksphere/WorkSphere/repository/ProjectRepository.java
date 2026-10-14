package com.worksphere.WorkSphere.repository;

import com.worksphere.WorkSphere.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository
        extends JpaRepository<Project, Long> {
}