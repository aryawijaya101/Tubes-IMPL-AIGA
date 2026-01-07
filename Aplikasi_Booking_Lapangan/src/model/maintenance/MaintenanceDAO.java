package model.maintenance;

import model.entity.Maintenance;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk Modul Kelola Jadwal (Maintenance).
 */
public class MaintenanceDAO {
    // CREATE (TAMBAH JADWAL MAINTENANCE BARU)
    public boolean insertMaintenance(Maintenance m) {
        String sql = "INSERT INTO tbl_maintenaces (field_id, scheduled_date, description, status_maintenace) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, m.getFieldId());
            // Mengubah java.util.Date ke java.sql.Date untuk database
            ps.setDate(2, m.getScheduledDate());
            ps.setString(3, m.getDescription());
            ps.setString(4, m.getStatus()); // 'Scheduled', 'In Progress', etc.
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error Insert Maintenance: " + e.getMessage());
            return false;
        }
    }

    // READ (AMBIL SEMUA JADWAL)
    public List<Maintenance> getAllMaintenance() {
        List<Maintenance> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_maintenaces ORDER BY scheduled_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Maintenance m = new Maintenance(
                    rs.getInt("maintenace_id"),
                    rs.getInt("field_id"),
                    rs.getDate("scheduled_date"),
                    rs.getString("description"),
                    rs.getString("status_maintenace")
                );
                list.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error Get All Maintenance: " + e.getMessage());
        }
        return list;
    }

    // UPDATE (EDIT JADWAL)
    public boolean updateMaintenance(Maintenance m) {
        String sql = "UPDATE tbl_maintenaces SET field_id=?, scheduled_date=?, description=?, status_maintenace=? WHERE maintenace_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getFieldId());
            ps.setDate(2, m.getScheduledDate());
            ps.setString(3, m.getDescription());
            ps.setString(4, m.getStatus());
            ps.setInt(5, m.getMaintenanceId()); // WHERE id = ...
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error Update Maintenance: " + e.getMessage());
            return false;
        }
    }

    // DELETE (HAPUS JADWAL)
    public boolean deleteMaintenance(int id) {
        String sql = "DELETE FROM tbl_maintenaces WHERE maintenace_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error Delete Maintenance: " + e.getMessage());
            return false;
        }
    }
    
    // Cek Bentrok Jadwal
    public boolean isScheduleClash(int fieldId, java.util.Date date) {
        String sql = "SELECT maintenace_id FROM tbl_maintenaces WHERE field_id = ? AND scheduled_date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, fieldId);
            ps.setDate(2, new java.sql.Date(date.getTime()));
            
            ResultSet rs = ps.executeQuery();
            return rs.next(); // True jika sudah ada jadwal
            
        } catch (SQLException e) {
            return false;
        }
    }
}
