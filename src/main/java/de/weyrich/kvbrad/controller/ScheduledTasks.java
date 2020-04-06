package de.weyrich.kvbrad.controller;

import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.repository.BikeRepository;
import de.weyrich.kvbrad.service.BikeHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final BikeHandlerService bikeHandlerService;

    @Autowired
    public ScheduledTasks(BikeHandlerService bikeHandlerService) {
        this.bikeHandlerService = bikeHandlerService;
    }

    @Scheduled(cron = "* * * * * *")
    public void scheduleDownloadBikeData() {
        logger.debug("download");
        RootModel rootModel = bikeHandlerService.downloadBikeDate();
        bikeHandlerService.saveToDatabase(rootModel);
    }

}
