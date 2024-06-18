package com.example.YoungTalens.dto;

import java.util.Date;

public record UserDto(
        Long id,
        String email,
        String password,
        String firstName,
        String lastName,
        String username,
        String actualJob,
        String actualCompany,
        String professionalDomain,
        String mentor,
        String token,
        FacultyDto facultyDto,
        String yearOfStudy,
        String profilePicturePath,
        RoleDto role


) {}
