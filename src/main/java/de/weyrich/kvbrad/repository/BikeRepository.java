package de.weyrich.kvbrad.repository;

import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.jpa.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RestResource(exported = false)
public interface BikeRepository extends CrudRepository<Bike, String> {

    List<Bike> findAllByOrderByCreationDateAsc();

    List<Bike> findByBikeIdOrderByCreationDateDesc(String bikeId);

    Optional<Bike> findTopByBikeIdOrderByCreationDateDesc(String bikeId);

}
