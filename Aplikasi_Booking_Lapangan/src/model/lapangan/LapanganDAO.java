package model.lapangan;

import database.DatabaseConnection;
import model.entity.Lapangan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LapanganDAO {

    // CREATE BIASA (Mengembalikan True/False)
    public boolean insertLapangan(Lapangan lapangan) {
        String sql = "INSERT INTO tbl_fields (name_field, location_field, type_field, price_per_hour, status_field) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lapangan.getName());
            ps.setString(2, lapangan.getLocation());
            ps.setString(3, lapangan.getType());
            ps.setDouble(4, lapangan.getPricePerHour());
            ps.setString(5, lapangan.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Insert Lapangan: " + e.getMessage());
            return false;
        }
    }

    // Method ini yang akan dipakai di Controller untuk fitur otomatisasi
    public int insertLapanganReturnId(Lapangan lapangan) {
        String sql = "INSERT INTO tbl_fields (name_field, location_field, type_field, price_per_hour, status_field) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             // Tambahkan parameter Statement.RETURN_GENERATED_KEYS untuk minta ID baru
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, lapangan.getName());
            ps.setString(2, lapangan.getLocation());
            ps.setString(3, lapangan.getType());
            ps.setDouble(4, lapangan.getPricePerHour());
            ps.setString(5, lapangan.getStatus());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                // Ambil ID yang baru digenerate database
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Kembalikan ID (misal: 10)
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error Insert Lapangan Return ID: " + e.getMessage());
        }
        return -1; // Kode Error jika gagal
    }

    // READ ALL
    public List<Lapangan> getAllLapangan() {
        List<Lapangan> listLapangan = new ArrayList<>();
        String sql = "SELECT * FROM tbl_fields ORDER BY field_id ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lapangan l = new Lapangan(
                        rs.getInt("field_id"),
                        rs.getString("name_field"),
                        rs.getString("location_field"),
                        rs.getString("type_field"),
                        rs.getDouble("price_per_hour"),
                        rs.getString("status_field")
                );
                listLapangan.add(l);
            }
        } catch (SQLException e) {
            System.err.println("Error Get All Lapangan: " + e.getMessage());
        }
        return listLapangan;
    }

    // UPDATE
    public boolean updateLapangan(Lapangan lapangan) {
        String sql = "UPDATE tbl_fields SET name_field=?, location_field=?, type_field=?, price_per_hour=?, status_field=? WHERE field_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lapangan.getName());
            ps.setString(2, lapangan.getLocation());
            ps.setString(3, lapangan.getType());
            ps.setDouble(4, lapangan.getPricePerHour());
            ps.setString(5, lapangan.getStatus());
            ps.setInt(6, lapangan.getFieldId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Update Lapangan: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteLapangan(int fieldId) {
        if (isLapanganBooked(fieldId)) {
            System.err.println("Gagal Hapus: Lapangan sedang ada booking aktif!");
            return false;
        }

        String sql = "DELETE FROM tbl_fields WHERE field_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, fieldId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error Delete Lapangan: " + e.getMessage());
            return false;
        }
    }

    private boolean isLapanganBooked(int fieldId) {
        String sql = "SELECT booking_id FROM tbl_bookings WHERE field_id = ? AND status IN ('Pending', 'Confirmed')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fieldId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    // GET BY ID
    public Lapangan getLapanganById(int id) {
        String sql = "SELECT * FROM tbl_fields WHERE field_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Lapangan(
                        rs.getInt("field_id"),
                        rs.getString("name_field"),
                        rs.getString("location_field"),
                        rs.getString("type_field"),
                        rs.getDouble("price_per_hour"),
                        rs.getString("status_field")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // SEARCH
    public List<Lapangan> cariLapangan(String keyword) {
        List<Lapangan> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_fields WHERE name_field LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lapangan l = new Lapangan(
                        rs.getInt("field_id"),
                        rs.getString("name_field"),
                        rs.getString("location_field"),
                        rs.getString("type_field"),
                        rs.getDouble("price_per_hour"),
                        rs.getString("status_field")
                );
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}