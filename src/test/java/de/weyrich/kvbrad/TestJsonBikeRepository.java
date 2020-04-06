package de.weyrich.kvbrad;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.weyrich.kvbrad.controller.ScheduledTasks;
import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.nextbike.Place;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.repository.BikeRepository;
import de.weyrich.kvbrad.service.BikeHandlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@SpringBootTest
@Transactional
public class TestJsonBikeRepository {

    public static final String EXPECTED_ID = "123";
    public static final double EXTECTED_LAT = 50.930510;
    public static final double EXTECTED_LNG = 6.973100;

    @Autowired
    BikeHandlerService bikeHandlerService;

    @Autowired
    BikeRepository repository;
    Bike bike;

    @BeforeEach
    public void setUp() {
        bike = new Bike(EXPECTED_ID, EXTECTED_LAT, EXTECTED_LNG);
    }

    @Test
    public void saveBike() {
        Bike save = repository.save(bike);
        assertEquals(EXPECTED_ID, save.getId());
        assertEquals(EXTECTED_LAT, save.getLat());
        assertEquals(EXTECTED_LNG, save.getLng());
    }

    @Test
    public void scheduledDownload() throws InterruptedException {
        Thread.sleep(1000);
        final Iterable<Bike> all = repository.findAll();

        int count = 0;
        for (Bike bike : all) {
            count++;
            assertNotNull(bike.getId());
            assertThat(bike.getLat(), not(0.0));
            assertThat(bike.getLng(), not(0.0));
        }
        assertTrue(count > 2);
    }

    @Test
    public void saveToDatabase_withChanges() throws IOException {
        RootModel rootModel = loadLocalNextbikeJson();
        Place place = rootModel.getCountries()[0].getCities()[0].getPlaces()[3];
        final String bikeId = place.getBikeNumbers()[0];

        place.setLat(1.00);
        place.setLng(1.00);
        bikeHandlerService.saveToDatabase(rootModel);

        place.setLat(2.0);
        place.setLng(2.0);
        bikeHandlerService.saveToDatabase(rootModel);

        Optional<Bike> bike = repository.findById(bikeId);

        assertTrue(bike.isPresent());
        String actualId = bike.get().getId();
        double actualLat = bike.get().getLat();
        double actualLng = bike.get().getLng();
        assertEquals(bikeId, actualId);
        assertEquals(2.0, actualLat);
        assertEquals(2.0, actualLng);
    }

    private RootModel loadLocalNextbikeJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonFileStream = getClass().getClassLoader().getResourceAsStream("example.json");
        return mapper.readValue(jsonFileStream, RootModel.class);
    }
}
