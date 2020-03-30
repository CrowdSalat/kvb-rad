package de.weyrich.kvbrad.model.nextbike;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Bounds {
    private NorthEast southWest;
    private NorthEast northEast;

    public NorthEast getSouthWest() { return southWest; }
    public void setSouthWest(NorthEast value) { this.southWest = value; }

    public NorthEast getNorthEast() { return northEast; }
    public void setNorthEast(NorthEast value) { this.northEast = value; }

    @Override
    public String toString() {
        return "Bounds{" +
                "southWest=" + southWest +
                ", northEast=" + northEast +
                '}';
    }
}
