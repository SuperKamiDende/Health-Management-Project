package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Room {
    private Connection connection;

    public Room(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new room to the database
    public void addRoom(String roomName, int capacity) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Room (RoomName, Capacity) VALUES (?, ?)");
            preparedStatement.setString(1, roomName);
            preparedStatement.setInt(2, capacity);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Method to update an existing room in the database
    public void updateRoom(int roomId, String roomName, int capacity) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Room SET RoomName = ?, Capacity = ? WHERE RoomID = ?");
            preparedStatement.setString(1, roomName);
            preparedStatement.setInt(2, capacity);
            preparedStatement.setInt(3, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
