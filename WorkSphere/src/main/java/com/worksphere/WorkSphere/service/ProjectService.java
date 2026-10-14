package com.worksphere.WorkSphere.service;

import com.worksphere.WorkSphere.dto.ProjectRequestDTO;
import com.worksphere.WorkSphere.dto.ProjectResponseDTO;
import com.worksphere.WorkSphere.entity.Project;
import com.worksphere.WorkSphere.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;

    // ================= CREATE PROJECT =================

    public ProjectResponseDTO createProject(
            ProjectRequestDTO request
    ) {

        log.info(
                "Creating project name={}",
                request.getName()
        );

        if (request.getName() == null
                || request.getName().isBlank()) {

            log.warn(
                    "Project creation failed. Name is empty"
            );

            throw new RuntimeException(
                    "Project name is required"
            );
        }

        Project project =
                Project.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .status(request.getStatus())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .build();

        project = projectRepository.save(project);

        log.info(
                "Project created successfully id={} name={}",
                project.getId(),
                project.getName()
        );

        return map(project);
    }

    // ================= UPDATE PROJECT =================

    public ProjectResponseDTO updateProject(
            Long projectId,
            ProjectRequestDTO request
    ) {

        log.info(
                "Updating project id={}",
                projectId
        );

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Project not found id={}",
                                    projectId
                            );

                            return new RuntimeException(
                                    "Project not found"
                            );
                        });

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());

        project = projectRepository.save(project);

        log.info(
                "Project updated successfully id={}",
                projectId
        );

        return map(project);
    }

    // ================= DELETE PROJECT =================

    public void deleteProject(
            Long projectId
    ) {

        log.warn(
                "Deleting project id={}",
                projectId
        );

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Project not found id={}",
                                    projectId
                            );

                            return new RuntimeException(
                                    "Project not found"
                            );
                        });

        projectRepository.delete(project);

        log.warn(
                "Project deleted successfully id={}",
                projectId
        );
    }

    // ================= GET PROJECT =================

    public ProjectResponseDTO getProjectById(
            Long projectId
    ) {

        log.info(
                "Fetching project id={}",
                projectId
        );

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() -> {

                            log.warn(
                                    "Project not found id={}",
                                    projectId
                            );

                            return new RuntimeException(
                                    "Project not found"
                            );
                        });

        log.info(
                "Project fetched successfully id={}",
                projectId
        );

        return map(project);
    }

    // ================= GET ALL PROJECTS =================

    public List<ProjectResponseDTO> getAllProjects() {

        log.info(
                "Fetching all projects"
        );

        List<ProjectResponseDTO> projects =
                projectRepository.findAll()
                        .stream()
                        .map(this::map)
                        .toList();

        log.info(
                "Fetched {} projects",
                projects.size()
        );

        return projects;
    }

    // ================= DTO MAPPER =================

    private ProjectResponseDTO map(
            Project project
    ) {

        return ProjectResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }
}