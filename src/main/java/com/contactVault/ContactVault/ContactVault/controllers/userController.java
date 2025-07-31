package com.contactVault.ContactVault.ContactVault.controllers;


import com.contactVault.ContactVault.ContactVault.dtos.*;
import com.contactVault.ContactVault.ContactVault.entities.User;
import com.contactVault.ContactVault.ContactVault.repositories.UserRepository;
import com.contactVault.ContactVault.ContactVault.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class userController {

    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto registeredUser = userService.registerUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto requestDto) {

        String token = userService.loginUser(requestDto.getEmail(), requestDto.getPassword());

        LoginResponseDto response = LoginResponseDto
                .builder()
                .message("Login Successful")
                .token(token)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/update-profile")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserDto userDto, Principal principal) {
        UserDto updated = userService.updateProfile(principal.getName(), userDto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request, Principal principal) {
        userService.changePassword(principal.getName(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.sendOtpForPasswordReset(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "OTP sent to email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPasswordUsingOtp(request.getEmail(), request.getOtp(), request.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id, Principal principal) {
        UserDto user = userService.getUserById(id);
        if (!user.getEmail().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.logoutUser(user.getId());
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }











/*
    //login user

    //sign up user

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String userId,
                                              @Valid @RequestBody UserDto userDto
    ) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{userId}")
    ResponseEntity<APIResponseMessage> deleteUser(@Valid @PathVariable("userId") String userId) throws IOException {
        userService.deleteUser(userId);
        APIResponseMessage apiResponseMessage = APIResponseMessage.builder()
                .message("User deleted Successfully")
                .success(true)
                .httpStatus(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }


    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@Valid @PathVariable("id") String id) {
        UserDto user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@Valid @PathVariable("email") String email) {
        UserDto user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/getUserByName/{name}")
    public ResponseEntity<UserDto> getUserByName(@Valid @PathVariable("name") String name) {
        UserDto user = userService.getUserByName(name);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    //forgot password

*/
}
