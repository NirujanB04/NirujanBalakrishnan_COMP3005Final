package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Event {
    @JsonProperty("id")
    private String eventId;
    private int index;
    private int period;
    private String timestamp;
    private int minute;
    private int second;
    @JsonProperty("type")
    private EventType type;
    private int possession;
    @JsonProperty("possession_team")
    private Team possessionTeam;
    @JsonProperty("play_pattern")
    private PlayPattern playPattern;
    private Team team;
    private Player player;
    private Position position;
    private List<Double> location;
    private Double duration;
    private Tactics tactics;
    private boolean underPressure;
    private Shot shot;
    private Pass pass;

    public Event() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getPossession() {
        return possession;
    }

    public void setPossession(int possession) {
        this.possession = possession;
    }

    public Team getPossessionTeam() {
        return possessionTeam;
    }

    public void setPossessionTeam(Team possessionTeam) {
        this.possessionTeam = possessionTeam;
    }

    public PlayPattern getPlayPattern() {
        return playPattern;
    }

    public void setPlayPattern(PlayPattern playPattern) {
        this.playPattern = playPattern;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Tactics getTactics() {
        return tactics;
    }

    public void setTactics(Tactics tactics) {
        this.tactics = tactics;
    }

    public boolean isUnderPressure() {
        return underPressure;
    }

    public void setUnderPressure(boolean underPressure) {
        this.underPressure = underPressure;
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public static class Shot {
        @JsonProperty("statsbomb_xg")
        private double statsbombXg;
        @JsonProperty("end_location")
        private List<Double> endLocation;
        @JsonProperty("key_pass_id")
        private String keyPassId;
        private Technique technique;
        @JsonProperty("body_part")
        private BodyPart bodyPart;
        private Outcome outcome;
        private EventType type;
        private List<FreezeFrame> freezeFrame;

        public double getStatsbombXg() {
            return statsbombXg;
        }

        public void setStatsbombXg(double statsbombXg) {
            this.statsbombXg = statsbombXg;
        }

        public List<Double> getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(List<Double> endLocation) {
            this.endLocation = endLocation;
        }

        public String getKeyPassId() {
            return keyPassId;
        }

        public void setKeyPassId(String keyPassId) {
            this.keyPassId = keyPassId;
        }

        public Technique getTechnique() {
            return technique;
        }

        public void setTechnique(Technique technique) {
            this.technique = technique;
        }

        public BodyPart getBodyPart() {
            return bodyPart;
        }

        public void setBodyPart(BodyPart bodyPart) {
            this.bodyPart = bodyPart;
        }

        public Outcome getOutcome() {
            return outcome;
        }

        public void setOutcome(Outcome outcome) {
            this.outcome = outcome;
        }

        public EventType getType() {
            return type;
        }

        public void setType(EventType type) {
            this.type = type;
        }

        public List<FreezeFrame> getFreezeFrame() {
            return freezeFrame;
        }

        public void setFreezeFrame(List<FreezeFrame> freezeFrame) {
            this.freezeFrame = freezeFrame;
        }

        public static class Technique {
            private int id;
            private String name;

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
        }

        public static class BodyPart {
            private int id;
            private String name;

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
        }

        public static class Outcome {
            private int id;
            private String name;

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
        }

        public static class FreezeFrame {
            private List<Double> location;
            private Player player;
            private Position position;
            private boolean teammate;

            public List<Double> getLocation() {
                return location;
            }

            public void setLocation(List<Double> location) {
                this.location = location;
            }

            public Player getPlayer() {
                return player;
            }

            public void setPlayer(Player player) {
                this.player = player;
            }

            public Position getPosition() {
                return position;
            }

            public void setPosition(Position position) {
                this.position = position;
            }

            public boolean isTeammate() {
                return teammate;
            }

            public void setTeammate(boolean teammate) {
                this.teammate = teammate;
            }
        }
    }
    public static class Pass {
        private Recipient recipient;
        private Double length;
        private Double angle;
        private Height height;
        private List<Double> endLocation;
        private BodyPart bodyPart;

        public Recipient getRecipient() {
            return recipient;
        }

        public void setRecipient(Recipient recipient) {
            this.recipient = recipient;
        }

        public Double getLength() {
            return length;
        }

        public void setLength(Double length) {
            this.length = length;
        }

        public Double getAngle() {
            return angle;
        }

        public void setAngle(Double angle) {
            this.angle = angle;
        }

        public Height getHeight() {
            return height;
        }

        public void setHeight(Height height) {
            this.height = height;
        }

        public List<Double> getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(List<Double> endLocation) {
            this.endLocation = endLocation;
        }

        public BodyPart getBodyPart() {
            return bodyPart;
        }

        public void setBodyPart(BodyPart bodyPart) {
            this.bodyPart = bodyPart;
        }

        public static class Recipient {
            @JsonProperty("id")
            private Integer id;
            private String name;

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
        }

        public static class Height {
            @JsonProperty("id")
            private Integer id;
            private String name;

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
        }

        public static class BodyPart {
            @JsonProperty("id")
            private Integer id;
            private String name;

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
        }
    }


}
