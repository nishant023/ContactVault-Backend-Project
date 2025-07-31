package com.contactVault.ContactVault.ContactVault.services;

import com.contactVault.ContactVault.ContactVault.dtos.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    // Register new user
    UserDto registerUser(UserDto userDto);

    // Get user profile by ID
    UserDto getUserById(Long userId);

    // Update user profile
    UserDto updateProfile(String userEmail, UserDto updatedUserDto);

    // Change password
    void changePassword(String userEmail, String oldPassword, String newPassword);

    // Forgot password via email (send OTP)
    void sendOtpForPasswordReset(String email);

    // Verify OTP and reset password
    void resetPasswordUsingOtp(String email, String otp, String newPassword);

    // Authenticate user (Login)
    String loginUser(String email, String password); // or return JWT token

    // Logout (optional if JWT based, else invalidate session)
    void logoutUser(Long userId);













/*
    //create user
    UserDto createUser(UserDto userDto);

    //update user
    UserDto updateUser(UserDto userDto,String id);

    //delete user
    void deleteUser (String id)throws IOException;

    //get user by id
    UserDto getUserById(String id);

    //get user by email
    UserDto getUserByEmail(String email);

    //get user by name
    UserDto getUserByName(String name);

    //get all users
    List<UserDto> getAllUsers();

    //forgot password
*/

}
