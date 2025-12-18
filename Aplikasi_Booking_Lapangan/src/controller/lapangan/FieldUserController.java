package controller.lapangan;

import model.entity.Lapangan;
import model.lapangan.LapanganDAO;
import view.lapangan.ListFieldView;
import view.lapangan.RiwayatBooking;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FieldUserController {
    private ListFieldView view;
    private LapanganDAO dao;

    public FieldUserController(ListFieldView view) {
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

    // LOAD DATA (READ)
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

    // SEARCH (FILTER TABEL)
    private void searchLapangan() {
        String keyword = view.getTxtSearch().getText().toLowerCase();
        // Filter row tabel (Client Side) atau Panggil DAO search (Server Side)
        // Di sini kita pakai cara simple reload data lalu filter manual di DAO (idealnya buat method search di DAO)
        // (Untuk MVP, refresh data saja sudah cukup jika search kosong)
        loadData();
    }
}