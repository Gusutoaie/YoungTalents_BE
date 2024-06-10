package com.example.YoungTalens.mapper;

import com.example.YoungTalens.dto.UserDto;
import com.example.YoungTalens.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getActualJob(),
                user.getActualCompany(),
                user.getProfessionalDomain(),
                user.getMentor(),
                user.getToken(),
                FacultyMapper.toDto(user.getFaculty()),
                user.getYearOfStudy(),
                user.getProfilePicturePath(),
                user.getRole() != null ? user.getRole().getName() : null // Map role name

        );
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return new User(
                userDto.id(),
                userDto.email(),
                userDto.password(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.username(),
                userDto.actualJob(),
                userDto.actualCompany(),
                userDto.professionalDomain(),
                userDto.mentor(),
                userDto.token(),
                FacultyMapper.toEntity(userDto.facultyDto()),
                userDto.yearOfStudy(),
                userDto.profilePicturePath()
        );
    }
}
