package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stadium {
    @JsonProperty("id")

    private Integer stadiumId;
    @JsonProperty("name")

    private String name;
    @JsonProperty("country")

    private Country country;

    public Stadium() {}

    public Integer getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
