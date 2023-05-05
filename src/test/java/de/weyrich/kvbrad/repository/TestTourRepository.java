package de.weyrich.kvbrad.repository;

import de.weyrich.kvbrad.model.jpa.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.iterableWithSize;

@DataJpaTest
public class TestTourRepository {

    @Autowired
    TourRepository tourRepository;

    @Test
    void customQuery_findToursBetweenDates_doesNotThrowException() throws ParseException {
        // given
        tourRepository.save(new Tour("123",2.0,"DUMMY"));
        tourRepository.save(new Tour("234",3.0,"DUMMY"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date start = dateFormat.parse("1970-05-01");
        Date end = dateFormat.parse("9999-05-01");

        // when
        List<Tour> toursBetweenDates = tourRepository.findToursBetweenDates(start, end);

        // then
        assertThat(toursBetweenDates, iterableWithSize(2));
    }

    @Test
    void customQuery_findToursBetweenDates_setSensibleDefaults() throws ParseException {
        // given
        tourRepository.save(new Tour("123",2.0,"DUMMY"));
        tourRepository.save(new Tour("234",3.0,"DUMMY"));
        Date start = null;
        Date end = null;
        //when
        List<Tour> toursBetweenDates = tourRepository.findToursBetweenDates(start, end);

        // then
        assertThat(toursBetweenDates, iterableWithSize(0));
    }

}
