package de.weyrich.kvbrad.controller;

import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.service.BikeHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    // in ms
    private static final int interval = 60 * 1000;

    private final BikeHandlerService bikeHandlerService;

    @Autowired
    public ScheduledTasks(BikeHandlerService bikeHandlerService) {
        this.bikeHandlerService = bikeHandlerService;
    }

    @Scheduled(fixedDelay = interval)
    public void scheduleDownloadBikeData() {
        logger.info("download");
        RootModel rootModel = bikeHandlerService.downloadBikeDate();
        bikeHandlerService.saveToDatabase(rootModel);
    }

}
