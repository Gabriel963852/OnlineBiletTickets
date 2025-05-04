package services;

import database.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class AirportService {

    public static void addAirports(Scanner scanner) {
        while (true) {
            System.out.println("\n📞 Управление на летища:");
            System.out.println("1. Показване на летища");
            System.out.println("2. Добавяне на летище");
            System.out.println("3. Изтриване на летище");
            System.out.println("4. Назад");
            System.out.print("👉 Избор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllAirports();
                    break;
                case 2:
                    addAirport(scanner);
                    break;
                case 3:
                    deleteAirport(scanner);
                    break;
                case 4: {
                    System.out.println("🔚 Назад към администраторското меню.");
                    return;
                }
                default: System.out.println("⚠ Невалиден избор!");
            }
        }
    }

    public static void addAirport(Scanner scanner) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Въведи код на летището: ");
            String code = scanner.nextLine();
            System.out.print("Име на летището: ");
            String name = scanner.nextLine();
            System.out.print("Държава: ");
            String country = scanner.nextLine();
            System.out.print("Град:  ");
            String city = scanner.nextLine();


            String query = "INSERT INTO airports (code, name, country, city) VALUES (?, ?, ?, ?)";
             PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, code);
            stmt.setString(2, name);
            stmt.setString(3, country);
            stmt.setString(4, city);
            stmt.executeUpdate();
            System.out.println("✅ Летището е добавено успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAllAirports() {
        String query = "SELECT * FROM airports";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Код на летището: " + rs.getString("code") +
                        "| Име на летището: " + rs.getString("name") +
                        "| Държава: " + rs.getString("country") +
                        "| Град: " + rs.getString("city"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAirport(Scanner scanner) {
        System.out.print("👉 Име на летището за изтриване: ");
        String name = scanner.nextLine();

        String query = "DELETE FROM Airports WHERE Name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Летището е изтрито.");
            } else {
                System.out.println("⚠ Летището не е намерено.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

