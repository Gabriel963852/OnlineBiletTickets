package services;

import database.DatabaseConnection;
import java.sql.*;

public class LoginService {

    // Унифициран метод за автентикация, връща роля или "none"
    public static String authenticate(String email, String password) {
        String query = "SELECT role FROM customers WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role");  // Връща 'user' или 'admin'
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "none";
    }
}
