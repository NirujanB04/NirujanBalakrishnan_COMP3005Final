package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Manager {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nickname")
    private String nickname;  // nullable

    @JsonProperty("dob")
    private String dob;       // Date of Birth

    @JsonProperty("country")
    private Country country;  // Using a nested Country class for deserialization

    // Constructor
    public Manager() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

