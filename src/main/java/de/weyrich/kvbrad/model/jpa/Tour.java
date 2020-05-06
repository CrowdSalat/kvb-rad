package de.weyrich.kvbrad.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Tour")
public class Tour extends AbstractEntity {

    @Column(name = "bike_id")
    private String bikeId;
    @Column(name = "distance")
    private double distance;
    @Column(name="start_longitude")
    private double startLng;
    @Column(name="start_latitude")
    private double startLat;
    @Column(name="end_longitude")
    private double endLng;
    @Column(name="end_latitude")
    private double endLat;

    protected Tour() {
    }

    public Tour(String bikeId, double distance){
        this.bikeId = bikeId;
        this.distance=distance;
    }

    public Tour(String bikeId, double distance, double startLon, double startLat, double endLng, double endLat){
        this(bikeId, distance);
        this.startLng = startLon;
        this.startLat = startLat;
        this.endLng = endLng;
        this.endLat = endLat;
    }

    public double getDistance() {
        return distance;
    }

    public double getStartLng() {
        return startLng;
    }

    public void setStartLng(double startLng) {
        this.startLng = startLng;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getEndLng() {
        return endLng;
    }

    public void setEndLng(double endLng) {
        this.endLng = endLng;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }
}
