package model.pembayaran;

import model.entity.Pembayaran;
import database.DatabaseConnection; // Sesuaikan import ini

import java.sql.*;
import java.util.List;

public class PembayaranDAO {

    // Ambil data pembayaran berdasarkan ID Booking
    public Pembayaran getPembayaranByBookingId(int bookingId) {
        String sql = "SELECT * FROM tbl_payments WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Pembayaran(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getTimestamp("payment_date"),
                        rs.getDouble("amount"),
                        rs.getString("method_payment"), // Sesuaikan nama kolom DB
                        rs.getString("status_payment")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null jika belum dibayar
    }

    // Method simpan pembayaran (dipakai di form pembayaran nanti)
    public boolean insertPembayaran(Pembayaran p) {
        String sql = "INSERT INTO tbl_payments (booking_id, payment_date, amount, method_payment, status_payment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getBookingId());
            ps.setTimestamp(2, p.getPaymentDate());
            ps.setDouble(3, p.getAmount());
            ps.setString(4, p.getMethod());
            ps.setString(5, p.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pembayaran> getPembayaran() {
        java.util.List<Pembayaran> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM tbl_payments ORDER BY payment_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pembayaran p = new Pembayaran(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getTimestamp("payment_date"),
                        rs.getDouble("amount"),
                        rs.getString("method_payment"), // Pastikan sesuai nama kolom DB kamu
                        rs.getString("status_payment")  // Pastikan sesuai nama kolom DB kamu
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error Get All Payments: " + e.getMessage());
        }
        return list;
    }
}