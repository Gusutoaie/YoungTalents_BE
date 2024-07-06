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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public UserDto getUserByUsernameAndPassword(String email, String password) {
        String hashedPassword = hashPassword(password);
        User user = userRepository.findByEmailAndPassword(email, hashedPassword);
        System.out.println(user);
        System.out.println(email);
        System.out.println(hashedPassword);
        if (user == null) {
            return null;
        }
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
                    hashPassword(userDto.password()),
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
        } else {
            userDto = new UserDto(
                    userDto.id(),
                    userDto.email(),
                    hashPassword(userDto.password()),
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
                    userDto.role()
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
        existingUser.setPassword(hashPassword(userDto.password()));
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
            String hashedPassword = hashPassword(password);
            UserDto userDto = new UserDto(
                    null,
                    email,
                    hashedPassword,
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
            SendEmail.sendNewAccountEmail(email, password); // Send plain text password
            return UserMapper.toDto(savedUser);
        }).collect(Collectors.toList());
    }

    private String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private final Path rootLocation = Paths.get("uploads");

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!", e);
        }
    }

    public String saveFile(MultipartFile file) {
        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("FAIL!", e);
        }
    }

    public UserDto updateProfilePicture(Long userId, MultipartFile file) {
        System.out.println("Updating profile picture");
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        String filename = saveFile(file);
        user.setProfilePicturePath("/uploads/" + filename);
        System.out.println(user);
        userRepository.save(user);
        return UserMapper.toDto(user); // Return the updated user
    }

//    public UserDto updateHeaderImage(Long userId, MultipartFile file) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        String filename = saveFile(file);
//        user.setHeaderImagePath("/uploads/" + filename);
//        userRepository.save(user);
//        return UserMapper.toDto(user); // Return the updated user
//    }




}
