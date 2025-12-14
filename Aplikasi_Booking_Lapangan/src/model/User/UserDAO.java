package model.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.entity.User;

public class UserDAO {
    // 1. GET ALL USERS (Untuk ditampilkan di Tabel)
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        // Pastikan nama tabel sesuai database kamu (tbl_users)
        String sql = "SELECT * FROM tbl_users ORDER BY user_id ASC";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User(
                        rs.getInt("user_id"),
                        rs.getString("nama_lengkap"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
                list.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error Get Users: " + e.getMessage());
        }
        return list;
    }

    // 2. INSERT USER (Tambah User Baru)
    public boolean insertUser(User user) {
        String sql = "INSERT INTO tbl_users (nama_lengkap, email, password, phone, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getNama());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); // Idealnya di-hash
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Insert User: " + e.getMessage());
            return false;
        }
    }

    // 3. UPDATE USER (Edit Data)
    public boolean updateUser(User user) {
        // Kita tidak update password di sini agar aman, kecuali mau fitur reset password terpisah
        String sql = "UPDATE tbl_users SET nama_lengkap=?, email=?, phone=?, role=? WHERE user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getNama());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Update User: " + e.getMessage());
            return false;
        }
    }

    // 4. DELETE USER (Hapus User)
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM tbl_users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Delete User: " + e.getMessage());
            return false;
        }
    }

    // 5. GET USER BY ID (Untuk Relasi)
    public User getUserById(int userId) {
        String sql = "SELECT * FROM tbl_users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("nama_lengkap"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
