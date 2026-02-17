package com.nischay.airpool.ride;

import com.nischay.airpool.domain.*;
import com.nischay.airpool.dto.*;
import com.nischay.airpool.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepo;
    private final UserRepository userRepo;
    private final BookingRepository bookingRepo;

    // CREATE RIDE
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

    // SEARCH RIDES
    public Page<Ride> search(String airport,
                             Instant start,
                             Instant end,
                             int seats,
                             Pageable pageable) {

        return rideRepo
                .findByAirportContainingIgnoreCaseAndDepartureTimeBetweenAndSeatsAvailableGreaterThan(
                        airport, start, end, seats, pageable);
    }

    // BOOK RIDE WITH LOCK
    @Transactional
    public Booking bookRide(BookRideRequest req) {

        Ride ride = rideRepo.findRideForUpdate(req.rideId);
        if (ride == null)
            throw new RuntimeException("Ride not found");

        if (ride.getSeatsAvailable() < req.seatCount)
            throw new RuntimeException("Not enough seats");

        if (ride.getLuggageCapacity() < req.luggageCount)
            throw new RuntimeException("Not enough luggage");

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

    //  CANCEL BOOKING (PUT BACK SEATS + LUGGAGE)
    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if ("CANCELLED".equals(booking.getStatus()))
            return;

        Ride ride = rideRepo.findRideForUpdate(
                booking.getRide().getId());

        ride.setSeatsAvailable(
                ride.getSeatsAvailable() + booking.getSeatCount());

        ride.setLuggageCapacity(
                ride.getLuggageCapacity() + booking.getLuggageCount());

        ride.setStatus("OPEN");
        booking.setStatus("CANCELLED");

        rideRepo.save(ride);
        bookingRepo.save(booking);
    }
}
