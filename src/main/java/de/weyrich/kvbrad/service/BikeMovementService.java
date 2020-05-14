package de.weyrich.kvbrad.service;

import de.weyrich.kvbrad.aspepcts.ActivateProfiler;
import de.weyrich.kvbrad.model.graphhopper.Welcome;
import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.jpa.Tour;
import de.weyrich.kvbrad.repository.BikeRepository;
import de.weyrich.kvbrad.repository.TourRepository;
import org.apache.lucene.util.SloppyMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

@Service
public class BikeMovementService {

    private final Logger logger = LoggerFactory.getLogger(BikeMovementService.class);

    private final RestTemplate template;
    private final BikeRepository bikeRepo;
    private final TourRepository tourRepo;

    private final String graphhopperUrl;

    public BikeMovementService(RestTemplate template,
                               BikeRepository bikeRepo,
                               TourRepository tourRepo,
                               @Value("${api.graphhopper.route.url}") String graphhopperUrl) {
        this.template = template;
        this.bikeRepo = bikeRepo;
        this.tourRepo = tourRepo;
        this.graphhopperUrl = graphhopperUrl;
    }


    @ActivateProfiler
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

        if(this.approximateDistance(bikeNew, bikeOld) < 5.0){
            logger.trace("Bike was not moved but it was probably a GPS correction.");
            bikeRepo.delete(bikeOld);
            return;
        }

        final Welcome welcome = loadRoute(bikeNew, bikeOld);
        double movedDistance = welcome.getPaths()[0].getDistance();
        String encodedWaypoints = welcome.getPaths()[0].getPoints();

        if (movedDistance < 5.0) {
            bikeRepo.delete(bikeOld);
            return;
        }

        final Tour tour = createTour(bikeNew, bikeOld, movedDistance, encodedWaypoints);
        tourRepo.save(tour);
        bikeRepo.delete(bikeOld);
    }

    /**
     * Impl of Haversine formula.
     */
    private double approximateDistance(Bike bike1, Bike bike2) {
        return SloppyMath.haversinMeters(bike1.getLat(), bike1.getLng(),bike2.getLat(), bike2.getLng());
    }

    private Welcome loadRoute(Bike bikeNew, Bike bikeOld) {
        final String query = "?point={latitude},{longitude}&point={latitude2},{longitude2}"+
                "&vehicle=bike&locale=de&instructions=false&details=street_name&points_encoded=true";


        final ResponseEntity<Welcome> forEntity = template.getForEntity(graphhopperUrl + query, Welcome.class,
                bikeOld.getLat(),
                bikeOld.getLng(),
                bikeNew.getLat(),
                bikeNew.getLng());

        if(forEntity.getStatusCode() != HttpStatus.OK ) {
            logger.warn("API call failed {}", forEntity.toString());
        }

        return forEntity.getBody();
    }

    private Tour createTour(Bike bikeNew, Bike bikeOld, double movedDistance, String encodedWaypoints) {
        Tour tour = new Tour(bikeNew.getBikeId(), movedDistance, encodedWaypoints);
        tour.setStartLat(bikeNew.getLat());
        tour.setStartLng(bikeNew.getLng());
        tour.setEndLat(bikeOld.getLat());
        tour.setEndLng(bikeOld.getLng());
        return tour;
    }
}
