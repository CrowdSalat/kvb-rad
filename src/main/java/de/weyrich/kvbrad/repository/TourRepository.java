package de.weyrich.kvbrad.repository;

import de.weyrich.kvbrad.model.jpa.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
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

    // do not export create and delete over rest
    @Override
    @RestResource(exported = false)
    void deleteById(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(Tour var1);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> var1);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S var1);

    @Override
    @RestResource(exported = false)
    <S extends Tour> Iterable<S> saveAll(Iterable<S> var1);

}
