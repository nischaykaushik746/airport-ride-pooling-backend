package com.nischay.airpool.repository;

import com.nischay.airpool.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRideAndStatus(Ride ride, String status);
}
