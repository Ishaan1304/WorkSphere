package com.worksphere.WorkSphere.service;

import com.worksphere.WorkSphere.dto.AssignProjectMemberRequestDTO;
import com.worksphere.WorkSphere.dto.ProjectMemberResponseDTO;
import com.worksphere.WorkSphere.entity.Project;
import com.worksphere.WorkSphere.entity.ProjectMember;
import com.worksphere.WorkSphere.entity.User;
import com.worksphere.WorkSphere.repository.ProjectMemberRepository;
import com.worksphere.WorkSphere.repository.ProjectRepository;
import com.worksphere.WorkSphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    // ================= ASSIGN MEMBER =================

    public ProjectMemberResponseDTO assignMember(
            AssignProjectMemberRequestDTO request
    ) {

        log.info(
                "Assigning user={} to project={}",
                request.getUserId(),
                request.getProjectId()
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

        User user =
                userRepository.findById(
                        request.getUserId()
                ).orElseThrow(() -> {

                    log.warn(
                            "User not found id={}",
                            request.getUserId()
                    );

                    return new RuntimeException(
                            "User not found"
                    );
                });

        boolean alreadyAssigned =
                projectMemberRepository
                        .findByProjectIdAndUserId(
                                project.getId(),
                                user.getId()
                        )
                        .isPresent();

        if (alreadyAssigned) {

            log.warn(
                    "User already assigned to project"
            );

            throw new RuntimeException(
                    "User already assigned to project"
            );
        }

        ProjectMember member =
                ProjectMember.builder()
                        .project(project)
                        .user(user)
                        .memberRole(
                                request.getMemberRole()
                        )
                        .isActive(true)
                        .build();

        member =
                projectMemberRepository.save(member);

        log.info(
                "User assigned successfully memberId={}",
                member.getId()
        );

        return map(member);
    }

    // ================= REMOVE MEMBER =================

    public void removeMember(
            Long projectId,
            Long userId
    ) {

        log.warn(
                "Removing user={} from project={}",
                userId,
                projectId
        );

        ProjectMember member =
                projectMemberRepository
                        .findByProjectIdAndUserId(
                                projectId,
                                userId
                        )
                        .orElseThrow(() -> {

                            log.warn(
                                    "Project member not found"
                            );

                            return new RuntimeException(
                                    "Project member not found"
                            );
                        });

        projectMemberRepository.delete(member);

        log.warn(
                "Project member removed successfully"
        );
    }

    // ================= GET PROJECT MEMBERS =================

    public List<ProjectMemberResponseDTO>
    getProjectMembers(Long projectId) {

        log.info(
                "Fetching members for project={}",
                projectId
        );

        List<ProjectMemberResponseDTO> members =
                projectMemberRepository
                        .findByProjectId(projectId)
                        .stream()
                        .map(this::map)
                        .toList();

        log.info(
                "Fetched {} members",
                members.size()
        );

        return members;
    }

    // ================= DTO MAPPER =================

    private ProjectMemberResponseDTO map(
            ProjectMember member
    ) {

        return ProjectMemberResponseDTO
                .builder()
                .id(member.getId())
                .projectId(
                        member.getProject().getId()
                )
                .projectName(
                        member.getProject().getName()
                )
                .userId(
                        member.getUser().getId()
                )
                .userName(
                        member.getUser().getName()
                )
                .userEmail(
                        member.getUser().getEmail()
                )
                .memberRole(
                        member.getMemberRole()
                )
                .isActive(
                        member.getIsActive()
                )
                .build();
    }
}