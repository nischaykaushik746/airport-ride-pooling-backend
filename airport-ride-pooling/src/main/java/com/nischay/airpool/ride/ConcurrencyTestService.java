package com.nischay.airpool.ride;

import com.nischay.airpool.dto.BookRideRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ConcurrencyTestService {

    private final RideService rideService;

    public String test(Long rideId, Long userId, int threads) {

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    BookRideRequest req = new BookRideRequest();
                    req.setRideId(rideId);
                    req.setUserId(userId);
                    req.setSeatCount(1);
                    req.setLuggageCount(1);

                    rideService.bookRide(req);

                } catch (Exception ignored) {}
            });
        }

        executor.shutdown();
        return "Concurrency test started";
    }
}
