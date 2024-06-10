package com.example.YoungTalens.dto;

public record ArticleDTO(
        Long id,
        String title,
        String description,
        String date,
        String location,
        String image
) {}
