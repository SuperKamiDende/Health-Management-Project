package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassSchedule {
    private Connection connection;

    public ClassSchedule(Connection connection) {
        this.connection = connection;
    }


   
    public List<String> getSchedule(String date) throws SQLException {
        List<String> schedule = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT RoomID, GroupID, Time FROM ClassSchedule WHERE Date = CAST(? AS DATE)");
            preparedStatement.setString(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int roomID = resultSet.getInt("RoomID");
                int groupID = resultSet.getInt("GroupID");
                String time = resultSet.getString("Time");
                schedule.add("RoomID: " + roomID + ", GroupID: " + groupID + ", Time: " + time);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return schedule;
    }


    // Method to update the class schedule
  public void bookRoomForGroup(int groupID, int roomID, String date, String time) throws SQLException {
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(
            "INSERT INTO ClassSchedule (GroupID, RoomID, Date, Time) VALUES (?, ?, CAST(? AS DATE), CAST(? AS TIME))");
        preparedStatement.setInt(1, groupID);
        preparedStatement.setInt(2, roomID);
        preparedStatement.setString(3, date);
        preparedStatement.setString(4, time);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
}
