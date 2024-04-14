package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerPosition {
    @JsonProperty("position_id")
    private Integer id;

    @JsonProperty("position")
    private String name;

    @JsonProperty("from")
    private String fromTime;

    @JsonProperty("to")
    private String toTime;

    @JsonProperty("from_period")
    private Integer fromPeriod;

    @JsonProperty("to_period")
    private Integer toPeriod;

    @JsonProperty("start_reason")
    private String startReason;

    @JsonProperty("end_reason")
    private String endReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public Integer getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(Integer fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public Integer getToPeriod() {
        return toPeriod;
    }

    public void setToPeriod(Integer toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getStartReason() {
        return startReason;
    }

    public void setStartReason(String startReason) {
        this.startReason = startReason;
    }

    public String getEndReason() {
        return endReason;
    }

    public void setEndReason(String endReason) {
        this.endReason = endReason;
    }
}
