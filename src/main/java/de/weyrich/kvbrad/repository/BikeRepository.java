package de.weyrich.kvbrad.repository;

import de.weyrich.kvbrad.model.jpa.Bike;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BikeRepository extends CrudRepository<Bike, String> {

    List<Bike> findByBikeIdOrderByCreationDateDesc(String bikeId);

    Optional<Bike> findTopByBikeIdOrderByCreationDateDesc(String bikeId);

}
