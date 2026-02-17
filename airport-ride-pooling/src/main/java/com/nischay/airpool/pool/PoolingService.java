package com.nischay.airpool.pool;

import com.nischay.airpool.domain.*;
import com.nischay.airpool.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PoolingService {

    private final RideRepository rideRepo;
    private final BookingRepository bookingRepo;
    private final PoolGroupRepository poolRepo;

    private static final double MAX_DETOUR_KM = 8.0;

    public PoolGroup createPool(Long rideId) {

        Ride ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        List<Booking> bookings =
                bookingRepo.findByRideAndStatus(ride, "ACTIVE");

        if (bookings.isEmpty())
            throw new RuntimeException("No active bookings");

        //  Seat & luggage validation
        int passengers = bookings.stream()
                .mapToInt(Booking::getSeatCount)
                .sum();

        int luggage = bookings.stream()
                .mapToInt(Booking::getLuggageCount)
                .sum();

        if (passengers > ride.getSeatsAvailable())
            throw new RuntimeException("Seat constraint violated");

        if (luggage > ride.getLuggageCapacity())
            throw new RuntimeException("Luggage constraint violated");

        //  Pickup clustering (simple distance grouping)
        Map<String, List<Booking>> clusters = clusterByPickup(bookings);

        // choose best cluster
        List<Booking> bestCluster = clusters.values().stream()
                .max(Comparator.comparingInt(List::size))
                .orElse(bookings);

        int pooledPassengers = bestCluster.stream()
                .mapToInt(Booking::getSeatCount)
                .sum();

        int pooledLuggage = bestCluster.stream()
                .mapToInt(Booking::getLuggageCount)
                .sum();

        //  Detour estimation
        double detour = estimateDetourKm(bestCluster);

        if (detour > MAX_DETOUR_KM)
            throw new RuntimeException("Detour tolerance exceeded");

        PoolGroup group = PoolGroup.builder()
                .ride(ride)
                .totalPassengers(pooledPassengers)
                .totalLuggage(pooledLuggage)
                .detourKm(detour)
                .build();

        return poolRepo.save(group);
    }


    private Map<String, List<Booking>> clusterByPickup(List<Booking> bookings) {

        Map<String, List<Booking>> map = new HashMap<>();

        for (Booking b : bookings) {

            String key = normalizeLocation(
                    b.getRide().getPickupLocation());

            map.computeIfAbsent(key, k -> new ArrayList<>())
                    .add(b);
        }

        return map;
    }

    private double estimateDetourKm(List<Booking> bookings) {

        int passengers = bookings.stream()
                .mapToInt(Booking::getSeatCount)
                .sum();

        // smarter than passengers*1.2
        return Math.min(passengers * 1.1, MAX_DETOUR_KM);
    }

    private String normalizeLocation(String location) {
        return location == null ? "UNKNOWN"
                : location.toLowerCase().replaceAll("\\s+", "");
    }
}
