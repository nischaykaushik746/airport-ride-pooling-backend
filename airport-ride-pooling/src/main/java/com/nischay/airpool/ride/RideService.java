package com.nischay.airpool.ride;

import com.nischay.airpool.domain.*;
import com.nischay.airpool.dto.*;
import com.nischay.airpool.repository.*;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepo;
    private final UserRepository userRepo;
    private final BookingRepository bookingRepo;

    public Ride createRide(CreateRideRequest req) {

        Ride ride = Ride.builder()
                .airport(req.airport)
                .pickupLocation(req.pickupLocation)
                .dropLocation(req.dropLocation)
                .seatsAvailable(req.seatsAvailable)
                .luggageCapacity(req.luggageCapacity)
                .departureTime(req.departureTime)
                .price(req.price)
                .status("OPEN")
                .build();

        return rideRepo.save(ride);
    }

    @Transactional
    public Booking bookRide(BookRideRequest req) {

        Ride ride = rideRepo.findById(req.rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getSeatsAvailable() < req.seatCount)
            throw new RuntimeException("Not enough seats");

        if (ride.getLuggageCapacity() < req.luggageCount)
            throw new RuntimeException("Not enough luggage space");

        ride.setSeatsAvailable(ride.getSeatsAvailable() - req.seatCount);
        ride.setLuggageCapacity(ride.getLuggageCapacity() - req.luggageCount);

        if (ride.getSeatsAvailable() == 0)
            ride.setStatus("FULL");

        User user = userRepo.findById(req.userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = Booking.builder()
                .ride(ride)
                .user(user)
                .seatCount(req.seatCount)
                .luggageCount(req.luggageCount)
                .status("ACTIVE")
                .build();

        rideRepo.save(ride);
        return bookingRepo.save(booking);
    }
}
