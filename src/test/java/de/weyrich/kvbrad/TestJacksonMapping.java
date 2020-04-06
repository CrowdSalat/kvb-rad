package de.weyrich.kvbrad;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import de.weyrich.kvbrad.controller.ScheduledTasks;
import de.weyrich.kvbrad.model.nextbike.City;
import de.weyrich.kvbrad.model.nextbike.Country;
import de.weyrich.kvbrad.model.nextbike.Place;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.service.BikeHandlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestJacksonMapping {

    @Autowired
    BikeHandlerService bikeHandlerService;

    @Test
    void downloadBikeData_shouldReturnAllFields() {
        final RootModel model = bikeHandlerService.downloadBikeDate();
        final Country[] countries = model.getCountries();
        assertNotNull(countries);

        final Country country = countries[0];
        final City[] cities = country.getCities();
        assertNotNull(cities);

        final City city = cities[0];
        final Place[] places = city.getPlaces();
        assertNotNull(places);

        int count = 0;
        for (Place place : places) {
            assertNotNull(places);
            assertNotNull(place.getBikeList());
            assertNotNull(place.getBikeNumbers());
            count++;
        }

        assertThat(count, is(greaterThan(300)));
    }

}
