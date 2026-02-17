package com.nischay.airpool.ride;

import com.nischay.airpool.domain.*;
import com.nischay.airpool.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;


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

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        rideService.cancelBooking(id);
        return "Booking Cancelled";
    }

    // SEARCH API
    @PostMapping("/search")
    public Page<Ride> search(@RequestBody SearchRequest req) {

        String sortField = "departureTime";
        Sort.Direction dir = Sort.Direction.ASC;

        if (req.sort != null && req.sort.contains(",")) {
            String[] parts = req.sort.split(",");
            sortField = parts[0];
            dir = parts[1].equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(req.page, req.size, Sort.by(dir, sortField));

        return rideService.search(req.airport, req.start, req.end, req.seats, pageable);
    }
}
