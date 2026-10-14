package com.worksphere.WorkSphere.controller;

import com.worksphere.WorkSphere.dto.AssignProjectMemberRequestDTO;
import com.worksphere.WorkSphere.dto.ProjectMemberResponseDTO;
import com.worksphere.WorkSphere.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    // ================= ASSIGN MEMBER =================

    @PostMapping
    public ResponseEntity<?> assignMember(
            @RequestBody
            AssignProjectMemberRequestDTO request
    ) {

        try {

            return ResponseEntity.ok(
                    projectMemberService.assignMember(
                            request
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ================= REMOVE MEMBER =================

    @DeleteMapping
    public ResponseEntity<?> removeMember(
            @RequestParam Long projectId,
            @RequestParam Long userId
    ) {

        try {

            projectMemberService.removeMember(
                    projectId,
                    userId
            );

            return ResponseEntity.ok(
                    "Member removed successfully"
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // ================= GET MEMBERS =================

    @GetMapping("/{projectId}")
    public ResponseEntity<
            List<ProjectMemberResponseDTO>>
    getProjectMembers(
            @PathVariable Long projectId
    ) {

        return ResponseEntity.ok(
                projectMemberService
                        .getProjectMembers(
                                projectId
                        )
        );
    }
}