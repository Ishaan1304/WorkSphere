package com.worksphere.WorkSphere.service;

import com.worksphere.WorkSphere.dto.AddTaskCommentRequestDTO;
import com.worksphere.WorkSphere.dto.CreateTaskRequestDTO;
import com.worksphere.WorkSphere.dto.TaskAttachmentResponseDTO;
import com.worksphere.WorkSphere.dto.TaskCommentResponseDTO;
import com.worksphere.WorkSphere.dto.TaskResponseDTO;
import com.worksphere.WorkSphere.dto.UpdateTaskRequestDTO;
import com.worksphere.WorkSphere.entity.Project;
import com.worksphere.WorkSphere.entity.Task;
import com.worksphere.WorkSphere.entity.TaskAttachment;
import com.worksphere.WorkSphere.entity.TaskComment;
import com.worksphere.WorkSphere.entity.User;
import com.worksphere.WorkSphere.enums.TaskStatus;
import com.worksphere.WorkSphere.repository.ProjectRepository;
import com.worksphere.WorkSphere.repository.TaskAttachmentRepository;
import com.worksphere.WorkSphere.repository.TaskCommentRepository;
import com.worksphere.WorkSphere.repository.TaskRepository;
import com.worksphere.WorkSphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;
    
    private final TaskCommentRepository taskCommentRepository;

    // ================= CREATE TASK =================

    public TaskResponseDTO createTask(
            CreateTaskRequestDTO request
    ) {

        log.info(
                "Creating task title={}",
                request.getTitle()
        );

        Project project =
                projectRepository.findById(
                        request.getProjectId()
                ).orElseThrow(() -> {

                    log.warn(
                            "Project not found id={}",
                            request.getProjectId()
                    );

                    return new RuntimeException(
                            "Project not found"
                    );
                });

        User assignedUser =
                userRepository.findById(
                        request.getAssignedToId()
                ).orElseThrow(() -> {

                    log.warn(
                            "User not found id={}",
                            request.getAssignedToId()
                    );

                    return new RuntimeException(
                            "User not found"
                    );
                });

        Task task =
                Task.builder()
                        .project(project)
                        .assignedTo(assignedUser)
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .priority(request.getPriority())
                        .dueDate(request.getDueDate())
                        .status(TaskStatus.TODO)
                        .build();

        task = taskRepository.save(task);

        log.info(
                "Task created successfully id={}",
                task.getId()
        );

        return map(task);
    }

    // ================= UPDATE TASK =================

    public TaskResponseDTO updateTask(
            Long taskId,
            UpdateTaskRequestDTO request
    ) {

        log.info(
                "Updating task id={}",
                taskId
        );

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Task not found id={}",
                                    taskId
                            );

                            return new RuntimeException(
                                    "Task not found"
                            );
                        });

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());

        task = taskRepository.save(task);

        log.info(
                "Task updated successfully id={}",
                taskId
        );

        return map(task);
    }

    // ================= DELETE TASK =================

    public void deleteTask(Long taskId) {

        log.warn(
                "Deleting task id={}",
                taskId
        );

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Task not found id={}",
                                    taskId
                            );

                            return new RuntimeException(
                                    "Task not found"
                            );
                        });

        taskRepository.delete(task);

        log.warn(
                "Task deleted successfully id={}",
                taskId
        );
    }

    // ================= GET TASK =================

    public TaskResponseDTO getTaskById(
            Long taskId
    ) {

        log.info(
                "Fetching task id={}",
                taskId
        );

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Task not found id={}",
                                    taskId
                            );

                            return new RuntimeException(
                                    "Task not found"
                            );
                        });

        return map(task);
    }

    // ================= GET ALL TASKS =================

    public List<TaskResponseDTO> getAllTasks() {

        log.info("Fetching all tasks");

        List<TaskResponseDTO> tasks =
                taskRepository.findAll()
                        .stream()
                        .map(this::map)
                        .toList();

        log.info(
                "Fetched {} tasks",
                tasks.size()
        );

        return tasks;
    }

    // ================= GET PROJECT TASKS =================

    public List<TaskResponseDTO> getTasksByProject(
            Long projectId
    ) {

        log.info(
                "Fetching tasks for project={}",
                projectId
        );

        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::map)
                .toList();
    }

    // ================= GET USER TASKS =================

    public List<TaskResponseDTO> getTasksByUser(
            Long userId
    ) {

        log.info(
                "Fetching tasks for user={}",
                userId
        );

        return taskRepository.findByAssignedToId(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    // ================= DTO MAPPER =================

    private TaskResponseDTO map(
            Task task
    ) {

        return TaskResponseDTO.builder()
                .id(task.getId())
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .assignedToId(task.getAssignedTo().getId())
                .assignedToName(task.getAssignedTo().getName())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .completedAt(task.getCompletedAt())
                .build();
    }
    
    
    
    public TaskResponseDTO assignTask(
            Long taskId,
            Long userId
    ) {

        log.info(
                "Assigning task={} to user={}",
                taskId,
                userId
        );

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Task not found"
                                ));

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                ));

        task.setAssignedTo(user);

        task = taskRepository.save(task);

        log.info(
                "Task assigned successfully taskId={} userId={}",
                taskId,
                userId
        );

        return map(task);
    }
    
    
    
    public TaskResponseDTO updateStatus(
            Long taskId,
            TaskStatus status
    ) {

        log.info(
                "Updating task status taskId={} status={}",
                taskId,
                status
        );

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Task not found"
                                ));

        task.setStatus(status);

        if (status == TaskStatus.DONE) {

            task.setCompletedAt(
                    LocalDateTime.now()
            );

        } else {

            task.setCompletedAt(null);
        }

        task = taskRepository.save(task);

        log.info(
                "Task status updated successfully taskId={}",
                taskId
        );

        return map(task);
    }
    
    
    public TaskCommentResponseDTO addComment(
            Long taskId,
            AddTaskCommentRequestDTO request
    ) {

        log.info(
                "Adding comment on task={}",
                taskId
        );

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Task not found"
                                ));

        User user =
                userRepository.findById(
                        request.getUserId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        TaskComment comment =
                TaskComment.builder()
                        .task(task)
                        .user(user)
                        .comment(request.getComment())
                        .build();

        comment = taskCommentRepository.save(comment);

        log.info(
                "Comment added successfully taskId={}",
                taskId
        );

        return TaskCommentResponseDTO.builder()
                .id(comment.getId())
                .taskId(task.getId())
                .userId(user.getId())
                .userName(user.getName())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();
    }
    
    public List<TaskCommentResponseDTO> getComments(
            Long taskId
    ) {

        log.info(
                "Fetching comments for task={}",
                taskId
        );

        return taskCommentRepository
                .findByTaskId(taskId)
                .stream()
                .map(comment ->
                        TaskCommentResponseDTO.builder()
                                .id(comment.getId())
                                .taskId(
                                        comment.getTask().getId()
                                )
                                .userId(
                                        comment.getUser().getId()
                                )
                                .userName(
                                        comment.getUser().getName()
                                )
                                .comment(
                                        comment.getComment()
                                )
                                .createdAt(
                                        comment.getCreatedAt()
                                )
                                .build())
                .toList();
    }
    
   
}