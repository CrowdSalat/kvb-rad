package de.weyrich.kvbrad.model.nextbike;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Arrays;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Place {
    private long uid;
    private double lat;
    private double lng;
    private boolean bike;
    private String name;
    private Object address;
    private boolean spot;
    private long number;
    private long bikes;
    private long bookedBikes;
    private long bikeRacks;
    private long freeRacks;
    private long specialRacks;
    private long freeSpecialRacks;
    private boolean maintenance;
    private TerminalType terminalType;
    private JsonBike[] bikeList;
    private String[] bikeNumbers;
    private PlaceBikeTypes bikeTypes;
    private String placeType;
    private boolean rackLocks;

    public long getUid() { return uid; }
    public void setUid(long value) { this.uid = value; }

    public double getLat() { return lat; }
    public void setLat(double value) { this.lat = value; }

    public double getLng() { return lng; }
    public void setLng(double value) { this.lng = value; }

    public boolean getBike() { return bike; }
    public void setBike(boolean value) { this.bike = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public Object getAddress() { return address; }
    public void setAddress(Object value) { this.address = value; }

    public boolean getSpot() { return spot; }
    public void setSpot(boolean value) { this.spot = value; }

    public long getNumber() { return number; }
    public void setNumber(long value) { this.number = value; }

    public long getBikes() { return bikes; }
    public void setBikes(long value) { this.bikes = value; }

    public long getBookedBikes() { return bookedBikes; }
    public void setBookedBikes(long value) { this.bookedBikes = value; }

    public long getBikeRacks() { return bikeRacks; }
    public void setBikeRacks(long value) { this.bikeRacks = value; }

    public long getFreeRacks() { return freeRacks; }
    public void setFreeRacks(long value) { this.freeRacks = value; }

    public long getSpecialRacks() { return specialRacks; }
    public void setSpecialRacks(long value) { this.specialRacks = value; }

    public long getFreeSpecialRacks() { return freeSpecialRacks; }
    public void setFreeSpecialRacks(long value) { this.freeSpecialRacks = value; }

    public boolean getMaintenance() { return maintenance; }
    public void setMaintenance(boolean value) { this.maintenance = value; }

    public TerminalType getTerminalType() { return terminalType; }
    public void setTerminalType(TerminalType value) { this.terminalType = value; }

    public JsonBike[] getBikeList() { return bikeList; }
    public void setBikeList(JsonBike[] value) { this.bikeList = value; }

    public String[] getBikeNumbers() { return bikeNumbers; }
    public void setBikeNumbers(String[] value) { this.bikeNumbers = value; }

    public PlaceBikeTypes getBikeTypes() { return bikeTypes; }
    public void setBikeTypes(PlaceBikeTypes value) { this.bikeTypes = value; }

    public String getPlaceType() { return placeType; }
    public void setPlaceType(String value) { this.placeType = value; }

    public boolean getRackLocks() { return rackLocks; }
    public void setRackLocks(boolean value) { this.rackLocks = value; }

    @Override
    public String toString() {
        return "Place{" +
                "uid=" + uid +
                ", lat=" + lat +
                ", lng=" + lng +
                ", bike=" + bike +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", spot=" + spot +
                ", number=" + number +
                ", bikes=" + bikes +
                ", bookedBikes=" + bookedBikes +
                ", bikeRacks=" + bikeRacks +
                ", freeRacks=" + freeRacks +
                ", specialRacks=" + specialRacks +
                ", freeSpecialRacks=" + freeSpecialRacks +
                ", maintenance=" + maintenance +
                ", terminalType=" + terminalType +
                ", bikeList=" + Arrays.toString(bikeList) +
                ", bikeNumbers=" + Arrays.toString(bikeNumbers) +
                ", bikeTypes=" + bikeTypes +
                ", placeType='" + placeType + '\'' +
                ", rackLocks=" + rackLocks +
                '}';
    }
}
