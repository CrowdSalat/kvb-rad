package de.weyrich.kvbrad.repository;

import de.weyrich.kvbrad.model.jpa.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface TourRepository extends CrudRepository<Tour, String> {

    @Query("SELECT t FROM Tour t WHERE t.creationDate  <= :end AND t.creationDate >= :start")
    List<Tour> findToursBetweenDates(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("start") @Param("start") Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("end") @Param("end") Date endDate);

}
