package com.nischay.airpool.repository;

import com.nischay.airpool.domain.Ride;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

import java.time.Instant;

public interface RideRepository extends JpaRepository<Ride,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Ride r WHERE r.id=:id")
    Ride findRideForUpdate(@Param("id") Long id);

    Page<Ride> findByAirportContainingIgnoreCaseAndDepartureTimeBetweenAndSeatsAvailableGreaterThan(
            String airport,
            Instant start,
            Instant end,
            int seats,
            Pageable pageable
    );
}
