package com.outpatient.service;

import com.outpatient.entity.Doctor;
import com.outpatient.entity.User;
import com.outpatient.repository.DoctorRepository;
import com.outpatient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired  
    private DoctorRepository doctorRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder interface

    // Generate a 4-digit OTP
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999
        return String.valueOf(otp);
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(false); // Initially not verified
        String otp = generateOtp(); // Generate OTP
        user.setOtp(otp); // Store OTP
        user.setOtpExpiration(System.currentTimeMillis() + 300000); // Set expiration for 5 minutes
        userRepository.save(user); // Save user with OTP

        // If the user is a doctor, also save the doctor information
        if ("DOCTOR".equalsIgnoreCase(user.getRole())) {
            Doctor doctor = new Doctor();
            doctor.setUsername(user.getUsername()); // Use the same username as the User entity
            doctor.setName(user.getUsername()); // Optionally set name to the username
            doctor.setSpecialization("General Practitioner"); // Example specialization
            doctor.setAvailable(true); // Set the doctor as available
            doctorRepository.save(doctor); // Save the Doctor entity
        }

        emailService.sendOtpEmail(user.getEmail(), user.getUsername(), otp); // Send OTP email
        return user;
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean verifyOtp(String username, String otp) {
        // Fetch the user
        User user = userRepository.findByUsername(username);

        // Check if the user exists
        if (user == null) {
            System.out.println("User not found for username: " + username);
            return false; // User does not exist
        }

        // Log the current state
        long currentTime = System.currentTimeMillis();
        System.out.println("Stored OTP: " + user.getOtp());
        System.out.println("Provided OTP: " + otp);
        System.out.println("Current Time: " + currentTime);
        System.out.println("OTP Expiration Time: " + user.getOtpExpiration());

        // Verify OTP and expiration
        if (user.getOtp() != null && user.getOtp().trim().equals(otp.trim()) && currentTime < user.getOtpExpiration()) {
            user.setVerified(true); // Mark user as verified
            user.setOtp(null); // Clear OTP
            user.setOtpExpiration(0); // Clear expiration
            userRepository.save(user); // Update user
            return true; // Successful verification
        }

        System.out.println("OTP verification failed for user: " + username);
        return false; // Invalid OTP or expired
    }
}