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

    private String username;

    private String actualJob;

    private String actualCompany;

    private String professionalDomain;

    private String mentor;

    private String token; // Ensure token handling is secure and fits your auth needs

    @ManyToOne
    @JoinColumn(name = "faculty_id") // Explicitly define join column
    private Faculty faculty;

    @Column(nullable = false)
    private String yearOfStudy; // Added to handle the academic year of the student
    private String profilePicturePath;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false) // Explicitly define join column for role
    private Role role;


    // Constructor without role for easier mapping
    public User(Long id, String email, String password, String firstName, String lastName, String username, String actualJob, String actualCompany, String professionalDomain, String mentor, String token, Faculty faculty, String yearOfStudy, String profilePicturePath) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.actualJob = actualJob;
        this.actualCompany = actualCompany;
        this.professionalDomain = professionalDomain;
        this.mentor = mentor;
        this.token = token;
        this.faculty = faculty;
        this.yearOfStudy = yearOfStudy;
        this.profilePicturePath = profilePicturePath;
    }

}