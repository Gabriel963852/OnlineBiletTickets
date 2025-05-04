package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/airports";
    private static final String USER = "root"; // MySQL потребител
    private static final String PASSWORD = "gabriel123"; // Парола за MySQL

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Зареждане на драйвера
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Successfully connected to MySQL!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return connection; // Връщаме връзката
    }

    public static void main(String[] args) {
        Connection conn = getConnection(); // Тества връзката
        if (conn != null) {
            System.out.println("Database connection established.");
        } else {
            System.out.println("Failed to establish database connection.");
        }
    }

}





