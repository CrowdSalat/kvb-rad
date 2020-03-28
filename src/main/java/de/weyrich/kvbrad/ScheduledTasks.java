package de.weyrich.kvbrad;


import de.weyrich.kvbrad.model.nextbike.RootModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    private final RestTemplate restTemplate;

    @Autowired
    public ScheduledTasks(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "* * * * * *")
    public void scheduleDownloadBikeData() {
        RootModel rootModel = this.downloadBikeDate();
        System.out.println(rootModel);
    }

    public RootModel downloadBikeDate() {
        String url = "https://api.nextbike.net/maps/nextbike-official.json?city=14";
        return this.restTemplate.getForObject(url, RootModel.class);
    }
}
