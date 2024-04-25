package com.example.YoungTalens.controller;

import com.example.YoungTalens.dto.FacultyDto;
import com.example.YoungTalens.dto.UserDto;
import com.example.YoungTalens.service.FacultyService;
import com.example.YoungTalens.service.UserService;
import com.example.YoungTalens.util.SendEmail;
import com.example.YoungTalens.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserUtil userUtil;
    private final FacultyService facultyService;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String errorMessages = userUtil.validateUser(body.get("faculty"), body.get("emailAddress"), body.get("password"), body.get("confirmPassword"));
        System.out.println("testtttttttttttttttttttt");
      /*  if (!errorMessages.equals("ok,ok,ok")) {
            return ResponseEntity.badRequest().body(errorMessages);
        }*/

        String token = RandomStringUtils.random(16, true, true);
        boolean isConfirmed = false;
        Date createdAt = new Date();
        FacultyDto facultyDto = new FacultyDto(null, body.get("faculty"));

        facultyDto =  facultyService.createFaculty(facultyDto);
        UserDto userDTO = new UserDto(null, body.get("emailAddress"),body.get("password"), null,null,null,null, null, token, facultyDto,"2");

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
            return ResponseEntity.badRequest().body("* Incorrect password");
        }


        return ResponseEntity.ok().body(userDto.email());
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        UserDto userDto = userService.getUserByEmailAddress(email);
        System.out.println(userDto.email());

        return ResponseEntity.ok().body(userDto);
    }

}
