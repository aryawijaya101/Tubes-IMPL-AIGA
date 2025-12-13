package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/field_booking_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e){
            System.err.println("Error: Gagal terhubung ke Database!.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    public static void main(String[] args) {
        Connection testConn = DatabaseConnection.getConnection();
        if (testConn != null) {
            System.out.println("OK");
        } else {
            System.out.println("STATUS: KONEKSI GAGAL.");
        }
    }
}
