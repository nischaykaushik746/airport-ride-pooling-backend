package com.nischay.airpool.repository;

import com.nischay.airpool.domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByStatus(String status);
}
