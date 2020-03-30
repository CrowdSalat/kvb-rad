package de.weyrich.kvbrad;

import static org.junit.jupiter.api.Assertions.*;

import de.weyrich.kvbrad.model.nextbike.City;
import de.weyrich.kvbrad.model.nextbike.Country;
import de.weyrich.kvbrad.model.nextbike.Place;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KvbRadApplicationTests {

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Test
    void contextLoads() {
        final RootModel model = scheduledTasks.downloadBikeDate();
        final Country[] countries = model.getCountries();
        assertNotNull(countries);

        final Country country = countries[0];
        final City[] cities = country.getCities();
        assertNotNull(cities);

        final City city = cities[0];
        final Place[] places = city.getPlaces();
        assertNotNull(places);

        for (Place place : places) {
            assertNotNull(places);
            assertNotNull(place.getBikeList());
            assertNotNull(place.getBikeNumbers());
        }


    }

}
