package services;

import database.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class AirlineService {

    public static void addAirlines(Scanner scanner) {
        while (true) {
            System.out.println("\n📞 Управление на авиокомпании:");
            System.out.println("1. Показване на авиокомпании");
            System.out.println("2. Добавяне на авиокомпания");
            System.out.println("3. Изтриване на авиокомпания");
            System.out.println("4. Назад");
            System.out.print("👉 Избор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllAirlines();
                    break;
                case 2:
                    addAirline(scanner);
                    break;
                case 3:
                    deleteAirline(scanner);
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

    public static void addAirline(Scanner scanner) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Въведи код на авиокомпанията: ");
            String code = scanner.nextLine();
            System.out.print("Име на авиокомпанията: ");
            String name = scanner.nextLine();
            System.out.print("Държава: ");
            String country = scanner.nextLine();


            String query = "INSERT INTO airlines(code,name,country) VALUES(?,?,?)";

             PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, code);
            stmt.setString(2, name);
            stmt.setString(3, country);
            stmt.executeUpdate();
            System.out.println("✅ Авиокомпанията е добавена успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Авиокомпанията не е добавена!");
        }
    }

    public static void showAllAirlines() {
        String query = "SELECT * FROM airlines";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Code: " + rs.getString("code") +
                        "| Name: " + rs.getString("name") +
                        "| Country: " + rs.getString("country"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAirline(Scanner scanner) {
        System.out.print("👉 Име на авиокомпанията за изтриване: ");
        String name = scanner.nextLine();

        String query = "DELETE FROM Airlines WHERE Name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Авиокомпанията е изтрита.");
            } else {
                System.out.println("⚠ Авиокомпанията не е намерена.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}