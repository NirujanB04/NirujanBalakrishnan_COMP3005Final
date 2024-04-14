package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Competition {
    @JsonProperty("competition_id")
    private int competitionId;

    @JsonProperty("season_id")
    private int seasonId;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("competition_name")
    private String competitionName;

    @JsonProperty("competition_gender")
    private String competitionGender;

    @JsonProperty("competition_youth")
    private boolean competitionYouth;

    @JsonProperty("competition_international")
    private boolean competitionInternational;

    @JsonProperty("season_name")
    private String seasonName;

    @JsonProperty("match_updated")
    private String matchUpdated;

    // Constructor
    public Competition() {
        // Default constructor
    }

    // Getters and Setters
    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getCompetitionGender() {
        return competitionGender;
    }

    public void setCompetitionGender(String competitionGender) {
        this.competitionGender = competitionGender;
    }

    public boolean isCompetitionYouth() {
        return competitionYouth;
    }

    public void setCompetitionYouth(boolean competitionYouth) {
        this.competitionYouth = competitionYouth;
    }

    public boolean isCompetitionInternational() {
        return competitionInternational;
    }

    public void setCompetitionInternational(boolean competitionInternational) {
        this.competitionInternational = competitionInternational;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getMatchUpdated() {
        return matchUpdated;
    }

    public void setMatchUpdated(String matchUpdated) {
        this.matchUpdated = matchUpdated;
    }
}
