package de.weyrich.kvbrad.model.dto;

import de.weyrich.kvbrad.model.jpa.Bike;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "items")
public class BikeDTO extends RepresentationModel<BikeDTO> {

    private String bikeId;

    private double lat;

    private double lng;

    public BikeDTO() {

    }

    public BikeDTO(Bike bike) {
        this.bikeId = bike.getBikeId();
        this.lat = bike.getLat();
        this.lng = bike.getLng();
    }

    public BikeDTO(String bikeId, double lat, double lng) {
        this.bikeId = bikeId;
        this.lat = lat;
        this.lng = lng;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
