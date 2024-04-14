package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Match {
    @JsonProperty("match_id")
    private int matchId;

    @JsonProperty("match_date")
    private String matchDate;

    @JsonProperty("kick_off")
    private String kickOff;

    @JsonProperty("home_team")
    private Team homeTeam;

    @JsonProperty("away_team")
    private Team awayTeam;

    @JsonProperty("home_score")
    private int homeScore;

    @JsonProperty("away_score")
    private int awayScore;

    @JsonProperty("match_status")
    private String matchStatus;

    @JsonProperty("match_week")
    private int matchWeek;

    @JsonProperty("competition")
    private Competition competition;

    @JsonProperty("season")
    private Season season;

    @JsonProperty("competition_stage")
    private CompetitionStage competitionStage;

    @JsonProperty("stadium")
    private Stadium stadium;

    @JsonProperty("referee_id")
    private Integer refereeId;

    // Constructors
    public Match() {}

    // Getters
    public int getMatchId() {
        return matchId;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public String getKickOff() {
        return kickOff;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public int getMatchWeek() {
        return matchWeek;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Season getSeason() {
        return season;
    }

    public CompetitionStage getCompetitionStage() {
        return competitionStage;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public Integer getRefereeId() {
        return refereeId;
    }

    // Setters
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public void setKickOff(String kickOff) {
        this.kickOff = kickOff;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public void setMatchWeek(int matchWeek) {
        this.matchWeek = matchWeek;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setCompetitionStage(CompetitionStage competitionStage) {
        this.competitionStage = competitionStage;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }
}
