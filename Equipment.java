package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class Equipment {
    private Connection connection;

    public Equipment(Connection connection) {
        this.connection = connection;
    }

    // Method to record equipment maintenance
    public void recordMaintenance(String equipmentName, Date maintenanceDate) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Equipment SET LastMaintenanceDate = ? WHERE EquipmentName = ?");
            preparedStatement.setDate(1, maintenanceDate);
            preparedStatement.setString(2, equipmentName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Equipment maintenance recorded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void addEquipment(String equipmentName, Date lastMaintenanceDate) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Equipment (EquipmentName, LastMaintenanceDate) VALUES (?, ?)");
            preparedStatement.setString(1, equipmentName);
            preparedStatement.setDate(2, lastMaintenanceDate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Equipment inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
