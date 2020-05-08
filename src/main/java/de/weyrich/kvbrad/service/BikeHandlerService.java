package de.weyrich.kvbrad.service;

import de.weyrich.kvbrad.aspepcts.ActivateProfiler;
import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.nextbike.JsonBike;
import de.weyrich.kvbrad.model.nextbike.Place;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.repository.BikeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BikeHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(BikeHandlerService.class);
    private final String url;
    private final RestTemplate restTemplate;
    private final BikeRepository bikeRepository;
    private final BikeMovementService bikeMovementService;
    private final Map<String, Bike> cacheLastPosition = new ConcurrentHashMap<>();

    public BikeHandlerService(RestTemplate restTemplate,
                              BikeRepository bikeRepository,
                              BikeMovementService bikeMovementService,
                              @Value("${api.nextbike.kvb.url}") String url) {
        this.restTemplate = restTemplate;
        this.bikeRepository = bikeRepository;
        this.bikeMovementService = bikeMovementService;
        this.url = url;
    }

    public RootModel downloadBikeDate() {
        return this.restTemplate.getForObject(this.url, RootModel.class);
    }

    @ActivateProfiler
    public void saveToDatabase(RootModel jsonModel) {
        final List<Bike> jpaModel = this.mapExternalToInternalModel(jsonModel);
        this.saveNewPositions(jpaModel);
    }

    private List<Bike> mapExternalToInternalModel(RootModel rootModel) {
        final Place[] places = rootModel.getCountries()[0].getCities()[0].getPlaces();
        List<Bike> bikes = new ArrayList<>();
        for (Place place : places) {
            final JsonBike[] bikeList = place.getBikeList();
            for (JsonBike jsonBike : bikeList) {
                final String bikeId = jsonBike.getNumber();
                final double lat = place.getLat();
                final double lng = place.getLng();
                final Bike bike = new Bike(bikeId, lat, lng);
                bikes.add(bike);
            }
        }
        return bikes;
    }

    private void saveNewPositions(List<Bike> bikes) {
        Map<String, Bike> movedBikes = new HashMap<>();
        Map<String, Bike> movedBikesOld = new HashMap<>();

        for (Bike bike : bikes) {
            final String bikeId = bike.getBikeId();
            Bike bikeLast = cacheLastPosition.get(bikeId);

            if (bikeLast == null) {
                final Optional<Bike> optBike = bikeRepository.findTopByBikeIdOrderByCreationDateDesc(bikeId);
                optBike.ifPresent(bike1 -> cacheLastPosition.put(bikeId, bike1));
                bikeLast = cacheLastPosition.get(bikeId);
            }

            if (isMoved(bike, bikeLast)) {
                movedBikes.put(bike.getBikeId(), bike);
                if (bikeLast != null) {
                    movedBikesOld.put(bikeLast.getBikeId(), bikeLast);
                }
            }
        }
        bikeRepository.saveAll(movedBikes.values());
        cacheLastPosition.putAll(movedBikes);

        bikeMovementService.notifyAboutMovements(movedBikes, movedBikesOld);
        this.logger.info("{} bikes were moved since last update.", movedBikes.size());
    }

    private boolean isMoved(Bike bikeCurrent, Bike bikeLast) {
        if (bikeLast == null) {
            return true;
        }
        return bikeCurrent.getLat() != bikeLast.getLat()
                || bikeCurrent.getLng() != bikeLast.getLng();
    }
}
