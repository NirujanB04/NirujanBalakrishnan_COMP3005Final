package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Data_Map {

    private Connection connection;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Set<Integer> relevantCompetitionIds = new HashSet<>();
    private Set<Integer> relevantSeasonIds = new HashSet<>();
    private Set<Integer> validMatchIds = new HashSet<>();


    public Data_Map(Connection connection) {
        this.connection = connection;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void LoadID(String filePath) throws IOException {
        List<Competition> competitions = objectMapper.readValue(new File(filePath), new TypeReference<List<Competition>>() {
        });
        for (Competition comp : competitions) {
            if ("La Liga".equals(comp.getCompetitionName()) || "Premier League".equals(comp.getCompetitionName())) {
                relevantCompetitionIds.add(comp.getCompetitionId());
                relevantSeasonIds.add(comp.getSeasonId());
            }
        }
    }

    public void importCompetitions(String filePath) throws IOException, SQLException {
        List<Competition> competitions = objectMapper.readValue(new File(filePath), new TypeReference<List<Competition>>() {});
        String sqlCompetition = "INSERT INTO competitions (competition_id, country_name, competition_name, competition_gender, competition_youth, competition_international) VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (competition_id) DO NOTHING;";
        String sqlSeason = "INSERT INTO seasons (season_id, competition_id, season_name) VALUES (?, ?, ?) ON CONFLICT (season_id) DO NOTHING;";

        PreparedStatement pstmtComp = connection.prepareStatement(sqlCompetition);
        PreparedStatement pstmtSeason = connection.prepareStatement(sqlSeason);

        // Begin transaction
        connection.setAutoCommit(false);

        try {
            for (Competition competition : competitions) {
                if (relevantCompetitionIds.contains(competition.getCompetitionId())) {
                    // Insert into competitions table
                    pstmtComp.setInt(1, competition.getCompetitionId());
                    pstmtComp.setString(2, competition.getCountryName());
                    pstmtComp.setString(3, competition.getCompetitionName());
                    pstmtComp.setString(4, competition.getCompetitionGender());
                    pstmtComp.setBoolean(5, competition.isCompetitionYouth());
                    pstmtComp.setBoolean(6, competition.isCompetitionInternational());
                    pstmtComp.executeUpdate();

                    // Insert into seasons table
                    pstmtSeason.setInt(1, competition.getSeasonId());
                    pstmtSeason.setInt(2, competition.getCompetitionId());
                    pstmtSeason.setString(3, competition.getSeasonName());
                    pstmtSeason.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error importing competitions and seasons: " + e.getMessage());
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            pstmtComp.close();
            pstmtSeason.close();
        }
    }

    public void importMatches(String baseDir) throws IOException, SQLException {
        File baseDirectory = new File(baseDir);
        File[] competitionDirectories = baseDirectory.listFiles();
        if (competitionDirectories != null) {
            for (File competitionDir : competitionDirectories) {
                int competitionId = Integer.parseInt(competitionDir.getName());
                if (relevantCompetitionIds.contains(competitionId)) {
                    for (File seasonFile : competitionDir.listFiles()) {
                        if (seasonFile.isFile() && seasonFile.getName().endsWith(".json")) {
                            List<Match> matches = objectMapper.readValue(seasonFile, new TypeReference<List<Match>>() {});
                            for (Match match : matches) {
                                insertTeam(match.getHomeTeam());
                                insertTeam(match.getAwayTeam());

                                insertStadium(match.getStadium());
                                insertCompetitionStage(match.getCompetitionStage());
                                insertMatch(match);
                            }
                        }
                    }
                }
            }
        }
    }

    public void insertMatch(Match match) throws SQLException {
        String sql = "INSERT INTO matches (match_id, competition_id, season_id, home_team_id, away_team_id, match_date, home_score, away_score, match_status, match_week, competition_stage_id, stadium_id, referee_id, kick_off) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (match_id) DO NOTHING;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, match.getMatchId());
            pstmt.setInt(2, match.getCompetition().getCompetitionId());
            pstmt.setInt(3, match.getSeason().getSeasonId());
            pstmt.setInt(4, match.getHomeTeam().getTeamId());
            pstmt.setInt(5, match.getAwayTeam().getTeamId());
            pstmt.setString(6, match.getMatchDate());
            pstmt.setInt(7, match.getHomeScore());
            pstmt.setInt(8, match.getAwayScore());
            pstmt.setString(9, match.getMatchStatus());
            pstmt.setInt(10, match.getMatchWeek());
            pstmt.setInt(11, match.getCompetitionStage().getId());

            if (match.getStadium() != null && match.getStadium().getStadiumId() != null) {
                pstmt.setInt(12, match.getStadium().getStadiumId());
            } else {
                pstmt.setNull(12, Types.INTEGER);
            }

            if (match.getRefereeId() != null) {
                pstmt.setInt(13, match.getRefereeId());
            } else {
                pstmt.setNull(13, Types.INTEGER);
            }

            pstmt.setString(14, match.getKickOff());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                validMatchIds.add(match.getMatchId());
            }
        }
    }





    public void insertTeam(Team team) throws SQLException {
        insertCountry(team.getCountry());

        String sql = "INSERT INTO teams (team_id, team_name, team_gender, country_id) VALUES (?, ?, ?, ?) ON CONFLICT (team_id) DO NOTHING;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, team.getTeamId());
            pstmt.setString(2, team.getTeamName());
            pstmt.setString(3, team.getTeamGender());
            pstmt.setInt(4, team.getCountry().getId());
            pstmt.executeUpdate();
        }
    }

    public void insertCountry(Country country) throws SQLException {

        String sql = "INSERT INTO countries (country_id, country_name) VALUES (?, ?) ON CONFLICT (country_id) DO NOTHING;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, country.getId());
            pstmt.setString(2, country.getName());
            pstmt.executeUpdate();
        }
    }

    public void insertCompetitionStage(CompetitionStage compStage) throws SQLException {
        String sql = "INSERT INTO competition_stages (stage_id, name) VALUES (?, ?) ON CONFLICT (stage_id) DO NOTHING;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, compStage.getId());
            pstmt.setString(2, compStage.getName());
            pstmt.executeUpdate();
        }
    }

    public void insertStadium(Stadium stadium) throws SQLException {
        if (stadium == null) {
            return;
        }
        String sql = "INSERT INTO stadiums (stadium_id, name, country_id) VALUES (?, ?, ?) ON CONFLICT (stadium_id) DO NOTHING;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, stadium.getStadiumId());
            pstmt.setString(2, stadium.getName());
            pstmt.setInt(3, stadium.getCountry().getId());
            pstmt.executeUpdate();
        }
    }

    public void importEventsFromDirectory(String directoryPath, Connection conn) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath), "*.json")) {
            for (Path path : stream) {
                try {
                    String fileName = path.getFileName().toString();
                    int matchId = Integer.parseInt(fileName.substring(0, fileName.length() - 5));
                    if (validMatchIds.contains(matchId)) {
                        List<Event> events = readEventsFromFile(path);
                        for (Event event : events) {
                            System.out.println(matchId);
                            if (event.getPlayer() != null) {
                                ensurePlayerExists(conn, event.getPlayer());
                            }

                            if (event.getPosition() != null) {
                                ensurePositionExists(conn, event.getPosition());
                            }

                            insertEvent(conn, event);

                            if (event.getPass() != null) {
                                if (event.getPass().getRecipient() != null) {
                                    ensurePlayerExists(conn, new Player(event.getPass().getRecipient().getId(), event.getPass().getRecipient().getName(), null, 0));
                                }
                                insertPass(conn, event.getEventId(), event.getPass());
                            }


                        }
                    }
                } catch (NumberFormatException | IOException e) {
                    System.err.println("Failed to process events from file " + path + ": " + e.getMessage());
                } catch (SQLException e) {
                    System.err.println("SQL Exception: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void ensurePositionExists(Connection conn, Position position) throws SQLException {
        if (position == null) return; // If no position, nothing to do.

        String sqlCheck = "SELECT 1 FROM positions WHERE id = ?";
        String sqlInsert = "INSERT INTO positions (id, name) VALUES (?, ?) ON CONFLICT (id) DO NOTHING;";

        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
            pstmtCheck.setInt(1, position.getId());
            ResultSet rs = pstmtCheck.executeQuery();
            if (!rs.next()) {
                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                    pstmtInsert.setInt(1, position.getId());
                    pstmtInsert.setString(2, position.getName());
                    pstmtInsert.executeUpdate();
                }
            }
        }
    }

    private List<Event> readEventsFromFile(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return objectMapper.readValue(reader, new TypeReference<List<Event>>() {});
        }
    }

    public void insertEvent(Connection conn, Event event) throws SQLException {
        String sql = "INSERT INTO events (event_id, index, period, timestamp, minute, second, type_id, type_name, possession, possession_team_id, play_pattern_id, play_pattern_name, team_id, player_id, position_id, location_x, location_y, duration, tactics_formation, under_pressure) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getEventId());
            pstmt.setInt(2, event.getIndex());
            pstmt.setInt(3, event.getPeriod());
            String timestampStr = event.getTimestamp();
            Time timestamp = convertStringToTime(timestampStr);
            pstmt.setTime(4, timestamp);
            pstmt.setInt(5, event.getMinute());
            pstmt.setInt(6, event.getSecond());
            pstmt.setInt(7, event.getType().getId());
            pstmt.setString(8, event.getType().getName());
            pstmt.setInt(9, event.getPossession());
            // Check and set possession_team_id
            setIntegerWithNullCheck(pstmt, 10, event.getPossessionTeam() != null ? event.getPossessionTeam().getTeamId() : null);

            pstmt.setInt(11, event.getPlayPattern().getId());
            pstmt.setString(12, event.getPlayPattern().getName());

            // Check and set team_id
            setIntegerWithNullCheck(pstmt, 13, event.getTeam() != null ? event.getTeam().getTeamId() : null);

            // Handle player and position IDs
            setIntegerWithNullCheck(pstmt, 14, event.getPlayer() != null ? event.getPlayer().getId() : null);
            setIntegerWithNullCheck(pstmt, 15, event.getPosition() != null ? event.getPosition().getId() : null);

            // Handle location
            if (event.getLocation() != null && event.getLocation().size() == 2) {
                pstmt.setDouble(16, event.getLocation().get(0));
                pstmt.setDouble(17, event.getLocation().get(1));
            } else {
                pstmt.setNull(16, Types.DOUBLE);
                pstmt.setNull(17, Types.DOUBLE);
            }

            pstmt.setDouble(18, event.getDuration() != null ? event.getDuration() : 0.0);
            setIntegerWithNullCheck(pstmt, 19, event.getTactics() != null ? event.getTactics().getFormation() : null);
            pstmt.setBoolean(20, event.isUnderPressure());

            pstmt.executeUpdate();
        }

        if (event.getPass() != null) {
            insertPass(conn, event.getEventId(), event.getPass());
        }

        if (event.getShot() != null) {
            insertShot(conn, event.getEventId(), event.getShot());
        }
    }
    private void setIntegerWithNullCheck(PreparedStatement pstmt, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            pstmt.setInt(parameterIndex, value);
        } else {
            pstmt.setNull(parameterIndex, Types.INTEGER);
        }
    }

    private boolean teamIdExists(Connection conn, int teamId) throws SQLException {
        String query = "SELECT 1 FROM teams WHERE team_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, teamId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    public void insertPass(Connection conn, String eventId, Event.Pass pass) throws SQLException {
        if (pass.getRecipient() != null) {
            ensurePlayerExists(conn, new Player(pass.getRecipient().getId(), pass.getRecipient().getName()));
        }

        String sql = "INSERT INTO passes (event_id, recipient_id, length, angle, height_id, end_location_x, end_location_y, body_part_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eventId);

            // Handling recipient ID
            if (pass.getRecipient() != null) {
                pstmt.setInt(2, pass.getRecipient().getId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }

            // Handling length
            if (pass.getLength() != null) {
                pstmt.setDouble(3, pass.getLength());
            } else {
                pstmt.setDouble(3, 0.0);
            }

            // Handling angle
            if (pass.getAngle() != null) {
                pstmt.setDouble(4, pass.getAngle());
            } else {
                pstmt.setDouble(4, 0.0);
            }

            // Handling height ID
            if (pass.getHeight() != null) {
                pstmt.setInt(5, pass.getHeight().getId());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }

            // Handling end location
            if (pass.getEndLocation() != null && pass.getEndLocation().size() == 2) {
                pstmt.setDouble(6, pass.getEndLocation().get(0));
                pstmt.setDouble(7, pass.getEndLocation().get(1));
            } else {
                pstmt.setNull(6, Types.DOUBLE);
                pstmt.setNull(7, Types.DOUBLE);
            }

            // Handling body part ID
            if (pass.getBodyPart() != null) {
                pstmt.setInt(8, pass.getBodyPart().getId());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }

            pstmt.executeUpdate();
        }
    }


    public void insertShot(Connection conn, String eventId, Event.Shot shot) throws SQLException {
        String sql = "INSERT INTO shots (event_id, statsbomb_xg, end_location_x, end_location_y, technique_id, body_part_id, outcome_id, type_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (event_id) DO NOTHING;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eventId);
            pstmt.setDouble(2, shot.getStatsbombXg());

            if (shot.getEndLocation() != null && shot.getEndLocation().size() >= 2) {
                pstmt.setDouble(3, shot.getEndLocation().get(0));
                pstmt.setDouble(4, shot.getEndLocation().get(1));
            } else {
                pstmt.setNull(3, Types.DOUBLE);
                pstmt.setNull(4, Types.DOUBLE);
            }

            if (shot.getTechnique() != null) {
                pstmt.setInt(5, shot.getTechnique().getId());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }

            if (shot.getBodyPart() != null) {
                pstmt.setInt(6, shot.getBodyPart().getId());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }

            if (shot.getOutcome() != null) {
                pstmt.setInt(7, shot.getOutcome().getId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }

            if (shot.getType() != null) {
                pstmt.setInt(8, shot.getType().getId());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("Inserting shot failed, no rows affected.");
            }
        }
    }




    public void importLineupsFromDirectory(String directoryPath) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath), "*.json")) {
            for (Path path : stream) {
                String fileName = path.getFileName().toString();
                try {
                    int matchId = Integer.parseInt(fileName.substring(0, fileName.length() - 5));
                    if (validMatchIds.contains(matchId)) {
                        List<TeamLineup> teamLineups = readLineupsFromFile(path);
                        processLineups(teamLineups, matchId);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing match ID from filename: " + fileName);
                } catch (SQLException e) {
                    System.err.println("SQL Exception: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private List<TeamLineup> readLineupsFromFile(Path filePath) throws IOException {
        return objectMapper.readValue(filePath.toFile(), new TypeReference<List<TeamLineup>>() {});
    }

    private void processLineups(List<TeamLineup> teamLineups, int matchId) throws SQLException {
        connection.setAutoCommit(false);
        try {
            for (TeamLineup teamLineup : teamLineups) {
                for (Player player : teamLineup.getLineup()) {
                    ensurePlayerExists(connection, player);
                    insertLineupData(matchId, teamLineup.getTeamId(), player, connection);
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            System.err.println("Transaction failed for match ID " + matchId + ": " + ex.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
    }
    private void insertLineupData(int matchId, int teamId, Player player, Connection conn) throws SQLException {
        String sql = "INSERT INTO lineups (match_id, team_id, player_id, position_id, jersey_number, from_time, to_time, from_period, to_period, start_reason, end_reason) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (match_id, player_id, position_id) DO UPDATE SET " +
                "from_time = EXCLUDED.from_time, to_time = EXCLUDED.to_time, from_period = EXCLUDED.from_period, to_period = EXCLUDED.to_period, " +
                "start_reason = EXCLUDED.start_reason, end_reason = EXCLUDED.end_reason;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Position position : player.getPosition()) {
                pstmt.setInt(1, matchId);
                pstmt.setInt(2, teamId);
                pstmt.setInt(3, player.getId());
                pstmt.setInt(4, position.getId());
                pstmt.setInt(5, player.getJerseyNumber());
                pstmt.setString(6, position.getFromTime());
                pstmt.setString(7, position.getToTime());
                pstmt.setObject(8, position.getFromPeriod());
                pstmt.setObject(9, position.getToPeriod());
                pstmt.setString(10, position.getStartReason());
                pstmt.setString(11, position.getEndReason());
                pstmt.executeUpdate();
            }
        }
    }


    public void ensurePlayerExists(Connection conn, Player player) throws SQLException {
        if (player == null || player.getId() == null) return;

        String sqlCheck = "SELECT 1 FROM players WHERE player_id = ?";
        String sqlInsert = "INSERT INTO players (player_id, name) VALUES (?, ?) ON CONFLICT (player_id) DO NOTHING;";

        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
            pstmtCheck.setInt(1, player.getId());
            ResultSet rs = pstmtCheck.executeQuery();
            if (!rs.next()) {  // If player does not exist, insert new player
                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                    pstmtInsert.setInt(1, player.getId());
                    pstmtInsert.setString(2, player.getName());
                    pstmtInsert.executeUpdate();
                }
            }
        }
    }



    public void insertPosition(Connection conn, Position position) throws SQLException {
        if (position == null || position.getId() == null) return;

        String sql = "INSERT INTO positions (id, name) VALUES (?, ?) ON CONFLICT (id) DO NOTHING;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, position.getId());
            pstmt.setString(2, position.getName());
            pstmt.executeUpdate();
        }
    }


    public Time convertStringToTime(String timestampStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
            long ms = dateFormat.parse(timestampStr).getTime();
            return new Time(ms);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
