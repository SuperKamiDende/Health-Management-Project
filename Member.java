package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Member {

    private Connection connection;


    public Member(Connection connection) {
        this.connection = connection;
    }
    public int getMemberID(String email) {
        int memberID = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT MemberID FROM Member WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                memberID = resultSet.getInt("MemberID");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberID;
    }
    
// Get the weight goal for the member associated with the provided email
public double getWeightGoal(String email) {
    double weightGoal = 0.0;
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT WeightGoal FROM Member WHERE Email = ?");
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            weightGoal = resultSet.getDouble("WeightGoal");
        }
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return weightGoal;
}

// Get the current weight for the member associated with the provided email
public double getCurrentWeight(String email) {
    double currentWeight = 0.0;
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT CurrentWeight FROM Member WHERE Email = ?");
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            currentWeight = resultSet.getDouble("CurrentWeight");
        }
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return currentWeight;
}


    // User Registration
    public void register(String name, String email, String phone, double currentWeight, double weightGoal, String healthMetrics) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Member (name, email, phone, currentWeight, weightGoal, healthMetrics) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setDouble(4, currentWeight);
            preparedStatement.setDouble(5, weightGoal);
            preparedStatement.setString(6, healthMetrics);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Registration successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Profile Management: Update personal information, fitness goals, and health metrics
    public void updateProfile(String email, double currentWeight, double weightGoal, String healthMetrics) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Member SET currentWeight = ?, weightGoal = ?, healthMetrics = ? WHERE email = ?");
            preparedStatement.setDouble(1, currentWeight);
            preparedStatement.setDouble(2, weightGoal);
            preparedStatement.setString(3, healthMetrics);
            preparedStatement.setString(4, email);
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully.");
            } else {
                System.out.println("No member found with email " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dashboard Display: Display fitness achievements and health statistics
    public void displayDashboard(String email) {
        double weightGoal = getWeightGoal(email);
        double currentWeight = getCurrentWeight(email);

        // System.out.println("Current Weight" + currentWeight);
        // System.out.println("Weight Goal" + weightGoal);
       

        if (weightGoal > currentWeight) {
            double weightToGain = weightGoal-currentWeight;
            System.out.println("You need to gain " + weightToGain + " pounds to reach your weight goal."); 
        } else if (weightGoal < currentWeight) {
            double weightToLose = currentWeight-weightGoal;
            System.out.println("You need to lose " + weightToLose + " pounds to reach your weight goal.");
        } else {
            System.out.println("You have reached your weight goal. Congratulations!");
        }
    }


    // Check if the specified trainer is available at the given date and time
    private boolean checkTrainerAvailability(int trainerID, String date, String time) {
    boolean isAvailable = false;
    try {
         // System.out.println("Checking availability for Trainer ID: " + trainerID + " at Date: " + date + " Time: " + time)
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM TrainerSchedule WHERE TrainerID = ? AND Date = CAST(? AS DATE) AND Time = CAST(? AS TIME) AND Availability = true");
        preparedStatement.setInt(1, trainerID);
        preparedStatement.setString(2, date);
        preparedStatement.setString(3, time);
        ResultSet resultSet = preparedStatement.executeQuery();
         // System.out.println("ResultSet:");
        // while (resultSet.next()) {
        //     System.out.println("Trainer ID: " + resultSet.getInt("TrainerID") +
        //                        ", Date: " + resultSet.getString("Date") +
        //                        ", Time: " + resultSet.getString("Time"));
        // }
        isAvailable = !resultSet.next(); // Trainer is available if no rows are returned
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return isAvailable;
}

public void scheduleSession(String email,int trainerID, String sessionType, String groupName, String date, String time, int duration, int maxCapacity) throws SQLException {
    // Retrieve the MemberID of the current member
    int memberID = getMemberID(email);
    
    // Check if the specified trainer is available
    boolean isTrainerAvailable = checkTrainerAvailability(trainerID, date, time);
    if (!isTrainerAvailable) {
        System.out.println("Trainer is not available at the specified date and time.");
        return;
    }
    
    // Send schedule request to administrative staff based on session type
    AdministrativeStaff administrativeStaff = new AdministrativeStaff(connection);
    if (sessionType.equalsIgnoreCase("personal")) {
        administrativeStaff.schedulePersonalTrainingSession(memberID, trainerID, date, time, duration);
    } else if (sessionType.equalsIgnoreCase("group")) {
        administrativeStaff.scheduleGroupFitnessClass(memberID, trainerID, groupName, date, time, duration, maxCapacity);
    } else {
        System.out.println("Invalid session type. Please specify 'personal' or 'group'.");
        return;
    }
}


}
