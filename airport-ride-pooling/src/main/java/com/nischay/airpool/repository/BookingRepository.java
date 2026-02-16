package com.nischay.airpool.repository;

import com.nischay.airpool.domain.Booking;
import com.nischay.airpool.domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRide(Ride ride);
}
