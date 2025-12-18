package controller.booking;

import model.entity.Booking;
import model.booking.BookingDAO;
import model.entity.User;
import model.user.UserDAO;
import model.entity.Lapangan;
import model.lapangan.LapanganDAO;
import view.booking.ManageBookingView;
import view.lapangan.RiwayatBooking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class RiwayatBookingController {

    private RiwayatBooking view;
    private LapanganDAO dao;

    public RiwayatBookingController(RiwayatBooking view) {
        this.view = view;
        this.dao = new LapanganDAO();

        // Mendaftarkan Event Listener (Aksi Tombol)
        initController();

        // Load data pertama kali saat aplikasi dibuka
        loadData();
    }

    private void initController() {
        // Tombol Refresh
        view.getBtnRefresh().addActionListener(e -> loadData());

        // Tombol Search
        view.getBtnSearch().addActionListener(e -> searchLapangan());
    }

    private void loadData() {
        // Ambil data dari database via DAO
        List<Lapangan> list = dao.getAllLapangan();

        // Ambil model tabel dari View
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0); // Bersihkan tabel lama

        // Masukkan data baru baris per baris
        for (Lapangan l : list) {
            Object[] row = {
                    l.getFieldId(),
                    l.getName(),
                    l.getLocation(),
                    l.getType(),
                    l.getPricePerHour(),
                    l.getStatus()
            };
            model.addRow(row);
        }
    }

    private void searchLapangan() {
        String keyword = view.getTxtSearch().getText().toLowerCase();
        // Filter row tabel (Client Side) atau Panggil DAO search (Server Side)
        // Di sini kita pakai cara simple reload data lalu filter manual di DAO (idealnya buat method search di DAO)
        // (Untuk MVP, refresh data saja sudah cukup jika search kosong)
        loadData();
    }



}