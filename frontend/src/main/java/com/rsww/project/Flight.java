package com.rsww.project;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Flight {
    private String id;
    @NotEmpty
    private String fromCountry;
    @NotEmpty
    private String toCountry;
    @NotEmpty
    private String fromCity;
    @NotEmpty
    private String toCity;
    @NotNull
    private Integer price;

    public Flight() {
        // To avoid problem with conversion to JSON object
    }
    public Flight(String id, String fromCountry, String fromCity, String toCountry, String toCity, Integer price) {
        this.id = id;
        this.fromCountry = fromCountry;
        this.fromCity = fromCity;
        this.toCountry = toCountry;
        this.toCity = toCity;
        this.price = price;
    }
    public String getId() {
        return id;
    }
    public String getSourceCountry() {
        return fromCountry;
    }
    public String getSourceCity() {
        return fromCity;
    }
    public String getDestinationCountry() {
        return toCountry;
    }
    public String getDestinationCity() {
        return toCity;
    }
    public Integer getPrice() {
        return price;
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setSourceCountry(String country) {
        this.fromCountry = country;
    }
    public void setDestinationCountry(String country) {
        this.toCountry = country;
    }
    public void setDestinationCity(String city) {
        this.toCity = city;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public void setSourceCity(String city) {
        this.fromCity = city;
    }
}
