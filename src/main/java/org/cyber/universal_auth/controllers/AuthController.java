package org.cyber.universal_auth.controllers;

import org.cyber.universal_auth.dto.auth.LoginRequestDto;
import org.cyber.universal_auth.dto.auth.LoginResponseDto;
import org.cyber.universal_auth.models.User;
import org.cyber.universal_auth.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/auth/registration")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> registerUser(@RequestBody LoginRequestDto credentials){
        LoginResponseDto token = userService.loginUser(credentials);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
