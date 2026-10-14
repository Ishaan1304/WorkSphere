package com.worksphere.WorkSphere.controller;

import com.worksphere.WorkSphere.dto.TaskAttachmentResponseDTO;
import com.worksphere.WorkSphere.service.TaskAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/task-attachments")
@RequiredArgsConstructor
public class TaskAttachmentController {

    private final TaskAttachmentService taskAttachmentService;

    // ================= UPLOAD =================

    @PostMapping(
            value = "/task/{taskId}",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<TaskAttachmentResponseDTO>
    uploadAttachment(
            @PathVariable Long taskId,
            @RequestPart("file")
            MultipartFile file
    ) throws Exception {

        return ResponseEntity.ok(
                taskAttachmentService
                        .uploadAttachment(
                                taskId,
                                file
                        )
        );
    }

    // ================= GET ALL =================

    @GetMapping("/task/{taskId}")
    public ResponseEntity<
            List<TaskAttachmentResponseDTO>
            > getTaskAttachments(
                    @PathVariable Long taskId
    ) {

        return ResponseEntity.ok(
                taskAttachmentService
                        .getTaskAttachments(
                                taskId
                        )
        );
    }

    // ================= DELETE =================

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<String>
    deleteAttachment(
            @PathVariable Long attachmentId
    ) throws Exception {

        taskAttachmentService
                .deleteAttachment(
                        attachmentId
                );

        return ResponseEntity.ok(
                "Attachment deleted successfully"
        );
    }
}