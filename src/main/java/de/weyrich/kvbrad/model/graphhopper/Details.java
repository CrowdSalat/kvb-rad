package de.weyrich.kvbrad.model.graphhopper;

public class Details {
    private DetailsStreetName[] streetName;
    private Toll[] toll;
    private MaxSpeed[] maxSpeed;

    public DetailsStreetName[] getStreetName() { return streetName; }
    public void setStreetName(DetailsStreetName[] value) { this.streetName = value; }

    public Toll[] getToll() { return toll; }
    public void setToll(Toll[] value) { this.toll = value; }

    public MaxSpeed[] getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(MaxSpeed[] value) { this.maxSpeed = value; }
}
