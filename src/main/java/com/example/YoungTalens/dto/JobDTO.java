package com.example.YoungTalens.dto;

import java.time.LocalDateTime;

public record JobDTO(
        Long id,
        String title,
        String company,
        String location,
        String description,
        String responsibilities,
        String mandatorySkills,
        String niceToHaveSkills,
        String logoUrl,
        LocalDateTime vechimeAnunt,
        String tipOferta,
        String experienta,
        String remote
) {}
