package de.weyrich.kvbrad.repository;

import de.weyrich.kvbrad.model.jpa.Bike;
import org.springframework.data.repository.CrudRepository;

public interface BikeRepository extends CrudRepository<Bike, String> {

}
