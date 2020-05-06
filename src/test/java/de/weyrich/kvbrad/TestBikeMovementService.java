package de.weyrich.kvbrad;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.jpa.Tour;
import de.weyrich.kvbrad.repository.TourRepository;
import de.weyrich.kvbrad.service.BikeMovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestBikeMovementService {

    @Autowired
    private BikeMovementService bikeMovementService;

    @Autowired
    private TourRepository tourRepo;

    @BeforeEach
    public void setUp() {
        tourRepo.deleteAll();
    }



    @Test
    public void handleMovement_witValidData(){
        // GIVEN
        Bike bike = new Bike("123", 1.0, 1.0);
        Bike bikeOld = new Bike("123",2.0, 2.0);

        // WHEN
        bikeMovementService.handleMovement(bike, bikeOld);

        // THEN
        final Iterable<Tour> all = tourRepo.findAll();
        assertThat(all, iterableWithSize(1));
        final Tour tour = all.iterator().next();
        assertThat(tour.getStartLat(), is(1.0) );
        assertThat(tour.getStartLng(), is(1.0) );
        assertThat(tour.getEndLat(), is(2.0) );
        assertThat(tour.getEndLng(), is(2.0) );
        assertThat(tour.getDistance(), not(0.0));
    }

    @Test
    public void handleMovement_withoutActualMovement(){
        // GIVEN
        Bike bike = new Bike("234", 1.0, 1.0);
        Bike bikeOld = new Bike("234",1.0, 1.0);

        // WHEN
        bikeMovementService.handleMovement(bike, bikeOld);

        // THEN
        final Iterable<Tour> all = tourRepo.findAll();
        assertThat(all, iterableWithSize(0));
    }
}
