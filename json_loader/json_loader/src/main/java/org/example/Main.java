package org.example;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting the data import process...");

        String URL = "jdbc:postgresql://localhost:5432/project_database";
        String USER = "postgres";
        String PASSWORD = "1234";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Data_Map dataMap = new Data_Map(connection);

            String competitionsPath = "C:\\Users\\Nirujan\\OneDrive\\Documents\\GitHub\\soccer_data\\data\\competitions.json";

            dataMap.LoadID(competitionsPath);
            System.out.println("Loaded competition and season IDs.");

            dataMap.importCompetitions(competitionsPath);
            System.out.println("Competitions imported successfully.");

            dataMap.importMatches("C:\\Users\\Nirujan\\OneDrive\\Documents\\GitHub\\soccer_data\\data\\matches");
            System.out.println("Matches imported successfully.");

            dataMap.importEventsFromDirectory("C:\\Users\\Nirujan\\OneDrive\\Documents\\GitHub\\soccer_data\\data\\events", connection);
            System.out.println("Events imported successfully.");

            dataMap.importLineupsFromDirectory("C:\\Users\\Nirujan\\OneDrive\\Documents\\GitHub\\soccer_data\\data\\lineups");
            System.out.println("Lineup data has been successfully imported.");


        } catch (SQLException ex) {
            System.err.println("Database connection failed: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("File reading failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
