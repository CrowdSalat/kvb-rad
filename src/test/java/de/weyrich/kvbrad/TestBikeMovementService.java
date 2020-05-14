package de.weyrich.kvbrad;

import de.weyrich.kvbrad.model.graphhopper.Path;
import de.weyrich.kvbrad.model.graphhopper.Welcome;
import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.jpa.Tour;
import de.weyrich.kvbrad.repository.TourRepository;
import de.weyrich.kvbrad.service.BikeMovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestBikeMovementService {

    @Autowired
    private TourRepository tourRepo;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private BikeMovementService bikeMovementService;

    private Welcome grahphopperResponse = createGraphhopperResponse();

    @BeforeEach
    public void setUp() {
        tourRepo.deleteAll();

    }

    @Test
    public void handleMovement_fromTwoDifferentBikes_AbortCalc() {
        // GIVEN
        Bike bike = new Bike("123", 1.0, 1.0);
        Bike bikeOld = new Bike("234", 2.0, 2.0);

        // WHEN
        bikeMovementService.handleMovement(bike, bikeOld);

        // THEN
        final Iterable<Tour> all = tourRepo.findAll();
        assertThat(all, iterableWithSize(0));
    }

    @Test
    public void handleMovement_witValidData() {
        // GIVEN
        when(restTemplate.getForEntity(anyString(), eq(Welcome.class), any(), any(), any(), any()))
                .thenReturn(new ResponseEntity(this.grahphopperResponse, HttpStatus.OK));

        Bike bike = new Bike("123", 1.0, 1.0);
        Bike bikeOld = new Bike("123", 2.0, 2.0);

        // WHEN
        bikeMovementService.handleMovement(bike, bikeOld);

        // THEN
        final Iterable<Tour> all = tourRepo.findAll();
        assertThat(all, iterableWithSize(1));
        final Tour tour = all.iterator().next();
        assertThat(tour.getStartLat(), is(1.0));
        assertThat(tour.getStartLng(), is(1.0));
        assertThat(tour.getEndLat(), is(2.0));
        assertThat(tour.getEndLng(), is(2.0));
        assertThat(tour.getDistance(), not(0.0));
    }

    @Test
    public void handleMovement_withoutActualMovement() {
        // GIVEN
        when(restTemplate.getForEntity(anyString(), eq(Welcome.class), any(), any(), any(), any()))
                .thenReturn(new ResponseEntity(this.grahphopperResponse, HttpStatus.OK));
        Bike bike = new Bike("234", 1.0, 1.0);
        Bike bikeOld = new Bike("234", 1.0, 1.0);

        // WHEN
        bikeMovementService.handleMovement(bike, bikeOld);

        // THEN
        final Iterable<Tour> all = tourRepo.findAll();
        assertThat(all, iterableWithSize(0));
    }


    @Test
    public void handleMovement_withAPIError() {

    }


    private Welcome createGraphhopperResponse() {
        Welcome grahphopperResponse = new Welcome();
        Path path = new Path();
        path.setDistance(999.0);
        grahphopperResponse.setPaths(new Path[]{path});
        return grahphopperResponse;
    }

}
