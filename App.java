package com.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/HealthManagementDB";
        String username = "postgres";
        String password = "Lolpy911!";


        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database.");

     
        // 1)
        //     Test Member class
            Member member1 = new Member(connection);
            member1.register("SSJGoku", "SSJGoku@example.com", "123456789", 150, 170, "Super Saiyan");
            member1.updateProfile("SSJGoku@example.com", 160, 170, "Super Saiyan 2");
            member1.displayDashboard("SSJGoku@example.com");
            
            Member member2 = new Member(connection);
            member2.register("SSJBroly", "SSJBroly@example.com", "123456789", 300, 270, "Legendary Super Saiyan");
            member2.updateProfile("SSJBroly@example.com", 280, 270, "Legendary Super Saiyan");
            member2.displayDashboard("SSJBroly@example.com");

        //    they can also request a personal/group session to an admin directly from here but will showcase it seperately

        // 2)
            // Test Trainer class
            Trainer trainer = new Trainer(connection);
            trainer.insertTrainer("Beerus", "Beerus@example.com", "987654321", "Destruction");
            trainer.addScheduleEntry("Beerus@example.com", "2024-04-07", "10:00:00", true);
            boolean isAvailable = trainer.checkAvailability("trainer@example.com", "2024-04-07", "10:00:00");
            System.out.println("Trainer availability: " + isAvailable);


        // 3)
            // Test AdministrativeStaff class
            AdministrativeStaff adminStaff = new AdministrativeStaff(connection);
            adminStaff.schedulePersonalTrainingSession(1, 1, "2024-04-09", "09:00:00", 60);
            adminStaff.scheduleGroupFitnessClass(1, 1, "Power Focus", "2024-04-10", "11:00:00", 90, 3);

        // 4)
            // Test Room class
            Room room = new Room(connection);
            room.addRoom("Spirit Chamber", 30);
            room.updateRoom(1, "Spirit ChamberV2", 40);


        // 5)
            // Test ClassSchedule class
            ClassSchedule classSchedule = new ClassSchedule(connection);
            classSchedule.bookRoomForGroup(1, 1, "2024-04-10", "11:00:00");
            List<String> schedule = classSchedule.getSchedule("2024-04-10");
            System.out.println("Class schedule for 2024-04-10:");
            for (String entry : schedule) {
                System.out.println(entry);
            }

        // 6)
            // Test Billing class
            Billing billing = new Billing(connection);
            billing.processPayment("SSJGoku@example.com", 50.00, "2024-04-11", "Credit Card");
        
        // 7)
            Equipment equipment = new Equipment(connection);
            equipment.addEquipment("Cloak", Date.valueOf("2024-04-12"));

        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}