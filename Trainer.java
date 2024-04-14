package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Trainer {
    private Connection connection;

    public Trainer(Connection connection) {
        this.connection = connection;
    }


    // Other getter and setter methods omitted for brevity
    public int getTrainerID(String email) throws SQLException {
        int trainerID = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT TrainerID FROM Trainer WHERE Email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                trainerID = resultSet.getInt("TrainerID");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return trainerID;
    }
    
    // Check trainer availability for a specific date and time
    public boolean checkAvailability(String email, String date, String time) throws SQLException {
        boolean isAvailable = false;
        int trainerID = getTrainerID(email);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM TrainerSchedule WHERE TrainerID = ? AND Date = CAST(? AS DATE) AND Time = CAST(? AS TIME) AND Availability = true");
            preparedStatement.setInt(1, trainerID);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            isAvailable = resultSet.next(); // Trainer is available if a row with availability = true is returned
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return isAvailable;
    }
    
    

    // Add an entry to the trainer's schedule
   // Add an entry to the trainer's schedule
public void addScheduleEntry(String email, String date, String time, boolean availability) throws SQLException {
    try {
        // Retrieve the TrainerID based on the email
        int trainerID = getTrainerID(email);

        // Insert the schedule entry into the TrainerSchedule table
        PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO TrainerSchedule (TrainerID, Date, Time, Availability) VALUES (?, CAST(? AS DATE), CAST(? AS TIME), ?)");
        preparedStatement.setInt(1, trainerID);
        preparedStatement.setString(2, date);
        preparedStatement.setString(3, time);
        preparedStatement.setBoolean(4, availability);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}

    public void viewMemberProfile(String memberName) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Member WHERE Name = ?");
            preparedStatement.setString(1, memberName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Retrieve member details from the result set
                int memberID = resultSet.getInt("MemberID");
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone");
                double currentWeight = resultSet.getDouble("CurrentWeight");
                double weightGoal = resultSet.getDouble("WeightGoal");
                String healthMetrics = resultSet.getString("HealthMetrics");

                // Display member details
                System.out.println("Member ID: " + memberID);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.println("Current Weight: " + currentWeight);
                System.out.println("Weight Goal: " + weightGoal);
                System.out.println("Health Metrics: " + healthMetrics);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Method to insert a new trainer into the Trainer table
public void insertTrainer(String name, String email, String phone, String specialization) throws SQLException {
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Trainer (Name, Email, Phone, Specialization) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, specialization);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println("Trainer inserted successfully.");
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}

}
