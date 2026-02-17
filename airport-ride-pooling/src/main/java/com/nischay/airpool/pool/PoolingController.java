package com.nischay.airpool.pool;

import com.nischay.airpool.domain.PoolGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pool")
@RequiredArgsConstructor
public class PoolingController {

    private final PoolingService service;

    @PostMapping("/{rideId}")
    public PoolGroup pool(@PathVariable Long rideId) {
        return service.createPool(rideId);
    }
}
