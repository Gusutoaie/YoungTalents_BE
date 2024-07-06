package com.example.YoungTalens.dto;

public record FacultyDto(Long id, String name, String description, com.example.YoungTalens.entity.User dean, com.example.YoungTalens.entity.User proDean) {}

