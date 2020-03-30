package de.weyrich.kvbrad;

import static org.junit.jupiter.api.Assertions.*;

import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.repository.BikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestJsonBikeRepository {

    public static final String EXPECTED_ID = "123";
    public static final double EXTECTED_LAT = 50.930510;
    public static final double EXTECTED_LNG = 6.973100;

    @Autowired
    BikeRepository repository;
    Bike bike;

    @BeforeEach
    public void setUp() {
        bike = new Bike(EXPECTED_ID, EXTECTED_LAT, EXTECTED_LNG);
    }

    @Test
    public void test() {
        Bike save = repository.save(bike);
        assertEquals(EXPECTED_ID, save.getId());
        assertEquals(EXTECTED_LAT, save.getLat());
        assertEquals(EXTECTED_LNG, save.getLng());
    }

    @Test
    public void testSaveToDatabase() throws InterruptedException {
        Thread.sleep(1000);
        final Iterable<Bike> all = repository.findAll();

        int count = 0;
        for (Bike bike : all) {
            count++;
            assertNotNull(bike.getId());
            assertNotNull(bike.getLat());
            assertNotNull(bike.getLng());
        }
        assertTrue(count > 2);

    }

}
