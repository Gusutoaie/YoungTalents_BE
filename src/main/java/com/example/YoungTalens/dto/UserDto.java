package com.example.YoungTalens.dto;

import java.util.Date;

public record UserDto (Long id,String email,String password,String firstName,String lastName, String phoneNumber,String birthDate,String points,String token, FacultyDto facultyDto,String yearOfStudy) {}

