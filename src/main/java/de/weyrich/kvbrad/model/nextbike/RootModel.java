package de.weyrich.kvbrad.model.nextbike;

import java.util.Arrays;

public class RootModel {
    private Country[] countries;

    public Country[] getCountries() {
        return countries;
    }

    public void setCountries(Country[] value) {
        this.countries = value;
    }

    @Override
    public String toString() {
        return "RootModel{" +
                "countries=" + Arrays.toString(countries) +
                '}';
    }
}