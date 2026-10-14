package com.worksphere.WorkSphere.controller;

import com.worksphere.WorkSphere.dto.AddTaskCommentRequestDTO;
import com.worksphere.WorkSphere.dto.CreateTaskRequestDTO;
import com.worksphere.WorkSphere.dto.TaskCommentResponseDTO;
import com.worksphere.WorkSphere.dto.TaskResponseDTO;
import com.worksphere.WorkSphere.dto.UpdateTaskRequestDTO;
import com.worksphere.WorkSphere.dto.UpdateTaskStatusRequestDTO;
import com.worksphere.WorkSphere.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ================= CREATE TASK =================

    @PostMapping
    public ResponseEntity<?> createTask(
            @RequestBody CreateTaskRequestDTO request
    ) {

        try {

            return ResponseEntity.ok(
                    taskService.createTask(request)
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ================= UPDATE TASK =================

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long taskId,
            @RequestBody UpdateTaskRequestDTO request
    ) {

        try {

            return ResponseEntity.ok(
                    taskService.updateTask(
                            taskId,
                            request
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ================= DELETE TASK =================

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long taskId
    ) {

        try {

            taskService.deleteTask(taskId);

            return ResponseEntity.ok(
                    "Task deleted successfully"
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ================= GET TASK =================

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTask(
            @PathVariable Long taskId
    ) {

        return ResponseEntity.ok(
                taskService.getTaskById(taskId)
        );
    }

    // ================= GET ALL TASKS =================

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>>
    getAllTasks() {

        return ResponseEntity.ok(
                taskService.getAllTasks()
        );
    }

    // ================= GET PROJECT TASKS =================

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponseDTO>>
    getProjectTasks(
            @PathVariable Long projectId
    ) {

        return ResponseEntity.ok(
                taskService.getTasksByProject(projectId)
        );
    }

    // ================= GET USER TASKS =================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>>
    getUserTasks(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                taskService.getTasksByUser(userId)
        );
    }
    
    
    
    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<?> assignTask(
            @PathVariable Long taskId,
            @PathVariable Long userId
    ) {

        try {

            return ResponseEntity.ok(
                    taskService.assignTask(
                            taskId,
                            userId
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
    
    
    @PutMapping("/{taskId}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long taskId,
            @RequestBody UpdateTaskStatusRequestDTO request
    ) {

        try {

            return ResponseEntity.ok(
                    taskService.updateStatus(
                            taskId,
                            request.getStatus()
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
    
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable Long taskId,
            @RequestBody AddTaskCommentRequestDTO request
    ) {

        try {

            return ResponseEntity.ok(
                    taskService.addComment(
                            taskId,
                            request
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
    
    
    
    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<TaskCommentResponseDTO>>
    getComments(
            @PathVariable Long taskId
    ) {

        return ResponseEntity.ok(
                taskService.getComments(taskId)
        );
    }
    
    
   
    
}