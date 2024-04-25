package com.example.YoungTalens.util;

import com.example.YoungTalens.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserService userService;

    public boolean isEmailAddressValid(String emailAddress) {
        return emailAddress.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean isEmailAddressUnique(String emailAddress) {
        return userService.getUserByEmailAddress(emailAddress) == null;
    }

    public String validateUser(String username, String emailAddress, String password, String confirmPassword) {
        String errorMessages = "";
        if (!isEmailAddressValid(emailAddress)) {
            errorMessages += "* Invalid email address, ";
        } else if (!isEmailAddressUnique(emailAddress)) {
            errorMessages += "* An account with this email address already exists, ";
        } else {
            errorMessages += "ok,";
        }

        return errorMessages;
    }
}