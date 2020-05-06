package de.weyrich.kvbrad.repository;

import de.weyrich.kvbrad.model.jpa.Tour;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, String> {

}
