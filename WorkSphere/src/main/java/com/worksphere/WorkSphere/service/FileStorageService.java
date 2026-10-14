package com.worksphere.WorkSphere.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadProfilePicture(
            MultipartFile file
    ) {

        return upload(
                file,
                "profile"
        );
    }

    public String uploadTaskFile(
            MultipartFile file
    ) {

        return upload(
                file,
                "tasks"
        );
    }

    private String upload(
            MultipartFile file,
            String folder
    ) {

        try {

            Path folderPath =
                    Paths.get(
                            uploadDir,
                            folder
                    );

            Files.createDirectories(
                    folderPath
            );

            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + file.getOriginalFilename();

            Path filePath =
                    folderPath.resolve(
                            fileName
                    );

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            log.info(
                    "File uploaded successfully {}",
                    filePath
            );

            return filePath.toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "File upload failed"
            );
        }
    }
}