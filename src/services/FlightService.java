package services;

import database.DatabaseConnection;
import java.sql.*;
import java.util.Scanner;

public class FlightService {

    public static void addFlights(Scanner scanner) {
        while (true) {
            System.out.println("\n📞 Управление на полети:");
            System.out.println("1. Показване на полети");
            System.out.println("2. Добавяне на полет");
            System.out.println("3. Изтриване на полет");
            System.out.println("4. Назад");
            System.out.print("👉 Избор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllFlights();
                    break;
                case 2:
                    addFlight(scanner);
                    break;
                case 3:
                    deleteFlight(scanner);
                    break;
                case 4: {
                    System.out.println("🔚 Назад към администраторското меню.");
                    return;
                }
                default: System.out.println("⚠ Невалиден избор!");
            }
        }
    }

    // Добавяне на полет
    public static void addFlight(Scanner scanner) {

        try (Connection conn = DatabaseConnection.getConnection()){
            System.out.print("Въведи номер на полета: ");
            String fnumber = scanner.nextLine();
            System.out.print("Авиокомпания: ");
            String airlineOperator = scanner.nextLine();
            System.out.print("От летище: ");
            String depAirport = scanner.nextLine();
            System.out.print("До летище: ");
            String arrAirport = scanner.nextLine();
            System.out.print("👉 Въведи дата на полета (ГГГГ-ММ-ДД): ");
            String dateInput = scanner.nextLine();
            Date flight_date = Date.valueOf(dateInput);
            System.out.print("Час: ");
            String flightTime = scanner.nextLine();
            System.out.print("Продължителност на полета: ");
            int duration = Integer.parseInt(scanner.nextLine());
            System.out.print("Самолет: ");
            String airplane = scanner.nextLine();

        String query = "INSERT INTO flights (Fnumber, airline_operator, dep_airport, arr_airport, flight_date, flight_time, flight_duration, Airplane) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
             PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, fnumber);
            stmt.setString(2, airlineOperator);
            stmt.setString(3, depAirport);
            stmt.setString(4, arrAirport);
            stmt.setDate(5,flight_date);
            stmt.setString(6, flightTime);
            stmt.setInt(7, duration);
            stmt.setString(8, airplane);

            stmt.executeUpdate();
            System.out.println("✅ Полетът е добавен успешно!");

        } catch (SQLException e) {
            System.out.println("❌ Полетът не е добавен!");
        }
    }

    // Показване на всички полети
    public static void showAllFlights() {
        String query = "SELECT * FROM flights";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n📋 Списък с полети:");
            while (rs.next()) {
                System.out.println("Номер на полета: " + rs.getString("Fnumber") +
                        "| Авиокомпания: " + rs.getString("airline_operator") +
                        "| От летище: " + rs.getString("dep_airport") +
                        "| До летище: " + rs.getString("arr_airport") +
                        "| Дата: " + rs.getDate("flight_date") +
                        "| Час: " + rs.getString("flight_time") +
                        "| Продължителност: " + rs.getInt("flight_duration") + " мин." +
                        "| Самолет: " + rs.getString("Airplane"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Изтриване на полет по номер
    public static void deleteFlight(Scanner scanner) {
        System.out.print("👉 Номер на полета за изтриване: ");
        String Fnumber = scanner.nextLine();

        String query = "DELETE FROM Flights WHERE Fnumber = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, Fnumber);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Полетът е изтрит.");
            } else {
                System.out.println("⚠ Полетът с такъв номер не е намерен.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (Допълнително) Търсене на полети по дестинация
    public static void searchFlightsByDestinationName(String destinationName) {
        String query = "SELECT f.Fnumber, f.dep_airport, a1.name AS departure_name, f.arr_airport, a2.name AS arrival_name, f.flight_date, f.flight_time, f.flight_duration " +
                "FROM Flights f " +
                "JOIN Airports a1 ON f.dep_airport = a1.code " +
                "JOIN Airports a2 ON f.arr_airport = a2.code " +
                "WHERE a2.name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + destinationName + "%");
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n📄 Полети до " + destinationName + ":");
            while (rs.next()) {
                System.out.println("Полет №: " + rs.getString("Fnumber") +
                        " | Излита от: " + rs.getString("departure_name") +
                        " | Пристига в: " + rs.getString("arrival_name") +
                        " | Дата: " + rs.getDate("flight_date") +
                        " | Час: " + rs.getTime("flight_time") +
                        " | Продължителност: " + rs.getInt("flight_duration") + " мин.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Търсене на полет по дата
    public static void searchFlightsByDate(Date flightDate) {
        String sql = "SELECT * FROM Flights WHERE flight_date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, flightDate);
            ResultSet rs = stmt.executeQuery();

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                System.out.println("Номер на полета: " + rs.getString("Fnumber") +
                        "| Авиокомпания: " + rs.getString("airline_operator") +
                        "| От летище: " + rs.getString("dep_airport") +
                        "| До летище: " + rs.getString("arr_airport") +
                        "| Дата: " + rs.getDate("flight_date") +
                        "| Час: " + rs.getString("flight_time") +
                        "| Продължителност: " + rs.getInt("flight_duration") + " мин." +
                        "| Самолет: " + rs.getString("Airplane"));
            }

            if (!hasResults) {
                System.out.println("⚠ Няма полети за въведената дата.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
