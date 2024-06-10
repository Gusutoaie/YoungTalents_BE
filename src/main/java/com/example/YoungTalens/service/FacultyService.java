package com.example.YoungTalens.service;

import com.example.YoungTalens.dto.FacultyDto;
import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.mapper.FacultyMapper;
import com.example.YoungTalens.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public List<FacultyDto> findAll() {
        return facultyRepository.findAll().stream()
                .map(FacultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacultyDto save(FacultyDto facultyDto) {
        Faculty faculty = FacultyMapper.toEntity(facultyDto);
        Faculty savedFaculty = facultyRepository.save(faculty);
        return FacultyMapper.toDto(savedFaculty);
    }

    public FacultyDto getFacultyByName(String name) {
        Optional<Faculty> faculty = facultyRepository.findByName(name);
        return faculty.map(FacultyMapper::toDto).orElse(null);
    }

    public void saveAll(List<Faculty> faculties) {
        facultyRepository.saveAll(faculties);
    }
    public boolean existsByName(String name) {
        return facultyRepository.existsByName(name);
    }
}
