package com.nischay.airpool.auth;

import com.nischay.airpool.domain.User;
import com.nischay.airpool.dto.CreateUserRequest;
import com.nischay.airpool.dto.LoginRequest;
import com.nischay.airpool.repository.UserRepository;
import com.nischay.airpool.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    // REGISTER
    public String register(CreateUserRequest req) {

        if (userRepo.findByEmail(req.email).isPresent())
            throw new RuntimeException("Email already exists");

        User user = User.builder()
                .email(req.email)
                .password(encoder.encode(req.password))
                .role("ROLE_USER")
                .build();

        userRepo.save(user);

        return jwt.generateToken(user.getEmail(), user.getRole());
    }

    // LOGIN
    public String login(LoginRequest req) {

        User user = userRepo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.password, user.getPassword()))
            throw new RuntimeException("Wrong password");

        return jwt.generateToken(user.getEmail(), user.getRole());
    }

    //REGISTER-ADMIN
    public String registerAdmin(CreateUserRequest req) {

        if (userRepo.findByEmail(req.email).isPresent())
            throw new RuntimeException("Email already exists");

        User user = User.builder()
                .email(req.email)
                .password(encoder.encode(req.password))
                .role("ROLE_ADMIN")
                .build();

        userRepo.save(user);

        return jwt.generateToken(user.getEmail(), user.getRole());
    }

}
