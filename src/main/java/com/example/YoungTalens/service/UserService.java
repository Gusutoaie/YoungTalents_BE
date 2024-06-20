package com.example.YoungTalens.service;

import com.example.YoungTalens.dto.RoleDto;
import com.example.YoungTalens.dto.UserDto;
import com.example.YoungTalens.entity.Role;
import com.example.YoungTalens.entity.User;
import com.example.YoungTalens.mapper.UserMapper;
import com.example.YoungTalens.repository.RoleRepository;
import com.example.YoungTalens.repository.UserRepository;
import com.example.YoungTalens.util.SendEmail;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserByUsernameAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        return UserMapper.toDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        if (userDto.role() == null) {
            Role defaultRole = roleRepository.findByName("Simple User");
            if (defaultRole == null) {
                throw new RuntimeException("Default role not found");
            }
            userDto = new UserDto(
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
                    userDto.facultyDto(),
                    userDto.yearOfStudy(),
                    userDto.profilePicturePath(),
                    new RoleDto(defaultRole.getId(), defaultRole.getName())
            );
        }
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
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

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

        // Handle role if it is provided in the update
        if (userDto.role() != null) {
            Role role = roleRepository.findByName(userDto.role().name());
            if (role == null) {
                role = new Role();
                role.setName(userDto.role().name());
                role = roleRepository.save(role);
            }
            existingUser.setRole(role);
        }

        userRepository.save(existingUser);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public List<UserDto> createUsersFromEmails(List<String> emails) {
        return emails.stream().map(email -> {
            String password = generateRandomPassword();
            UserDto userDto = new UserDto(
                    null,
                    email,
                    password,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            Role defaultRole = roleRepository.findByName("Simple User");
            if (defaultRole == null) {
                throw new RuntimeException("Default role not found");
            }

            userDto = new UserDto(
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
                    userDto.facultyDto(),
                    userDto.yearOfStudy(),
                    userDto.profilePicturePath(),
                    new RoleDto(defaultRole.getId(), defaultRole.getName())
            );

            User savedUser = userRepository.save(UserMapper.toEntity(userDto));
            SendEmail.sendNewAccountEmail(email, password);
            return UserMapper.toDto(savedUser);
        }).collect(Collectors.toList());
    }

    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }



}
