package com.example.YoungTalens.controller;

import com.example.YoungTalens.dto.FacultyDto;
import com.example.YoungTalens.dto.UserDto;
import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.repository.FacultyRepository;
import com.example.YoungTalens.service.FacultyService;
import com.example.YoungTalens.service.UserService;
import com.example.YoungTalens.util.SendEmail;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final FacultyService facultyService;
    private static final String DEFAULT_ROLE = "Simple User"; // Default role
    @Autowired
    private FacultyRepository facultyRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String token = RandomStringUtils.random(16, true, true);

        // Fetch or create Faculty
        Optional<Faculty> facultyOptional = facultyRepository.findByName(body.get("faculty"));
        Faculty faculty;
        if (facultyOptional.isPresent()) {
            faculty = facultyOptional.get();
        } else {
            faculty = new Faculty();
            faculty.setName(body.get("faculty"));
            faculty = facultyRepository.save(faculty);
        }

        FacultyDto facultyDto = new FacultyDto(faculty.getId(), faculty.getName(), null, null, null);

        UserDto userDTO = new UserDto(
                null,
                body.get("emailAddress"),
                body.get("password"),
                body.get("firstName"),
                body.get("lastName"),
                body.get("username"),
                body.get("actualJob"),
                body.get("actualCompany"),
                body.get("professionalDomain"),
                body.get("mentor"),
                token,
                facultyDto,
                body.get("yearOfStudy"),
                body.get("profilePicturePath"),
                null
        );

        userService.createUser(userDTO);
        SendEmail.sendConfirmationEmail(userDTO.email(), token);

        return ResponseEntity.ok().body("User created successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> body) {
        if (userService.getUserByEmailAddress(body.get("emailAddress")) == null) {
            return ResponseEntity.badRequest().body("* An account with this email does not exist");
        }

        UserDto userDto = userService.getUserByUsernameAndPassword(body.get("emailAddress"), body.get("password"));

        if (userDto == null) {
            return ResponseEntity.badRequest().body("* Incorrect email or password");
        }

        return ResponseEntity.ok().body(userDto.email());
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        UserDto userDto = userService.getUserByEmailAddress(email);
        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto) {
        FacultyDto faculty = facultyService.getFacultyByName(userDto.facultyDto().name());
        if (faculty == null) {
            faculty = new FacultyDto(null, userDto.facultyDto().name(), null, null, null);
            faculty = facultyService.save(faculty);
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
                faculty,
                userDto.yearOfStudy(),
                userDto.profilePicturePath(),
                userDto.role()
        );
        userService.updateUser(userDto);
        return ResponseEntity.ok().body("User updated successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PostMapping("/upload-profile-picture")
    public ResponseEntity<UserDto> uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            UserDto updatedUser = userService.updateProfilePicture(userId, file);
            return ResponseEntity.ok().body(updatedUser); // Return the updated user details
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Return null in case of failure
        }
    }

//    @PostMapping("/upload-header-image")
//    public ResponseEntity<String> uploadHeaderImage(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
//        try {
//            userService.updateHeaderImage(userId, file);
//            return ResponseEntity.ok().body("Header image updated successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Failed to update header image");
//        }
//    }
}
