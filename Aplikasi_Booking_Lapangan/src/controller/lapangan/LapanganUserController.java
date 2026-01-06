package controller.lapangan;

import model.entity.Lapangan;
import model.lapangan.LapanganDAO;
import view.lapangan.DaftarLapnganMember;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LapanganUserController {
    private DaftarLapnganMember view;
    private LapanganDAO dao;

    public LapanganUserController(DaftarLapnganMember view) {
        this.view = view;
        this.dao = new LapanganDAO();

        initController();
        loadData(); // Load data awal
    }

    private void initController() {
        // Tombol Refresh
        view.getBtnRefresh().addActionListener(e -> {
            view.getTxtSearch().setText(""); // Bersihkan kotak pencarian
            loadData();
        });

        // Tombol Search
        view.getBtnSearch().addActionListener(e -> processSearch());
    }

    // LOAD DATA (SEMUA)
    private void loadData() {
        List<Lapangan> list = dao.getAllLapangan();
        updateTable(list);
    }

    // === PERBAIKAN LOGIC SEARCH ===
    private void processSearch() {
        String keyword = view.getTxtSearch().getText().trim();

        if (keyword.isEmpty()) {
            loadData(); // Kalau kosong, tampilkan semua
        } else {
            // Panggil DAO cariLapangan yang sudah diperbaiki
            List<Lapangan> list = dao.cariLapangan(keyword);
            updateTable(list);
        }
    }

    // Method pembantu agar coding tidak berulang
    private void updateTable(List<Lapangan> list) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0); // Hapus data lama

        for (Lapangan l : list) {
            // PERBAIKAN: Tambahkan elemen ke-7 ("Booking")
            // karena di ListFieldView ada 7 kolom (kolom terakhir adalah tombol)
            Object[] row = {
                    l.getFieldId(),
                    l.getName(),
                    l.getLocation(),
                    l.getType(),
                    String.format("%.0f", l.getPricePerHour()), // Format harga biar rapi
                    l.getStatus(),
                    "Booking" // <--- INI PENTING AGAR TOMBOL MUNCUL
            };
            model.addRow(row);
        }
    }
}