package de.weyrich.kvbrad;

import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.nextbike.JsonBike;
import de.weyrich.kvbrad.model.nextbike.Place;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.repository.BikeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final RestTemplate restTemplate;
    private final BikeRepository bikeRepository;
    private final String url;

    @Autowired
    public ScheduledTasks(RestTemplate restTemplate,
                          BikeRepository bikeRepository,
                          @Value("${nextbike.api.kvb.url}") String url) {
        this.restTemplate = restTemplate;
        this.bikeRepository = bikeRepository;
        this.url = url;
    }

    @Scheduled(cron = "* * * * * *")
    public void scheduleDownloadBikeData() {
        logger.debug("download");
        RootModel rootModel = this.downloadBikeDate();
        this.saveToDatabase(rootModel);
    }

    public RootModel downloadBikeDate() {
        String url = this.url;
        return this.restTemplate.getForObject(url, RootModel.class);
    }

    public void saveToDatabase(RootModel rootModel) {
        final Place[] places = rootModel.getCountries()[0].getCities()[0].getPlaces();
        List<Bike> bikes = new ArrayList<>();
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
