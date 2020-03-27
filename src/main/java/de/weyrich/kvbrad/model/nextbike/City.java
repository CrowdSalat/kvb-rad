package de.weyrich.kvbrad.model.nextbike;

public class City {
    private long uid;
    private double lat;
    private double lng;
    private long zoom;
    private String mapsIcon;
    private String alias;
    private boolean cityBreak;
    private String name;
    private long numPlaces;
    private String refreshRate;
    private Bounds bounds;
    private long bookedBikes;
    private long setPointBikes;
    private long availableBikes;
    private boolean returnToOfficialOnly;
    private BikeTypes bikeTypes;
    private String website;
    private Place[] places;

    public long getUid() { return uid; }
    public void setUid(long value) { this.uid = value; }

    public double getLat() { return lat; }
    public void setLat(double value) { this.lat = value; }

    public double getLng() { return lng; }
    public void setLng(double value) { this.lng = value; }

    public long getZoom() { return zoom; }
    public void setZoom(long value) { this.zoom = value; }

    public String getMapsIcon() { return mapsIcon; }
    public void setMapsIcon(String value) { this.mapsIcon = value; }

    public String getAlias() { return alias; }
    public void setAlias(String value) { this.alias = value; }

    public boolean getCityBreak() { return cityBreak; }
    public void setCityBreak(boolean value) { this.cityBreak = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public long getNumPlaces() { return numPlaces; }
    public void setNumPlaces(long value) { this.numPlaces = value; }

    public String getRefreshRate() { return refreshRate; }
    public void setRefreshRate(String value) { this.refreshRate = value; }

    public Bounds getBounds() { return bounds; }
    public void setBounds(Bounds value) { this.bounds = value; }

    public long getBookedBikes() { return bookedBikes; }
    public void setBookedBikes(long value) { this.bookedBikes = value; }

    public long getSetPointBikes() { return setPointBikes; }
    public void setSetPointBikes(long value) { this.setPointBikes = value; }

    public long getAvailableBikes() { return availableBikes; }
    public void setAvailableBikes(long value) { this.availableBikes = value; }

    public boolean getReturnToOfficialOnly() { return returnToOfficialOnly; }
    public void setReturnToOfficialOnly(boolean value) { this.returnToOfficialOnly = value; }

    public BikeTypes getBikeTypes() { return bikeTypes; }
    public void setBikeTypes(BikeTypes value) { this.bikeTypes = value; }

    public String getWebsite() { return website; }
    public void setWebsite(String value) { this.website = value; }

    public Place[] getPlaces() { return places; }
    public void setPlaces(Place[] value) { this.places = value; }
}
