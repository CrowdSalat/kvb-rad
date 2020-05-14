package de.weyrich.kvbrad.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.nextbike.Place;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.repository.BikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class TestBikeHandlerService {

    @MockBean
    BikeMovementService bikeMovementService;

    @Autowired
    BikeHandlerService bikeHandlerService;

    @Autowired
    BikeRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void scheduledDownload() throws InterruptedException {
        Thread.sleep(5000);
        final Iterable<Bike> all = repository.findAll();

        for (Bike bike : all) {
            assertNotNull(bike.getId());
            assertThat(bike.getLat(), not(0.0));
            assertThat(bike.getLng(), not(0.0));
        }
        assertThat(all , iterableWithSize(greaterThan(500)));
    }

    @Test
    public void saveToDatabase_withBikeAndStation() throws IOException {
        // GIVEN
        RootModel rootModel = loadLocalNextbikeJson("one_bike_one_station.json");
        Place[] places = rootModel.getCountries()[0].getCities()[0].getPlaces();

        Place stationPlace = places[0];
        final int stationSize = stationPlace.getBikeList().length;
        final double stationLat = stationPlace.getLat();
        final double stationLng = stationPlace.getLng();

        Place bikePlace = places[1];
        final double bikeLat = bikePlace.getLat();
        final double bikeLng = bikePlace.getLng();


        // WHEN
        bikeHandlerService.saveToDatabase(rootModel);

        // THEN
        final Iterable<Bike> all = repository.findAll();
        assertThat(all, iterableWithSize(3));
        assertThat(all, hasItems(
                hasProperty("lat", is(stationLat)),
                hasProperty("lng", is(stationLng))
        ));
        assertThat(all, hasItems(
                hasProperty("lat", is(bikeLat)),
                hasProperty("lng", is(bikeLng))
        ));
    }

    @Test
    public void saveToDatabase_withChanges() throws IOException {
        // GIVEN
        RootModel rootModel = loadLocalNextbikeJson("one_bike.json");
        Place place = rootModel.getCountries()[0].getCities()[0].getPlaces()[0];
        final String bikeId = place.getBikeNumbers()[0];
        place.setLat(1.00);
        place.setLng(1.00);
        bikeHandlerService.saveToDatabase(rootModel);

        // WHEN
        place.setLat(2.0);
        place.setLng(2.0);
        bikeHandlerService.saveToDatabase(rootModel);


        // THEN
        List<Bike> allPositions = repository.findByBikeIdOrderByCreationDateDesc(bikeId);
        assertThat(allPositions.size(), is(2));

        Optional<Bike> bike = repository.findTopByBikeIdOrderByCreationDateDesc(bikeId);
        assertTrue(bike.isPresent());
        assertEquals(bikeId, bike.get().getBikeId());
        assertEquals(2.0, bike.get().getLat());
        assertEquals(2.0, bike.get().getLng());
    }

    @Test
    public void saveToDatabase_withChangesAndWithoutChanges() throws IOException {
        //GIVEN
        final double oldVal = 1.0;
        final double newVal = 2.0;

        RootModel rootModel = loadLocalNextbikeJson("two_bikes.json");
        Place[] places = rootModel.getCountries()[0].getCities()[0].getPlaces();

        Place placeWithChanges = places[0];
        placeWithChanges.setLng(oldVal);
        placeWithChanges.setLat(oldVal);

        Place placeWithoutChanges = places[1];
        placeWithoutChanges.setLng(oldVal);
        placeWithoutChanges.setLat(oldVal);
        bikeHandlerService.saveToDatabase(rootModel);

        //WHEN
        placeWithChanges.setLng(newVal);
        placeWithChanges.setLat(newVal);
        bikeHandlerService.saveToDatabase(rootModel);

        //THEN
        Iterable<Bike> all = this.repository.findAll();
        assertThat(all, iterableWithSize(3));
        assertThat(all, hasItems(
                hasProperty("lat", is(oldVal)),
                hasProperty("lng", is(oldVal))

        ));
        assertThat(all, hasItems(
                hasProperty("lat", is(newVal)),
                hasProperty("lng", is(newVal))

        ));
    }

    private RootModel loadLocalNextbikeJson(String name) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonFileStream = getClass().getClassLoader().getResourceAsStream(name);
        return mapper.readValue(jsonFileStream, RootModel.class);
    }
}
