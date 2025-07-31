package com.contactVault.ContactVault.ContactVault.services.Impl;

import com.contactVault.ContactVault.ContactVault.Helper.JwtHelper;
import com.contactVault.ContactVault.ContactVault.dtos.UserDto;
import com.contactVault.ContactVault.ContactVault.entities.User;
import com.contactVault.ContactVault.ContactVault.repositories.UserRepository;
import com.contactVault.ContactVault.ContactVault.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;
    private final JavaMailSender mailSender;
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto registerUser(UserDto userDto) {

//    Accept UserDto
//    Validate and save user
//    Return created UserDto (without password)
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // BCrypt hash

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String loginUser(String email, String password) {
//        Login:
//        Accept login request (email + password)
//        Verify user
//        Return success message or session/JWT if implemented


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Login attempt for: " + email);
        System.out.println("Password matches: " + passwordEncoder.matches(password, user.getPassword()));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }



        // Generate JWT token
        return jwtHelper.generateToken(email);
    }

    @Override
    public void logoutUser(Long userId) {
        // 1. Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. If you are storing tokens (e.g., in Redis or DB), invalidate here
        // Example: tokenRepository.deleteByUserId(userId);

        // 3. Optional: Set a field like user.setLoggedIn(false) and save if you track session state
        // Example:
        // user.setLoggedIn(false);
        // userRepository.save(user);

        System.out.println("User with ID " + userId + " logged out (placeholder logic)");

    }


    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateProfile(String userEmail, UserDto updatedUserDto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUserDto.getName());
        user.setMobile(updatedUserDto.getMobile());

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public void changePassword(String userEmail, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password does not match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void sendOtpForPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit

        otpStore.put(email, otp); // in real app, use Redis or DB

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP for Password Reset");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);

    }

    @Override
    public void resetPasswordUsingOtp(String email, String otp, String newPassword) {
        String storedOtp = otpStore.get(email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpStore.remove(email); // OTP used
    }




/*
    @Override
    public UserDto createUser(UserDto userDto) {
        String id = UUID.randomUUID().toString();
        userDto.setId(id);
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with given id"));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String id) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with given id"));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with this id!"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this name"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user -> modelMapper.map(user, UserDto.class))).collect(Collectors.toList());
    }

    @Override
    public UserDto register(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getUserById(Long userId) {
        return null;
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        return null;
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {

    }

    @Override
    public void sendOtpForPasswordReset(String email) {

    }

    @Override
    public void resetPasswordUsingOtp(String email, String otp, String newPassword) {

    }

    @Override
    public String loginUser(String email, String password) {
        return "";
    }

    @Override
    public void logoutUser(Long userId) {

    }
*/
}
