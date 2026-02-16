package com.nischay.airpool.dto;

import lombok.Data;

@Data
public class BookRideRequest {
    public Long rideId;
    public Long userId;
    public int seatCount;
    public int luggageCount;
}
