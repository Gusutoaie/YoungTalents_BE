package com.example.YoungTalens.service;

import com.example.YoungTalens.dto.FacultyDto;
import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.entity.User;
import com.example.YoungTalens.mapper.FacultyMapper;
import com.example.YoungTalens.repository.FacultyRepository;
import com.example.YoungTalens.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<FacultyDto> findAll() {
        return facultyRepository.findAll().stream()
                .map(FacultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacultyDto save(FacultyDto facultyDto) {
        Faculty faculty = FacultyMapper.toEntity(facultyDto);

        // Handle dean
        if (faculty.getDean() != null) {
            Optional<User> dean = userRepository.findById(faculty.getDean().getId());
            dean.ifPresent(faculty::setDean);
        }

        // Handle pro-dean
        if (faculty.getProDean() != null) {
            Optional<User> proDean = userRepository.findById(faculty.getProDean().getId());
            proDean.ifPresent(faculty::setProDean);
        }

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

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}
