package services;

import database.DatabaseConnection;
import java.sql.*;
import java.util.Scanner;

public class CustomerService {

    public static void addCustomer(Scanner scanner) {
        System.out.print("Име: ");
        String fname = scanner.nextLine();
        System.out.print("Фамилия: ");
        String lname = scanner.nextLine();
        System.out.print("Имейл: ");
        String email = scanner.nextLine();
        System.out.print("Парола: ");
        String password = scanner.nextLine();
        System.out.print("👉 Роля (user/admin): ");
        String role = scanner.nextLine();

        String query = "INSERT INTO customers (Fname, Lname, Email, password,role) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, password);
            stmt.setString(5,role);
            stmt.executeUpdate();
            System.out.println("✅ Потребителят е регистриран успешно!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void manageCustomers(Scanner scanner) {
        System.out.println("1. Преглед на всички потребители");
        System.out.println("2. Изтриване на потребител по ID");
        System.out.println("3. Назад");
        System.out.print("Избор: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            String query = "SELECT * FROM customers";
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + " | Име: " + rs.getString("FName") + " | Фамилия: " + rs.getString("LName") + " | Email: " + rs.getString("Email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (choice.equals("2")) {
            System.out.print("Въведи ID на потребител за изтриване: ");
            int id = Integer.parseInt(scanner.nextLine());
            String query = "DELETE FROM customers WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                System.out.println("✅ Потребителят е изтрит.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(choice.equals("3")){
            System.out.println("🔚 Назад към администраторското меню.");
        }
    }

    public static int getCustomerIdByEmail(String email) {
        String query = "SELECT id FROM customers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // ако не намери
    }

}
