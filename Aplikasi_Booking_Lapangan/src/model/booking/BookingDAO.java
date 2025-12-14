package model.booking;

import model.entity.Booking;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Khusus untuk Modul Transaksi Booking.
 * Menangani tabel 'tbl_bookings'.
 */
public class BookingDAO {

    // CREATE (INSERT BOOKING BARU)
    public boolean insertBooking(Booking b) {
        String sql = "INSERT INTO tbl_bookings (user_id, field_id, booking_date, start_time, end_time, total_price, status_booking) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, b.getUserId());
            ps.setInt(2, b.getFieldId());
            ps.setDate(2, b.getBookingDate());
            ps.setTimestamp(4, b.getStartTime());
            ps.setTimestamp(5, b.getEndTime());
            ps.setDouble(6, b.getTotalPrice());
            ps.setString(7, b.getStatus()); // Usually 'Pending'
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error Insert Booking: " + e.getMessage());
            return false;
        }
    }

    // CHECK AVAILABILITY (CEK JADWAL BENTROK)
    // Mengembalikan TRUE jika jadwal tersedia (kosong), FALSE jika sudah ada yang booking
    public boolean checkAvailability(int fieldId, Date date, Timestamp start, Timestamp end) {
        // Cari booking di lapangan yg sama, tanggal sama, DAN jamnya beririsan
        // Status 'Cancelled' atau 'Rejected' tidak dianggap bentrok
        String sql = "SELECT booking_id FROM tbl_bookings " +
                     "WHERE field_id = ? " +
                     "AND booking_date = ? " +
                     "AND status_booking NOT IN ('Cancelled', 'Rejected') " +
                     "AND ( " +
                     "   (start_time < ? AND end_time > ?) " +  // Case A: Booking baru ada di tengah booking lama
                     ")";

        /*
          Kita cek apakah ada booking lain yang Waktu Mulainya SEBELUM Waktu Selesai kita,
          DAN Waktu Selesainya SETELAH Waktu Mulai kita.
        */
        String simpleSql = "SELECT booking_id FROM tbl_bookings " +
                           "WHERE field_id = ? " +
                           "AND booking_date = ? " +
                           "AND status_booking NOT IN ('Cancelled', 'Rejected') " +
                           "AND ((start_time <= ? AND end_time > ?) OR (start_time < ? AND end_time >= ?))"; 
                           // Query bisa disesuaikan tergantung ketatnya aturan jam
        
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, fieldId);
            ps.setDate(2, date);
            ps.setTimestamp(3, end);   // end_time booking lama > start_time booking baru
            ps.setTimestamp(4, start); // start_time booking lama < end_time booking baru
            
            ResultSet rs = ps.executeQuery();
            // Jika ada result (next() == true), berarti ada yang bentrok -> return false (tidak available)
            return !rs.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Anggap tidak available jika error biar aman
        }
    }

    // READ (GET ALL BOOKINGS - UNTUK ADMIN)
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_bookings ORDER BY booking_date DESC, start_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("field_id"),
                    rs.getDate("booking_date"),
                    rs.getTimestamp("start_time"),
                    rs.getTimestamp("end_time"),
                    rs.getDouble("total_price"),
                    rs.getString("status_booking")
                );
                list.add(b);
            }
        } catch (SQLException e) {
            System.err.println("Error Get All Bookings: " + e.getMessage());
        }
        return list;
    }

    // READ BY USER (RIWAYAT BOOKING MEMBER)
    public List<Booking> getBookingsByUser(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_bookings WHERE user_id = ? ORDER BY booking_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("field_id"),
                    rs.getDate("booking_date"),
                    rs.getTimestamp("start_time"),
                    rs.getTimestamp("end_time"),
                    rs.getDouble("total_price"),
                    rs.getString("status_booking")
                );
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE STATUS (KONFIRMASI / BATAL / SELESAI)
    public boolean updateStatus(int bookingId, String newStatus) {
        String sql = "UPDATE tbl_bookings SET status = ? WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
        
            ps.setString(1, newStatus);
            ps.setInt(2, bookingId);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error Update Status: " + e.getMessage());
            return false;
        }
    }
}
