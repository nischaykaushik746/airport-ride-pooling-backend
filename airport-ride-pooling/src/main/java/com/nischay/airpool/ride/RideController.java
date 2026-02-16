package com.nischay.airpool.ride;

import com.nischay.airpool.domain.Booking;
import com.nischay.airpool.domain.Ride;
import com.nischay.airpool.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/create")
    public Ride create(@RequestBody CreateRideRequest req) {
        return rideService.createRide(req);
    }

    @PostMapping("/book")
    public Booking book(@RequestBody BookRideRequest req) {
        return rideService.bookRide(req);
    }
}
