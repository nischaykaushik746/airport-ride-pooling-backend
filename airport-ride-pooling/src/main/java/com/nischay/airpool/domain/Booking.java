package com.nischay.airpool.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings", indexes = {
        @Index(name = "idx_booking_ride", columnList = "ride_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ride_id")
    private Ride ride;


    private int seatCount;
    private int luggageCount;

    private String status;
}
