package com.example.YoungTalens.service;

import com.example.YoungTalens.dto.UserDto;
import com.example.YoungTalens.entity.User;
import com.example.YoungTalens.mapper.FacultyMapper;
import com.example.YoungTalens.mapper.UserMapper;
import com.example.YoungTalens.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void updateUser(UserDto userDto) {
        User existingUser = userRepository.findUserByEmail(userDto.email());
        existingUser.setEmail(userDto.email());
        existingUser.setPassword(userDto.password());
        existingUser.setFirstName(userDto.firstName());
        existingUser.setLastName(userDto.lastName());
        existingUser.setUsername(userDto.username());
        existingUser.setActualJob(userDto.actualJob());
        existingUser.setActualCompany(userDto.actualCompany());
        existingUser.setProfessionalDomain(userDto.professionalDomain());
        existingUser.setMentor(userDto.mentor());
        existingUser.setToken(userDto.token());
        existingUser.setYearOfStudy(userDto.yearOfStudy());

            userRepository.save(existingUser);
        }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

}
