package controller.pembayaran;
import model.entity.Pembayaran;
import model.pembayaran.PembayaranDAO;
import view.pembayaran.KelolaPembayaranView;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class pembayaranController {
    private KelolaPembayaranView view;
    private PembayaranDAO dao;

    public pembayaranController(KelolaPembayaranView view) {
        this.view = view;
        this.dao = new PembayaranDAO();

        initController();

        // Load data otomatis saat halaman dibuka pertama kali
        muatData();
    }

    private void initController() {
        // Pasang aksi pada tombol Refresh
        view.getBtnRefresh().addActionListener(e -> muatData());

        view.getBtnCetak().addActionListener(e -> cetakLaporan());
    }

    private void cetakLaporan() {
        try {
            // 1. Buat Header (Judul di atas kertas)
            MessageFormat header = new MessageFormat("Laporan Pemasukan Booking Lapangan");

            // 2. Buat Footer (Nomor Halaman di bawah kertas)
            MessageFormat footer = new MessageFormat("Halaman {0,number,integer}");

            // 3. Perintah Print Bawaan JTable
            // Mode FIT_WIDTH agar semua kolom muat dalam satu kertas
            boolean complete = view.getTabelPembayaran().print(JTable.PrintMode.FIT_WIDTH, header, footer);

            if (complete) {
                JOptionPane.showMessageDialog(view, "Proses cetak selesai / PDF berhasil disimpan!");
            } else {
                JOptionPane.showMessageDialog(view, "Proses cetak dibatalkan.");
            }

        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(view, "Gagal mencetak: " + pe.getMessage());
        }
    }

    private void muatData() {
        // 1. Ambil semua data dari DAO
        // Pastikan di PembayaranDAO nama methodnya sesuai (ambilSemuaPembayaran / getAllPayments)
        List<Pembayaran> daftarPembayaran = dao.getPembayaran();

        // 2. Bersihkan tabel sebelum diisi ulang
        view.getTableModel().setRowCount(0);

        // Format Tanggal (Contoh: 20-10-2025 14:30)
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        double totalPemasukan = 0;

        // 3. Loop data dan masukkan ke tabel
        for (Pembayaran p : daftarPembayaran) {

            // Format Rupiah sederhana
            String formatRupiah = String.format("%,.0f", p.getAmount());

            Object[] baris = {
                    p.getPaymentId(),
                    p.getBookingId(),
                    (p.getPaymentDate() != null) ? sdf.format(p.getPaymentDate()) : "-", // Cek null biar gak error
                    p.getMethod(),
                    p.getStatus(),
                    "Rp " + formatRupiah
            };

            view.getTableModel().addRow(baris);

            // 4. Hitung Total Pemasukan (Hanya yang statusnya Success/Completed jika perlu validasi)
            // Di sini kita hitung semua yang ada di list
            totalPemasukan += p.getAmount();
        }

        // 5. Update Label Total di Pojok Kanan Atas View
        String totalFormat = String.format("%,.0f", totalPemasukan);
        view.setTotalPemasukan("Total Masuk: Rp " + totalFormat);
    }
}
