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
            final double lat = place.getLat();
            final double lng = place.getLng();
            for (JsonBike jsonBike : bikeList) {
                final Bike bike = new Bike(jsonBike.getNumber(), lat, lng);
                bikes.add(bike);
            }
        }
        this.bikeRepository.saveAll(bikes);
    }
}
