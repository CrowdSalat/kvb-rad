package de.weyrich.kvbrad;

import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.nextbike.JsonBike;
import de.weyrich.kvbrad.model.nextbike.Place;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.repository.BikeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final RestTemplate restTemplate;
    private final BikeRepository bikeRepository;

    @Autowired
    public ScheduledTasks(RestTemplate restTemplate, BikeRepository bikeRepository) {
        this.restTemplate = restTemplate;
        this.bikeRepository = bikeRepository;
    }

    @Scheduled(cron = "* * * * * *")
    public void scheduleDownloadBikeData() {
        logger.debug("download");
        RootModel rootModel = this.downloadBikeDate();
        this.saveToDatabase(rootModel);
    }

    public RootModel downloadBikeDate() {
        String url = "https://api.nextbike.net/maps/nextbike-official.json?city=14";
        return this.restTemplate.getForObject(url, RootModel.class);
    }

    public void saveToDatabase(RootModel rootModel) {
        final Place[] places = rootModel.getCountries()[0].getCities()[0].getPlaces();
        for (Place place : places) {
            final JsonBike[] bikeList = place.getBikeList();
            final double lat = place.getLat();
            final double lng = place.getLng();
            if (bikeList.length > 1) {
                final Bike bike = new Bike(bikeList[0].getNumber(), lat, lng);
                bikeRepository.save(bike);
            }
        }
    }
}
