package de.weyrich.kvbrad.repository;

import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.jpa.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

public interface BikeRepository extends CrudRepository<Bike, String> {

    List<Bike> findByBikeIdOrderByCreationDateDesc(String bikeId);

    Optional<Bike> findTopByBikeIdOrderByCreationDateDesc(String bikeId);

    // do not export create and delete over rest
    @Override
    @RestResource(exported = false)
    void deleteById(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(Bike var1);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Bike> var1);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    <S extends Bike> S save(S var1);

    @Override
    @RestResource(exported = false)
    <S extends Bike> Iterable<S> saveAll(Iterable<S> var1);
}
