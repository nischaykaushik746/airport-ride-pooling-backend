package com.nischay.airpool.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class CreateRideRequest {
    public String airport;
    public String pickupLocation;
    public String dropLocation;
    public int seatsAvailable;
    public int luggageCapacity;
    public Instant departureTime;
    public double price;
}
