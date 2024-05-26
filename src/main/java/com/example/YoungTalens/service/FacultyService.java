package com.example.YoungTalens.service;

import com.example.YoungTalens.dto.FacultyDto;
import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.mapper.FacultyMapper;
import com.example.YoungTalens.repository.FacultyRepository;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
    public FacultyDto createFaculty(FacultyDto facultyDto) {
        Faculty faculty = FacultyMapper.toEntity(facultyDto);
        facultyRepository.save(faculty);
        return FacultyMapper.toDto(faculty);
    }

    public FacultyDto getFacultyByName(String name) {
        Faculty faculty = facultyRepository.findByName(name);
        return FacultyMapper.toDto(faculty);
    }
}
