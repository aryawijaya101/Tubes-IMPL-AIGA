package model.login;

import database.DatabaseConnection;
import model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    public User login(String email, String password) {
        User user = null;
        // Pastikan nama tabel sesuai database
        String sql = "SELECT * FROM tbl_users WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Ambil data user yang cocok
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("nama_lengkap"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error saat Login: " + e.getMessage());
        }
        return user;
    }
}
