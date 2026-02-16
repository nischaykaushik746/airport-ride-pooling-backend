package com.nischay.airpool.auth;

import com.nischay.airpool.domain.User;
import com.nischay.airpool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userRepo.save(user);
    }
}
