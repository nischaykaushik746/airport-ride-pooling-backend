package com.nischay.airpool.pricing;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    @GetMapping("/calc")
    public double calculatePrice(
            @RequestParam double base,
            @RequestParam int passengers,
            @RequestParam double distance,
            @RequestParam int demand,
            @RequestParam int drivers
    ) {
        return pricingService.calculate(base, passengers, distance, demand, drivers);
    }
}
