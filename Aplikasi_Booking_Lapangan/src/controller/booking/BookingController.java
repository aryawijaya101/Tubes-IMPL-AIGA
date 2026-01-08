package controller.booking;

import model.booking.BookingDAO;
import model.lapangan.LapanganDAO; // IMPORT INI
import model.entity.Booking;
import view.booking.ManageBookingView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BookingController {

    private ManageBookingView view;
    private BookingDAO bookingDAO;
    private LapanganDAO lapanganDAO; // TAMBAH INI

    public BookingController(ManageBookingView view) {
        this.view = view;
        this.bookingDAO = new BookingDAO();
        this.lapanganDAO = new LapanganDAO(); // INISIALISASI

        initController();
        loadData();
    }

    private void initController() {
        view.getBtnRefresh().addActionListener(e -> loadData());
        view.getBtnFilter().addActionListener(e -> loadData()); // Filter status

        // --- EVENT LISTENER TOMBOL AKSI ---
        view.getBtnComplete().addActionListener(e -> processComplete()); // Selesai Main
        view.getBtnCancel().addActionListener(e -> processCancel());     // Batalkan
        view.getBtnConfirm().addActionListener(e -> processConfirm());   // Konfirmasi
    }

    private void loadData() {
        // Ambil filter status dari ComboBox
        String selectedStatus = (String) view.getComboFilterStatus().getSelectedItem();

        List<Booking> list = bookingDAO.getAllBookings(); // Bisa dimodifikasi untuk filter di DAO

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (Booking b : list) {
            // Filter Client-Side Sederhana
            if (!selectedStatus.equals("Semua") && !b.getStatus().equalsIgnoreCase(selectedStatus)) {
                continue; // Skip jika status tidak cocok
            }

            Object[] row = {
                    b.getBookingId(),
                    "User " + b.getUserId(), // Idealnya ambil Nama User via UserDAO
                    "Field " + b.getFieldId(), // Idealnya ambil Nama Lapangan via LapanganDAO
                    b.getBookingDate(),
                    b.getStartTime(),
                    b.getEndTime(),
                    b.getTotalPrice(),
                    b.getStatus()
            };
            model.addRow(row);
        }
    }

    // LOGIKA SELESAI MAIN (Completed -> Available)
    private void processComplete() {
        int selectedRow = view.getTableBooking().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data booking dulu!");
            return;
        }

        int bookingId = (int) view.getTableBooking().getValueAt(selectedRow, 0);
        String currentStatus = (String) view.getTableBooking().getValueAt(selectedRow, 7);

        // Validasi: Hanya yang Confirmed/Pending yang bisa diselesaikan
        if (currentStatus.equalsIgnoreCase("Cancelled") || currentStatus.equalsIgnoreCase("Completed")) {
            JOptionPane.showMessageDialog(view, "Booking ini sudah selesai atau dibatalkan.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Set booking ini jadi SELESAI dan buka kembali lapangan?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {

            // 1. Ubah Status Booking jadi "Completed"
            boolean updateBooking = bookingDAO.updateStatus(bookingId, "Completed");

            if (updateBooking) {
                // 2. CARI ID LAPANGAN DARI BOOKING TERSEBUT
                int fieldId = bookingDAO.getFieldIdByBookingId(bookingId);

                // 3. UBAH STATUS LAPANGAN JADI "Available"
                if (fieldId > 0) {
                    lapanganDAO.updateStatusLapangan(fieldId, "Available");
                }

                JOptionPane.showMessageDialog(view, "Status Update: Completed. Lapangan sekarang Available.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal update data.");
            }
        }
    }

    // --- LOGIKA CANCEL (Batalkan -> Available) ---
    // Tambahan: Kalau dicancel juga harusnya lapangan jadi Available lagi
    private void processCancel() {
        int selectedRow = view.getTableBooking().getSelectedRow();
        if (selectedRow == -1) return;

        int bookingId = (int) view.getTableBooking().getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view, "Batalkan Booking ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookingDAO.updateStatus(bookingId, "Cancelled")) {

                // Jika dibatalkan, lapangan juga harus Available kembali
                int fieldId = bookingDAO.getFieldIdByBookingId(bookingId);
                if (fieldId > 0) {
                    lapanganDAO.updateStatusLapangan(fieldId, "Available");
                }

                loadData();
            }
        }
    }

    private void processConfirm() {
        int selectedRow = view.getTableBooking().getSelectedRow();
        if (selectedRow == -1) return;
        int bookingId = (int) view.getTableBooking().getValueAt(selectedRow, 0);

        if (bookingDAO.updateStatus(bookingId, "Confirmed")) {
            // Kalau confirm, lapangan harusnya statusnya "Booked" (jika belum)
            // Bisa ditambahkan logika update lapangan jadi "Booked" disini untuk jaga-jaga
            loadData();
        }
    }
}