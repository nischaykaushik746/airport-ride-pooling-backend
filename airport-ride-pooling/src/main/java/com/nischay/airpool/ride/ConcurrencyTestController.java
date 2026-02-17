package com.nischay.airpool.ride;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class ConcurrencyTestController {

    private final ConcurrencyTestService service;

    @PostMapping("/concurrency")
    public String run(@RequestParam Long rideId,
                      @RequestParam Long userId,
                      @RequestParam(defaultValue = "10") int threads) {

        return service.test(rideId, userId, threads);
    }
}
