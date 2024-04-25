package com.example.YoungTalens.service;

import com.example.YoungTalens.dto.UserDto;
import com.example.YoungTalens.entity.User;
import com.example.YoungTalens.mapper.FacultyMapper;
import com.example.YoungTalens.mapper.UserMapper;
import com.example.YoungTalens.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserByUsernameAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        return UserMapper.toDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(UserMapper.toEntity(userDto));
        return UserMapper.toDto(savedUser);
    }

    public UserDto getUserByEmailAddress(String email) {
        User user = userRepository.findUserByEmail(email);
        return UserMapper.toDto(user);
    }
    public UserDto getUserByToken(String token) {
        User user = userRepository.findByToken(token);
        return UserMapper.toDto(user);
    }
    public void updateUser(Long id, UserDto userDto) {
        Optional < User > existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            existingUser.get().setEmail(userDto.email());
            existingUser.get().setPassword(userDto.password());
            existingUser.get().setFirstName(userDto.firstName());
            existingUser.get().setLastName(userDto.lastName());
            existingUser.get().setBirthDate(userDto.birthDate());
            existingUser.get().setPhoneNumber(userDto.phoneNumber());
            existingUser.get().setPoints(userDto.points());
            existingUser.get().setToken(userDto.token());
            existingUser.get().setFaculty(FacultyMapper.toEntity(userDto.facultyDto()));
            existingUser.get().setYearOfStudy(userDto.yearOfStudy());
            userRepository.save(existingUser.get());
        }

    }
}
