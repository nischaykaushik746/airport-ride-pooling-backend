package com.nischay.airpool.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "rides", indexes = {
        @Index(name = "idx_ride_airport", columnList = "airport"),
        @Index(name = "idx_ride_departure", columnList = "departureTime")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airport;
    private String pickupLocation;
    private String dropLocation;

    private int seatsAvailable;
    private int luggageCapacity;

    private Instant departureTime;
    private double price;

    private String status;
}
