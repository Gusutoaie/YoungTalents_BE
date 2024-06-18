package com.example.YoungTalens.dto;

import com.example.YoungTalens.entity.User;

public record FacultyDto(Long id, String name, String description, User dean, User proDean) {}

