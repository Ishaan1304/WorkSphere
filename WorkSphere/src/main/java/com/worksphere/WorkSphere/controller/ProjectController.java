package com.worksphere.WorkSphere.controller;

import com.worksphere.WorkSphere.dto.ProjectRequestDTO;
import com.worksphere.WorkSphere.dto.ProjectResponseDTO;
import com.worksphere.WorkSphere.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestBody ProjectRequestDTO request
    ) {

        try {

            return ResponseEntity.ok(
                    projectService.createProject(request)
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectRequestDTO request
    ) {

        try {

            return ResponseEntity.ok(
                    projectService.updateProject(
                            projectId,
                            request
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(
            @PathVariable Long projectId
    ) {

        try {

            projectService.deleteProject(projectId);

            return ResponseEntity.ok(
                    "Project deleted successfully"
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(
            @PathVariable Long projectId
    ) {

        try {

            return ResponseEntity.ok(
                    projectService.getProjectById(projectId)
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>>
    getAllProjects() {

        return ResponseEntity.ok(
                projectService.getAllProjects()
        );
    }
}