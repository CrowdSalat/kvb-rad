package de.weyrich.kvbrad.controller;

import de.weyrich.kvbrad.model.dto.BikeDTO;
import de.weyrich.kvbrad.model.jpa.Bike;
import de.weyrich.kvbrad.model.nextbike.RootModel;
import de.weyrich.kvbrad.service.BikeHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("bikes")
public class BikeController {

    private BikeHandlerService bikeService;

    @Autowired
    public BikeController(BikeHandlerService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    public CollectionModel<BikeDTO> getCurrentPosition() {
        final RootModel rootModel = bikeService.downloadBikeDate();
        final List<Bike> bikes = bikeService.mapExternalToInternalModel(rootModel);
        final List<BikeDTO> bikesDTOs = new ArrayList<>();
        bikes.forEach(bike -> bikesDTOs.add(new BikeDTO(bike)));
        Link selfLink = linkTo(BikeController.class).withSelfRel();
        CollectionModel<BikeDTO> result = new CollectionModel<>(bikesDTOs, selfLink);
        return result;
    }
}
