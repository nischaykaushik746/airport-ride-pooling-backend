package com.nischay.airpool.auth;

import com.nischay.airpool.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody CreateUserRequest req) {
        return new AuthResponse(authService.register(req));
    }

    @PostMapping("/register-admin")
    public AuthResponse registerAdmin(@RequestBody CreateUserRequest req) {
        return new AuthResponse(authService.registerAdmin(req));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return new AuthResponse(authService.login(req));
    }
}
