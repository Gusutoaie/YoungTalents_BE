package com.example.YoungTalens.util;

import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FacultyService facultyService;

    @Override
    public void run(String... args) throws Exception {
        Faculty[] facultiesArray = {
                new Faculty(null, "Facultatea de Arte și Design", null, null),
                new Faculty(null, "Facultatea de Chimie, Biologie, Geografie", null, null),
                new Faculty(null, "Facultatea de Drept", null, null),
                new Faculty(null, "Facultatea de Economie și de Administrare a Afacerilor", null, null),
                new Faculty(null, "Facultatea de Educație Fizică și Sport", null, null),
                new Faculty(null, "Facultatea de Fizică", null, null),
                new Faculty(null, "Facultatea de Litere, Istorie și Teologie", null, null),
                new Faculty(null, "Facultatea de Matematică și Informatică", null, null),
                new Faculty(null, "Facultatea de Muzică și Teatru", null, null),
                new Faculty(null, "Facultatea de Sociologie și Psihologie", null, null),
                new Faculty(null, "Facultatea de Științe Politice, Filosofie și Științe ale Comunicării", null, null)
        };

        List<Faculty> faculties = Arrays.asList(facultiesArray);

        List<Faculty> facultiesToSave = faculties.stream()
                .filter(faculty -> !facultyService.existsByName(faculty.getName()))
                .collect(Collectors.toList());

        if (!facultiesToSave.isEmpty()) {
            facultyService.saveAll(facultiesToSave);
        }
    }
}
