package com.worksphere.WorkSphere.service;

import com.worksphere.WorkSphere.dto.CreateUserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import com.worksphere.WorkSphere.dto.UpdatePasswordRequestDTO;
import com.worksphere.WorkSphere.dto.UpdateUserRequestDTO;
import com.worksphere.WorkSphere.entity.User;
import com.worksphere.WorkSphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final FileStorageService fileStorageService;

        // ================= CREATE USER =================

        public User createUser(CreateUserRequest request) {

                log.info(
                                "Creating user with email={}",
                                request.getEmail());

                if (userRepository.existsByEmail(
                                request.getEmail())) {

                        log.warn(
                                        "User creation failed. Email already exists={}",
                                        request.getEmail());

                        throw new RuntimeException(
                                        "Email already exists");
                }

                User user = User.builder()
                                .name(request.getName())
                                .email(request.getEmail())
                                .password(
                                                passwordEncoder.encode(
                                                                request.getPassword()))
                                .bio(request.getBio())
                                .profilePicture(
                                                request.getProfilePicture())
                                .status(request.getStatus())
                                .roles(
                                                request.getRoles() != null
                                                                ? request.getRoles()
                                                                : new HashSet<>())
                                .isActive(true)
                                .emailVerified(false)
                                .accountLocked(false)
                                .failedAttempts(0)
                                .isDeleted(false)
                                .build();

                User savedUser = userRepository.save(user);

                log.info(
                                "User created successfully id={} email={}",
                                savedUser.getId(),
                                savedUser.getEmail());

                return savedUser;
        }

        // ================= GET ALL USERS =================

        public Page<User> getAllUsers(
                        int page,
                        int size,
                        String sortBy,
                        String direction) {

                log.info(
                                "Fetching users page={} size={} sortBy={} direction={}",
                                page,
                                size,
                                sortBy,
                                direction);

                Sort sort = direction.equalsIgnoreCase("desc")
                                ? Sort.by(sortBy).descending()
                                : Sort.by(sortBy).ascending();

                Pageable pageable = PageRequest.of(
                                page,
                                size,
                                sort);

                Page<User> users = userRepository
                                .findByIsDeletedFalse(pageable);

                log.info(
                                "Fetched {} users",
                                users.getNumberOfElements());

                return users;
        }

        // ================= GET USER =================

        public User getUserById(Long id) {

                log.info(
                                "Fetching user id={}",
                                id);

                User user = userRepository.findById(id)
                                .filter(u -> !u.getIsDeleted())
                                .orElseThrow(() -> {

                                        log.warn(
                                                        "User not found id={}",
                                                        id);

                                        return new RuntimeException(
                                                        "User not found");
                                });

                log.info(
                                "User fetched successfully id={}",
                                id);

                return user;
        }

        // ================= UPDATE USER =================

        public User updateUser(
                        Long userId,
                        UpdateUserRequestDTO request) {

                log.info(
                                "Updating user id={}",
                                userId);

                User user = getUserById(userId);

                if (request.getName() != null) {
                        user.setName(
                                        request.getName());
                }

                if (request.getBio() != null) {
                        user.setBio(
                                        request.getBio());
                }

                if (request.getProfilePicture() != null) {
                        user.setProfilePicture(
                                        request.getProfilePicture());
                }

                if (request.getStatus() != null) {
                        user.setStatus(
                                        request.getStatus());
                }

                User updatedUser = userRepository.save(user);

                log.info(
                                "User updated successfully id={}",
                                userId);

                return updatedUser;
        }

        // ================= DELETE USER =================

        public void deleteUser(Long userId) {

                log.warn(
                                "Deleting user id={}",
                                userId);

                User user = getUserById(userId);

                user.setIsDeleted(true);

                userRepository.save(user);

                log.warn(
                                "User soft deleted id={}",
                                userId);
        }

        // ================= UPDATE PASSWORD =================

        public void updatePassword(
                        Long userId,
                        UpdatePasswordRequestDTO request) {

                log.info(
                                "Updating password for user id={}",
                                userId);

                User user = getUserById(userId);

                boolean matches = passwordEncoder.matches(
                                request.getOldPassword(),
                                user.getPassword());

                if (!matches) {

                        log.warn(
                                        "Password update failed. Incorrect old password userId={}",
                                        userId);

                        throw new RuntimeException(
                                        "Old password is incorrect");
                }

                user.setPassword(
                                passwordEncoder.encode(
                                                request.getNewPassword()));

                userRepository.save(user);

                log.info(
                                "Password updated successfully userId={}",
                                userId);
        }
        
        
        public String uploadProfilePicture(
                Long userId,
                MultipartFile file
        ) {

            log.info(
                    "Uploading profile picture for userId={}",
                    userId
            );

            User user = getUserById(userId);

            if (file.isEmpty()) {

                log.warn(
                        "Upload failed. Empty file userId={}",
                        userId
                );

                throw new RuntimeException(
                        "Please select a file"
                );
            }

            try {

                String uploadDir =
                        System.getProperty("user.dir")
                                + File.separator
                                + "uploads"
                                + File.separator
                                + "profiles";

                File directory =
                        new File(uploadDir);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName =
                        UUID.randomUUID()
                                + "_"
                                + file.getOriginalFilename();

                String filePath =
                        uploadDir
                                + File.separator
                                + fileName;

                file.transferTo(
                        new File(filePath)
                );

                user.setProfilePicture(fileName);

                userRepository.save(user);

                log.info(
                        "Profile picture uploaded successfully userId={} file={}",
                        userId,
                        fileName
                );

                return "Profile picture uploaded successfully";

            } catch (IOException e) {

                log.error(
                        "Error uploading profile picture userId={}",
                        userId,
                        e
                );

                throw new RuntimeException(
                        "Failed to upload file"
                );
            }
        }
        
        
}