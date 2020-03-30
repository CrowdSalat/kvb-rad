package de.weyrich.kvbrad.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Bikes")
public class Bike {

    @Id
    @Column(name = "bike_id")
    private String id;

    @Column(name = "pos_latitude")
    private double lat;

    @Column(name = "pos_longitude")
    private double lng;

    protected Bike() {
    }

    public Bike(String id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
