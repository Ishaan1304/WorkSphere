package com.worksphere.WorkSphere.controller;

import com.worksphere.WorkSphere.dto.UpdatePasswordRequestDTO;
import com.worksphere.WorkSphere.dto.UpdateUserRequestDTO;
import com.worksphere.WorkSphere.entity.User;
import com.worksphere.WorkSphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String helloUser() {

        return "Hello USER";
    }

    // ================= UPDATE USER =================

    @PutMapping("/{userId}")
    public User updateUser(
            @PathVariable Long userId,

            @RequestBody UpdateUserRequestDTO request) {

        return userService.updateUser(
                userId,
                request);
    }

    // ================= UPDATE PASSWORD =================

    @PutMapping("/{userId}/password")
    public String updatePassword(
            @PathVariable Long userId,

            @RequestBody UpdatePasswordRequestDTO request) {

        userService.updatePassword(
                userId,
                request);

        return "Password updated successfully";
    }

    @GetMapping
    public Page<User> getUsers(
            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "createdAt") String sortBy,

            @RequestParam(defaultValue = "desc") String direction) {

        return userService.getAllUsers(
                page,
                size,
                sortBy,
                direction);
    }

    @GetMapping("/{id}")
    public User getUser(
            @PathVariable Long id) {

        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);
    }
    
    @PostMapping(
            value = "/{userId}/profile-picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {

        String message =
                userService.uploadProfilePicture(
                        userId,
                        file
                );

        return ResponseEntity.ok(message);
    }

}