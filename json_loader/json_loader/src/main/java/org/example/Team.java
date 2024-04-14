package org.example;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Team {
    @JsonAlias({"home_team_id", "away_team_id", "id"})
    private Integer teamId;

    @JsonAlias({"home_team_name", "away_team_name", "name"})
    private String teamName;

    @JsonAlias({"home_team_gender", "away_team_gender"})
    private String teamGender;

    @JsonProperty("country")
    private Country country;

    public Integer getTeamId() { return teamId; }
    public void setTeamId(Integer teamId) { this.teamId = teamId; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getTeamGender() { return teamGender; }
    public void setTeamGender(String teamGender) { this.teamGender = teamGender; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
}
