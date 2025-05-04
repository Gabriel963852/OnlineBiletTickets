package services;

import database.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class AirplaneService {

    public static void addAirplanes(Scanner scanner) {
        while (true) {
            System.out.println("\n📞 Управление на самолети:");
            System.out.println("1. Показване на самолети");
            System.out.println("2. Добавяне на самолети");
            System.out.println("3. Изтриване на самолети");
            System.out.println("4. Назад");
            System.out.print("👉 Избор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllAirplanes();
                    break;
                case 2:
                    addAirplane(scanner);
                    break;
                case 3:
                    deleteAirplane(scanner);
                    break;
                case 4:
                {
                    System.out.println("🔚 Назад към администраторското меню.");
                    return;
                }
                default: System.out.println("⚠ Невалиден избор!");
            }
        }
    }

    public static void addAirplane(Scanner scanner) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Въведи код на полета: ");
            String code = scanner.nextLine();
            System.out.print("Марка на самолета: ");
            String type = scanner.nextLine();
            System.out.print("Брой места: ");
            int seats = Integer.parseInt(scanner.nextLine());
            System.out.print("Година на производство:  ");
            int yearA = Integer.parseInt(scanner.nextLine());

             String query = "INSERT INTO airplanes (code, type, seats, yearA) VALUES (?, ?, ?, ?)";
             PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, code);
            stmt.setString(2, type);
            stmt.setInt(3, seats);
            stmt.setInt(4, yearA);
            stmt.executeUpdate();
            System.out.println("✅ Самолетът е добавен успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAllAirplanes() {
        String query = "SELECT * FROM airplanes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Code: " + rs.getString("code") +
                        "| Type: " + rs.getString("type") +
                        "| Seats: " + rs.getInt("seats") +
                        "| Year: " + rs.getInt("yearA"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAirplane(Scanner scanner) {
        System.out.print("👉 Код на самолета за изтриване: ");
        String code = scanner.nextLine();

        String query = "DELETE FROM Airplanes WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, code);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Самолетът е изтрит.");
            } else {
                System.out.println("⚠ Самолетът не е намерен.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

