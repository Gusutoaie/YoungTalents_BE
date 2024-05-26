package com.example.YoungTalens.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String company;
    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String responsibilities;

    @Column(columnDefinition = "TEXT")
    private String mandatorySkills;

    @Column(columnDefinition = "TEXT")
    private String niceToHaveSkills;

    private String logoUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime vechimeAnunt;
    private String tipOferta;
    private String experienta;
    private String remote;
}
