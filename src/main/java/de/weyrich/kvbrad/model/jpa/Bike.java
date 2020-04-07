package de.weyrich.kvbrad.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Bikes")
public class Bike extends AbstractEntity {

    @Column(name = "bike_id")
    private String bikeId;

    @Column(name = "pos_latitude")
    private double lat;

    @Column(name = "pos_longitude")
    private double lng;

    protected Bike() {
    }

    public Bike(String bikeId, double lat, double lng) {
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

