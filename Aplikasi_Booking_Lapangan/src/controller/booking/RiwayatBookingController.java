package controller.booking;

import model.entity.Booking;
import model.entity.Lapangan;
import model.entity.Pembayaran;
import model.entity.User;
import model.booking.BookingDAO;
import model.lapangan.LapanganDAO;
import model.pembayaran.PembayaranDAO; // Pastikan DAO ini dibuat (lihat di bawah)
import view.lapangan.RiwayatBooking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class RiwayatBookingController {

    private RiwayatBooking view;
    private BookingDAO bookingDAO;
    private LapanganDAO lapanganDAO;
    private PembayaranDAO pembayaranDAO;
    private User currentUser; // User yang sedang login

    public RiwayatBookingController(RiwayatBooking view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;

        // Inisialisasi semua DAO yang dibutuhkan
        this.bookingDAO = new BookingDAO();
        this.lapanganDAO = new LapanganDAO();
        this.pembayaranDAO = new PembayaranDAO();

        initController();
        loadData();
    }

    private void initController() {
        // Tombol Refresh
        view.getBtnRefresh().addActionListener(e -> loadData());

        // Tombol Search
        view.getBtnSearch().addActionListener(e -> searchBooking());
    }

    private void loadData() {
        // Ambil data Booking berdasarkan User yang login
        // (Pastikan method getBookingsByUser ada di BookingDAO)
        List<Booking> listBooking = bookingDAO.getBookingsByUser(currentUser.getUserId());

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0); // Reset tabel

        SimpleDateFormat sdfTanggal = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdfJam = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdfLengkap = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (Booking b : listBooking) {
            // Ambil Nama Lapangan (Relasi ke LapanganDAO)
            Lapangan l = lapanganDAO.getLapanganById(b.getFieldId());
            String namaLapangan = (l != null) ? l.getName() : "Unknown Field";

            // Ambil Data Pembayaran (Relasi ke PembayaranDAO)
            Pembayaran p = pembayaranDAO.getPembayaranByBookingId(b.getBookingId());

            String metodeBayar = "-";
            String waktuBayar = "Belum Bayar";

            if (p != null) {
                metodeBayar = p.getMethod();
                if (p.getPaymentDate() != null) {
                    waktuBayar = sdfLengkap.format(p.getPaymentDate());
                }
            }

            // Format Waktu Main (Start - End)
            String waktuMain = sdfJam.format(b.getStartTime()) + " - " + sdfJam.format(b.getEndTime());

            // Masukkan ke Tabel sesuai urutan kolom di View
            Object[] row = {
                    b.getBookingId(),
                    namaLapangan,
                    sdfTanggal.format(b.getBookingDate()),
                    waktuMain,                // Kolom Waktu Main
                    "Rp " + (long)b.getTotalPrice(), // Format harga integer biar rapi
                    metodeBayar,
                    waktuBayar                // Kolom Waktu Pembayaran
            };
            model.addRow(row);
        }
    }

    private void searchBooking() {
        String keyword = view.getTxtSearch().getText().toLowerCase();

        // Load ulang semua, lalu hapus baris yang tidak cocok di tabel
        loadData();

        if (!keyword.isEmpty()) {
            DefaultTableModel model = view.getTableModel();
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                String idBooking = model.getValueAt(i, 0).toString().toLowerCase();
                String namaLap = model.getValueAt(i, 1).toString().toLowerCase();

                // Hapus jika ID atau Nama Lapangan tidak mengandung keyword
                if (!idBooking.contains(keyword) && !namaLap.contains(keyword)) {
                    model.removeRow(i);
                }
            }
        }
    }
}