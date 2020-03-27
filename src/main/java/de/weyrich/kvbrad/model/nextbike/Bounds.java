package de.weyrich.kvbrad.model.nextbike;

public class Bounds {
    private NorthEast southWest;
    private NorthEast northEast;

    public NorthEast getSouthWest() { return southWest; }
    public void setSouthWest(NorthEast value) { this.southWest = value; }

    public NorthEast getNorthEast() { return northEast; }
    public void setNorthEast(NorthEast value) { this.northEast = value; }
}
