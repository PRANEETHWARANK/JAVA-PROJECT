package LibraryManagementSystem;
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "praneeth$$6381851557"; // Change if your MySQL has a password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("DB Connection failed: " + e.getMessage());
            return null;
        }
    }
}

