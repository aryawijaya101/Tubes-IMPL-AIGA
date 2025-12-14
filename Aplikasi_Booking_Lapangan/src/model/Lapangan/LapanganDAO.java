package model.Lapangan;

import database.DatabaseConnection;
import model.entity.Lapangan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
* Model/DAO (Data Access Object) Khusus untuk Modul Kelola Lapangan.
* Menangani CRUD tabel 'fields'.
*/
public class LapanganDAO {
    // CREATE (TAMBAH LAPANGAN BARU)
    public boolean insertLapangan(Lapangan lapangan) {
        String sql = "INSERT INTO tbl_fields (name_field, location_field, type_field, price_per_hour, status_field) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, lapangan.getName());
            ps.setString(2, lapangan.getLocation());
            ps.setString(3, lapangan.getType()); // Futsal, Badminton, dll
            ps.setDouble(4, lapangan.getPricePerHour());
            ps.setString(5, lapangan.getStatus()); // Available, Maintenance
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error Insert Lapangan: " + e.getMessage());
            return false;
        }
    }

    // READ (AMBIL SEMUA DATA LAPANGAN)
    public List<Lapangan> getAllLapangan() {
        List<Lapangan> listLapangan = new ArrayList<>();
        String sql = "SELECT * FROM tbl_fields ORDER BY field_id ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Pakai Constructor Lengkap dari Entity Lapangan
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

    // UPDATE (UBAH DATA LAPANGAN)
    public boolean updateLapangan(Lapangan lapangan) {
        String sql = "UPDATE tbl_fields SET name_field=?, location_field=?, type_field=?, price_per_hour=?, status_field=? WHERE field_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
          PreparedStatement ps = conn.prepareStatement(sql)) {
          
            ps.setString(1, lapangan.getName());
            ps.setString(2, lapangan.getLocation());
            ps.setString(3, lapangan.getType());
            ps.setDouble(4, lapangan.getPricePerHour());
            ps.setString(5, lapangan.getStatus());
            ps.setInt(6, lapangan.getFieldId()); // WHERE field_id = ...
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error Update Lapangan: " + e.getMessage());
            return false;
        }
    }

    // DELETE (HAPUS LAPANGAN)
    public boolean deleteLapangan(int fieldId) {
        // Cek dulu apakah lapangan sedang dipakai booking (Optional Logic)
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

    // Cek apakah lapangan ada di tabel booking dengan status 'Confirmed' atau 'Pending'
    private boolean isLapanganBooked(int fieldId) {
        String sql = "SELECT booking_id FROM tbl_bookings WHERE field_id = ? AND status IN ('Pending', 'Confirmed')";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, fieldId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // True jika ada booking
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    // GET BY ID (Opsional, untuk Edit form nanti)
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
}
