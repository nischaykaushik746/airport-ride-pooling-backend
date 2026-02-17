package com.nischay.airpool.pricing;

import org.springframework.stereotype.Service;

@Service
public class PricingService {

    public double calculate(double base,
                            int passengers,
                            double distance,
                            int demand,
                            int drivers) {

        double distanceCost = distance * 10;

        double poolingDiscount = passengers * 15;

        double surge = demand > drivers ? 1.3 : 1.0;

        double price = (base + distanceCost - poolingDiscount) * surge;

        return Math.max(price, base * 0.5); // minimum price protection
    }
}
