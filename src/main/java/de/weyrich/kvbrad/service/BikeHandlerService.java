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

import javax.annotation.PostConstruct;
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

    @ActivateProfiler
    @PostConstruct
    public void initCache(){
        try {
            for (Bike bike : bikeRepository.findAllByOrderByCreationDateAsc()) {
                cacheLastPosition.put(bike.getBikeId(), bike);
            }
        } catch (Exception e) {
            logger.error("Could not initiate cache on bean startup with bulk operation. " +
                    "Now every cache entry need to be filled sequentially which might take long.");
        }
    }

    public RootModel downloadBikeDate() {
        return this.restTemplate.getForObject(this.url, RootModel.class);
    }

    @ActivateProfiler
    public void saveToDatabase(RootModel jsonModel) {
        final List<Bike> jpaModel = this.mapExternalToInternalModel(jsonModel);
        this.saveNewPositions(jpaModel);
    }

    public List<Bike> mapExternalToInternalModel(RootModel rootModel) {
        logger.debug("Map internal to external model");
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
        logger.debug("Check if bikes have new position. Number of bikes: {}", bikes.size());

        Map<String, Bike> movedBikes = new HashMap<>();
        Map<String, Bike> movedBikesOld = new HashMap<>();

        for (Bike bike : bikes) {
            logger.trace("Try to load bike {} from cache", bike.getBikeId());

            final String bikeId = bike.getBikeId();
            Bike bikeLast = cacheLastPosition.get(bikeId);

            if (bikeLast == null) {
                logger.trace("Cache miss. Load bike {} to cache", bike.getBikeId());

                final Optional<Bike> optBike = bikeRepository.findTopByBikeIdOrderByCreationDateDesc(bikeId);
                optBike.ifPresent(bike1 -> cacheLastPosition.put(bikeId, bike1));
                bikeLast = cacheLastPosition.get(bikeId);
            }

            if (isMoved(bike, bikeLast)) {
                logger.trace("Bike {} was moved", bike.getBikeId());

                movedBikes.put(bike.getBikeId(), bike);
                if (bikeLast != null) {
                    movedBikesOld.put(bikeLast.getBikeId(), bikeLast);
                }
            }
        }
        logger.trace("Save {} moved bikes to db.", movedBikes.size());
        bikeRepository.saveAll(movedBikes.values());
        logger.trace("Put {} moved bikes to cache.", movedBikes.size());
        cacheLastPosition.putAll(movedBikes);
        logger.trace("Remove {} old bike positions from db.", movedBikesOld.size());
        bikeRepository.deleteAll(movedBikesOld.values());

        bikeMovementService.notifyAboutMovements(movedBikes, movedBikesOld);
        this.logger.info("{} have different lat and lng (maybe just gps inaccuracy)", movedBikes.size());
    }

    private boolean isMoved(Bike bikeCurrent, Bike bikeLast) {
        if (bikeLast == null) {
            return true;
        }
        return bikeCurrent.getLat() != bikeLast.getLat()
                || bikeCurrent.getLng() != bikeLast.getLng();
    }
}
