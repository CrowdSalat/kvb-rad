package de.weyrich.kvbrad.model.nextbike;

import java.util.Arrays;

public class Country {
    private double lat;
    private double lng;
    private long zoom;
    private String name;
    private String hotline;
    private String domain;
    private String language;
    private String email;
    private String timezone;
    private String currency;
    private String countryCallingCode;
    private String systemOperatorAddress;
    private String country;
    private String countryName;
    private String terms;
    private String policy;
    private String website;
    private boolean showBikeTypes;
    private boolean showBikeTypeGroups;
    private boolean showFreeRacks;
    private long bookedBikes;
    private long setPointBikes;
    private long availableBikes;
    private boolean cappedAvailableBikes;
    private boolean noRegistration;
    private String pricing;
    private City[] cities;

    public double getLat() { return lat; }
    public void setLat(double value) { this.lat = value; }

    public double getLng() { return lng; }
    public void setLng(double value) { this.lng = value; }

    public long getZoom() { return zoom; }
    public void setZoom(long value) { this.zoom = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getHotline() { return hotline; }
    public void setHotline(String value) { this.hotline = value; }

    public String getDomain() { return domain; }
    public void setDomain(String value) { this.domain = value; }

    public String getLanguage() { return language; }
    public void setLanguage(String value) { this.language = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String value) { this.timezone = value; }

    public String getCurrency() { return currency; }
    public void setCurrency(String value) { this.currency = value; }

    public String getCountryCallingCode() { return countryCallingCode; }
    public void setCountryCallingCode(String value) { this.countryCallingCode = value; }

    public String getSystemOperatorAddress() { return systemOperatorAddress; }
    public void setSystemOperatorAddress(String value) { this.systemOperatorAddress = value; }

    public String getCountry() { return country; }
    public void setCountry(String value) { this.country = value; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String value) { this.countryName = value; }

    public String getTerms() { return terms; }
    public void setTerms(String value) { this.terms = value; }

    public String getPolicy() { return policy; }
    public void setPolicy(String value) { this.policy = value; }

    public String getWebsite() { return website; }
    public void setWebsite(String value) { this.website = value; }

    public boolean getShowBikeTypes() { return showBikeTypes; }
    public void setShowBikeTypes(boolean value) { this.showBikeTypes = value; }

    public boolean getShowBikeTypeGroups() { return showBikeTypeGroups; }
    public void setShowBikeTypeGroups(boolean value) { this.showBikeTypeGroups = value; }

    public boolean getShowFreeRacks() { return showFreeRacks; }
    public void setShowFreeRacks(boolean value) { this.showFreeRacks = value; }

    public long getBookedBikes() { return bookedBikes; }
    public void setBookedBikes(long value) { this.bookedBikes = value; }

    public long getSetPointBikes() { return setPointBikes; }
    public void setSetPointBikes(long value) { this.setPointBikes = value; }

    public long getAvailableBikes() { return availableBikes; }
    public void setAvailableBikes(long value) { this.availableBikes = value; }

    public boolean getCappedAvailableBikes() { return cappedAvailableBikes; }
    public void setCappedAvailableBikes(boolean value) { this.cappedAvailableBikes = value; }

    public boolean getNoRegistration() { return noRegistration; }
    public void setNoRegistration(boolean value) { this.noRegistration = value; }

    public String getPricing() { return pricing; }
    public void setPricing(String value) { this.pricing = value; }

    public City[] getCities() { return cities; }
    public void setCities(City[] value) { this.cities = value; }

    @Override
    public String toString() {
        return "Country{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", zoom=" + zoom +
                ", name='" + name + '\'' +
                ", hotline='" + hotline + '\'' +
                ", domain='" + domain + '\'' +
                ", language='" + language + '\'' +
                ", email='" + email + '\'' +
                ", timezone='" + timezone + '\'' +
                ", currency='" + currency + '\'' +
                ", countryCallingCode='" + countryCallingCode + '\'' +
                ", systemOperatorAddress='" + systemOperatorAddress + '\'' +
                ", country='" + country + '\'' +
                ", countryName='" + countryName + '\'' +
                ", terms='" + terms + '\'' +
                ", policy='" + policy + '\'' +
                ", website='" + website + '\'' +
                ", showBikeTypes=" + showBikeTypes +
                ", showBikeTypeGroups=" + showBikeTypeGroups +
                ", showFreeRacks=" + showFreeRacks +
                ", bookedBikes=" + bookedBikes +
                ", setPointBikes=" + setPointBikes +
                ", availableBikes=" + availableBikes +
                ", cappedAvailableBikes=" + cappedAvailableBikes +
                ", noRegistration=" + noRegistration +
                ", pricing='" + pricing + '\'' +
                ", cities=" + Arrays.toString(cities) +
                '}';
    }
}
