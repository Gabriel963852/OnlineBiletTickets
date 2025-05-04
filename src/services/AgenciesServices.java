package services;

import database.DatabaseConnection;
import java.sql.*;
import java.util.Scanner;

public class AgenciesServices {

    public static void addAgencies(Scanner scanner) {
        while (true) {
            System.out.println("\n📞 Управление на агенции:");
            System.out.println("1. Показване на агенции");
            System.out.println("2. Добавяне на агенция");
            System.out.println("3. Изтриване на агенция");
            System.out.println("4. Назад");
            System.out.print("👉 Избор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listAgencies();
                    break;
                case 2:
                    addAgency(scanner);
                    break;
                case 3:
                    deleteAgency(scanner);
                    break;
                case 4: {
                    System.out.println("🔚 Назад към администраторското меню.");
                    return;
                }
                default: System.out.println("⚠ Невалиден избор!");
            }
        }
    }

    public static void listAgencies() {
        String query = "SELECT * FROM Agencies";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n📋 Агенции:");
            while (rs.next()) {
                System.out.println("Име: " +rs.getString("Name") +
                        " | " + "Държава: " + rs.getString("Country") +
                        " | " + "Град: "+ rs.getString("City") +
                        " | " + "Телефон: "+ rs.getString("Phone"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAgency(Scanner scanner) {
        System.out.print("👉 Име: ");
        String name = scanner.nextLine();
        System.out.print("👉 Държава: ");
        String country = scanner.nextLine();
        System.out.print("👉 Град: ");
        String city = scanner.nextLine();
        System.out.print("👉 Телефон: ");
        String phone = scanner.nextLine();

        String query = "INSERT INTO Agencies (Name, Country, City, Phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, country);
            stmt.setString(3, city);
            stmt.setString(4, phone);
            stmt.executeUpdate();
            System.out.println("✅ Агенцията е добавена!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAgency(Scanner scanner) {
        System.out.print("👉 Име на агенцията за изтриване: ");
        String name = scanner.nextLine();

        String query = "DELETE FROM Agencies WHERE Name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Агенцията е изтрита.");
            } else {
                System.out.println("⚠ Агенцията не е намерена.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
