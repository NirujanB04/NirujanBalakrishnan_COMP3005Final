package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TeamLineup {
    @JsonProperty("team_id")
    private int teamId;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("lineup")
    private List<Player> lineup;

    public TeamLineup() {}

    public TeamLineup(int teamId, String teamName, List<Player> lineup) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.lineup = lineup;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getLineup() {
        return lineup;
    }

    public void setLineup(List<Player> lineup) {
        this.lineup = lineup;
    }
}
