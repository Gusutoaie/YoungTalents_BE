package com.example.YoungTalens.controller;

import com.example.YoungTalens.dto.FacultyDto;
import com.example.YoungTalens.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public List<FacultyDto> getAllFaculties() {
        return facultyService.findAll();
    }

    @GetMapping("/{name}")
    public FacultyDto getFacultyByName(@PathVariable String name) {
        return facultyService.getFacultyByName(name);
    }

    @PostMapping
    public FacultyDto createFaculty(@RequestBody FacultyDto facultyDto) {
        return facultyService.save(facultyDto);
    }

    @PutMapping("/{name}")
    public FacultyDto updateFaculty(@PathVariable String name, @RequestBody FacultyDto updatedFacultyDto) {
        FacultyDto existingFaculty = facultyService.getFacultyByName(name);
        if (existingFaculty != null) {
            FacultyDto newFacultyDto = new FacultyDto(existingFaculty.id(), updatedFacultyDto.name(), updatedFacultyDto.description(), updatedFacultyDto.dean(), updatedFacultyDto.proDean());
            return facultyService.save(newFacultyDto);
        } else {
            return facultyService.save(updatedFacultyDto);
        }
    }

    @DeleteMapping("/{name}")
    public void deleteFaculty(@PathVariable String name) {
        FacultyDto facultyDto = facultyService.getFacultyByName(name);
        if (facultyDto != null) {
            facultyService.deleteFaculty(facultyDto.id());
        }
    }
}
