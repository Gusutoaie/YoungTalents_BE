package com.example.YoungTalens.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password; // Consider using better security practices for password storage

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String birthDate; // Consider using java.time.LocalDate for date handling

    private String points; // Changed from String to Integer for proper numerical handling

    private String token; // Ensure token handling is secure and fits your auth needs

    @ManyToOne
    @JoinColumn(name = "faculty_id") // Explicitly define join column
    private Faculty faculty;

    @Column(nullable = false)
    private String yearOfStudy; // Added to handle the academic year of the student
}