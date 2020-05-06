package de.weyrich.kvbrad.service;

import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.jpa.Tour;
import de.weyrich.kvbrad.repository.BikeRepository;
import de.weyrich.kvbrad.repository.TourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class BikeMovementService {

    private final Logger logger = LoggerFactory.getLogger(BikeMovementService.class);

    public BikeRepository bikeRepo;
    public TourRepository tourRepo;

    public BikeMovementService(BikeRepository bikeRepo, TourRepository tourRepo) {
        this.bikeRepo = bikeRepo;
        this.tourRepo = tourRepo;
    }


    @Async
    public void notifyAboutMovements(Map<String, Bike> movedBikes, Map<String, Bike> movedBikesOld) {
        final Set<String> movedBikesBikeId = movedBikes.keySet();
        for (String movedBikeBikeId : movedBikesBikeId) {
            final Bike bike = movedBikes.get(movedBikeBikeId);
            final Bike bikeOld = movedBikesOld.get(movedBikeBikeId);
            if (bikeOld != null) {
                handleMovement(bike, bikeOld);
            }
        }
    }

    public void handleMovement(Bike bikeNew, Bike bikeOld) {
        if (bikeNew == null || bikeOld == null) {
            return;
        }

        if (!bikeNew.getBikeId().equals(bikeOld.getBikeId())) {
            logger.warn("Only one bike_id is expected but got two: {}, {}", bikeNew.getBikeId(), bikeOld.getBikeId());
            return;
        }

        double movedDistance = calc2dEuclideanDistance(bikeNew, bikeOld);
        if (movedDistance == 0) {
            return;
        }

        final Tour tour = createTour(bikeNew, bikeOld, movedDistance);
        tourRepo.save(tour);
    }

    private double calc2dEuclideanDistance(Bike bikeNew, Bike bikeOld) {
        double a = Math.abs(bikeNew.getLat() - bikeOld.getLat());
        double b = Math.abs(bikeNew.getLng() - bikeOld.getLng());
        return Math.sqrt((a * a) + (b * b));
    }


    private Tour createTour(Bike bikeNew, Bike bikeOld, double movedDistance) {
        Tour tour = new Tour(bikeNew.getBikeId(), movedDistance);
        tour.setStartLat(bikeNew.getLat());
        tour.setStartLng(bikeNew.getLng());
        tour.setEndLat(bikeOld.getLat());
        tour.setEndLng(bikeOld.getLng());
        return tour;
    }
}
