package com.worksphere.WorkSphere.repository;

import com.worksphere.WorkSphere.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository
        extends JpaRepository<TaskComment, Long> {

    List<TaskComment> findByTaskId(Long taskId);
}