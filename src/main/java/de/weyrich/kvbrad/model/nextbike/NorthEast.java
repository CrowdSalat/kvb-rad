package de.weyrich.kvbrad.model.nextbike;

public class NorthEast {
    private double lat;
    private double lng;

    public double getLat() { return lat; }
    public void setLat(double value) { this.lat = value; }

    public double getLng() { return lng; }
    public void setLng(double value) { this.lng = value; }

    @Override
    public String toString() {
        return "NorthEast{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
