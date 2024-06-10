package com.example.YoungTalens.mapper;

import com.example.YoungTalens.dto.FacultyDto;
import com.example.YoungTalens.entity.Faculty;
import org.springframework.stereotype.Component;


@Component
public class FacultyMapper {
    public static FacultyDto toDto(Faculty faculty) {
        if (faculty == null) {
            return null;
        }

        return new FacultyDto(faculty.getId(), faculty.getName(), faculty.getDean(), faculty.getProDean());
    }

    public static Faculty toEntity(FacultyDto facultyDto) {
        if (facultyDto == null) {
            return null;
        }

        return new Faculty(facultyDto.id(), facultyDto.name(), facultyDto.dean(), facultyDto.proDean());
    }
}