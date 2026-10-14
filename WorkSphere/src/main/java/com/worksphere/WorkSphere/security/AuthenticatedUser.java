package com.worksphere.WorkSphere.security;

public record AuthenticatedUser(
        Long id,
        String email) {
}