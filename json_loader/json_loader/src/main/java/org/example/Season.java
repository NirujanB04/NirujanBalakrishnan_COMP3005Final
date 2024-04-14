package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Season {
    @JsonProperty("season_id")
    private int seasonId;

    @JsonProperty("season_name")
    private String seasonName;

    public Season() {
    }

    public Season(int seasonId, String seasonName) {
        this.seasonId = seasonId;
        this.seasonName = seasonName;
    }

    // Getters and Setters
    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}
