package de.weyrich.kvbrad.service;

import de.weyrich.kvbrad.controller.ScheduledTasks;
import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.nextbike.JsonBike;
import de.weyrich.kvbrad.model.nextbike.Place;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BikeHandlerService {
    private final String url;
    private final RestTemplate restTemplate;
    private final BikeRepository bikeRepository;


    public BikeHandlerService(RestTemplate restTemplate,
                              BikeRepository bikeRepository,
                              @Value("${nextbike.api.kvb.url}") String url) {
        this.restTemplate = restTemplate;
        this.bikeRepository = bikeRepository;
        this.url = url;
    }

    public RootModel downloadBikeDate() {
        return this.restTemplate.getForObject(this.url, RootModel.class);
    }

    public void saveToDatabase(RootModel rootModel) {
        final Place[] places = rootModel.getCountries()[0].getCities()[0].getPlaces();
        List<Bike> bikes = new ArrayList<Bike>();
        for (Place place : places) {
            final JsonBike[] bikeList = place.getBikeList();
            for (JsonBike jsonBike : bikeList) {
                final String bikeId = jsonBike.getNumber();
                final double lat = place.getLat();
                final double lng = place.getLng();
                if (isMoved(bikeId, lat, lng)) {
                    final Bike bike = new Bike(bikeId, lat, lng);
                    bikes.add(bike);
                }
            }
        }
        this.bikeRepository.saveAll(bikes);
    }

    private boolean isMoved(String bikeId, double lat, double lng) {
        Optional<Bike> optBike = this.bikeRepository.findTopByBikeIdOrderByCreationDateDesc(bikeId);
        if (optBike.isPresent()) {
            Bike bike = optBike.get();
            double oldLat = bike.getLat();
            double oldLng = bike.getLng();
            return lat != oldLat || lng != oldLng;
        }
        return true;
    }
}
