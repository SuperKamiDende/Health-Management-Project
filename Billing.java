package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Billing {
    private Connection connection;

    public Billing(Connection connection) {
        this.connection = connection;
    }

    // Method to retrieve the member ID using email
private int getMemberID(String email) throws SQLException {
    int memberID = -1; // Default value if member not found
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT MemberID FROM Member WHERE Email = ?");
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            memberID = resultSet.getInt("MemberID");
        }
        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
    return memberID;
}


public void processPayment(String memberEmail, double amount, String date, String paymentMethod) throws SQLException {
    try {
        // Retrieve the member ID using the email
        int memberID = getMemberID(memberEmail);

        // Check if memberID is valid
        if (memberID != -1) {
            // Member exists, proceed with payment processing
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO BillingPayment (MemberID, Amount, Date, PaymentMethod) VALUES (?, ?, CAST(? AS DATE), ?)");
            preparedStatement.setInt(1, memberID);
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, paymentMethod);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Payment processed successfully.");
        } else {
            System.out.println("Member not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
}