package com.worksphere.WorkSphere.service;

import com.worksphere.WorkSphere.dto.TaskAttachmentResponseDTO;
import com.worksphere.WorkSphere.entity.Task;
import com.worksphere.WorkSphere.entity.TaskAttachment;
import com.worksphere.WorkSphere.repository.TaskAttachmentRepository;
import com.worksphere.WorkSphere.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskAttachmentService {

    private final TaskRepository taskRepository;
    private final TaskAttachmentRepository taskAttachmentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ================= UPLOAD ATTACHMENT =================

    public TaskAttachmentResponseDTO uploadAttachment(
            Long taskId,
            MultipartFile file
    ) throws Exception {

        log.info(
                "Uploading attachment for taskId={}",
                taskId
        );

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Task not found"
                        ));

        String folder =
                uploadDir + "/tasks";

        Files.createDirectories(
                Paths.get(folder)
        );

        String fileName =
                UUID.randomUUID()
                        + "_"
                        + file.getOriginalFilename();

        Path filePath =
                Paths.get(folder, fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        TaskAttachment attachment =
                TaskAttachment.builder()
                        .task(task)
                        .fileName(file.getOriginalFilename())
                        .filePath(filePath.toString())
                        .fileSize(file.getSize())
                        .contentType(file.getContentType())
                        .build();

        attachment =
                taskAttachmentRepository.save(
                        attachment
                );

        log.info(
                "Attachment uploaded successfully id={} taskId={}",
                attachment.getId(),
                taskId
        );

        return map(attachment);
    }

    // ================= GET TASK ATTACHMENTS =================

    public List<TaskAttachmentResponseDTO> getTaskAttachments(
            Long taskId
    ) {

        log.info(
                "Fetching attachments for taskId={}",
                taskId
        );

        return taskAttachmentRepository
                .findByTaskId(taskId)
                .stream()
                .map(this::map)
                .toList();
    }

    // ================= DELETE ATTACHMENT =================

    public void deleteAttachment(
            Long attachmentId
    ) throws Exception {

        log.warn(
                "Deleting attachment id={}",
                attachmentId
        );

        TaskAttachment attachment =
                taskAttachmentRepository
                        .findById(attachmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Attachment not found"
                                ));

        Files.deleteIfExists(
                Paths.get(
                        attachment.getFilePath()
                )
        );

        taskAttachmentRepository.delete(
                attachment
        );

        log.warn(
                "Attachment deleted successfully id={}",
                attachmentId
        );
    }

    // ================= MAPPER =================

    private TaskAttachmentResponseDTO map(
            TaskAttachment attachment
    ) {

        return TaskAttachmentResponseDTO
                .builder()
                .id(attachment.getId())
                .taskId(
                        attachment.getTask().getId()
                )
                .fileName(
                        attachment.getFileName()
                )
                .filePath(
                        attachment.getFilePath()
                )
                .fileSize(
                        attachment.getFileSize()
                )
                .contentType(
                        attachment.getContentType()
                )
                .build();
    }
}