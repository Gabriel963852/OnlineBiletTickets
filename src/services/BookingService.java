package services;

import database.DatabaseConnection;
import java.sql.*;
import java.util.Scanner;

public class BookingService {

    public static void addBooking(Scanner scanner, int customerId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Въведи име на агенцията: ");
            String agency = scanner.nextLine();
            System.out.print("Код на авиокомпания: ");
            String airlineCode = scanner.nextLine();
            System.out.print("Номер на полет: ");
            String flightNumber = scanner.nextLine();
            System.out.print("Дата на резервация (yyyy-mm-dd): ");
            String bookingDate = scanner.nextLine();
            System.out.print("Дата на полета (YYYY-MM-DD): ");
            String flightDate = scanner.nextLine();
            System.out.print("Цена: ");
            double price = Double.parseDouble(scanner.nextLine());

            String bookingCode = "B" + System.currentTimeMillis() % 1000000;


            String query = "INSERT INTO bookings (Code, Agency, airline_code, flight_number, customer_id, booking_date, flight_date, Price, status) VALUES (?,?,?,?,?,?,?,?,'0')";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookingCode);
            stmt.setString(2, agency);
            stmt.setString(3, airlineCode);
            stmt.setString(4, flightNumber);
            stmt.setInt(5,customerId);
            stmt.setDate(6, java.sql.Date.valueOf(bookingDate));
            stmt.setDate(7, java.sql.Date.valueOf(flightDate));
            stmt.setDouble(8, price);

            stmt.executeUpdate();
            System.out.println("✅ Резервацията е направена успешно с код: " + bookingCode);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewCustomerBookings(int customerId) {
        String query = "SELECT * FROM bookings WHERE customer_id = ?";
        double totalAmount = 0.0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                double price = rs.getDouble("Price");
                totalAmount += price;

                System.out.println("Резервация " + rs.getString("Code")
                        + " | Полет: " + rs.getString("flight_number")
                        + " | Дата: " + rs.getDate("flight_date")
                        + " | Цена: " + String.format("%.2f", price) + " лв."
                        + " | Статус: " + (rs.getString("status").equals("1") ? "Потвърдена" : "Непотвърдена"));
            }

            System.out.println("\n💵 Обща сума за всички резервации: " + String.format("%.2f", totalAmount) + " лв.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void manageBookings(Scanner scanner) {
        String query = "SELECT * FROM bookings WHERE status = '0'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Код: " + rs.getString("Code") + " | Полет: " + rs.getString("flight_number") + " | Статус: Непотвърдена");
            }

            System.out.print("Въведи код на резервация за потвърждение: ");
            String code = scanner.nextLine();

            String update = "UPDATE bookings SET status = '1' WHERE Code = ?";
            PreparedStatement up = conn.prepareStatement(update);
            up.setString(1, code);
            up.executeUpdate();
            System.out.println("✅ Резервацията е потвърдена.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewAllBookings() {
        String query = "SELECT * FROM bookings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Код: " + rs.getString("Code") + " | Полет: " + rs.getString("flight_number") +
                        " | Клиент ID: " + rs.getInt("customer_id") + " | Статус: " +
                        (rs.getString("status").equals("1") ? "Потвърдена" : "Непотвърдена"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBooking(Scanner scanner) {
        System.out.print("👉 Код на резервацията за изтриване: ");
        String code = scanner.nextLine();

        String query = "DELETE FROM Bookings WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, code);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Резервацията е изтрита.");
            } else {
                System.out.println("⚠ Резервация с такъв код не е намерена.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkBookingStatusByCode(String bookingCode) {
        String sql = "SELECT status FROM Bookings WHERE Code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bookingCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                if (status.equals("1")) {
                    System.out.println("✅ Резервацията с код " + bookingCode + " е потвърдена.");
                } else {
                    System.out.println("❌ Резервацията с код " + bookingCode + " все още НЕ е потвърдена.");
                }
            } else {
                System.out.println("⚠ Няма открита резервация с този код.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Проверка за налични места в полет
    public static void checkAvailableSeats(Scanner scanner) {
        System.out.print("👉 Въведи номер на полета за проверка: ");
        String flightNumber = scanner.nextLine();

        String seatsQuery = "SELECT a.seats FROM Airplanes a JOIN Flights f ON a.Code = f.Airplane WHERE f.Fnumber = ?";
        String bookedQuery = "SELECT COUNT(*) FROM Bookings WHERE flight_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement seatsStmt = conn.prepareStatement(seatsQuery);
             PreparedStatement bookedStmt = conn.prepareStatement(bookedQuery)) {

            seatsStmt.setString(1, flightNumber);
            ResultSet seatsRs = seatsStmt.executeQuery();

            if (!seatsRs.next()) {
                System.out.println("❌ Невалиден номер на полет.");
                return;
            }

            int totalSeats = seatsRs.getInt(1);

            bookedStmt.setString(1, flightNumber);
            ResultSet bookedRs = bookedStmt.executeQuery();
            bookedRs.next();
            int bookedSeats = bookedRs.getInt(1);

            int availableSeats = totalSeats - bookedSeats;

            System.out.println("✈ Свободни места за полет " + flightNumber + ": " + availableSeats + " от общо " + totalSeats);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
