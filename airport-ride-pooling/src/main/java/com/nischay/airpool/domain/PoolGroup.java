package com.nischay.airpool.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pool_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoolGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    private int totalPassengers;
    private int totalLuggage;
    private double detourKm;
}
