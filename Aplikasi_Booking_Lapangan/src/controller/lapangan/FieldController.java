package controller.lapangan;

import model.entity.Lapangan;
import model.Lapangan.LapanganDAO;
import view.Lapangan.ManageFieldView;
import view.Lapangan.FieldFormDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FieldController {
    private ManageFieldView view;
    private LapanganDAO dao;

    public FieldController(ManageFieldView view) {
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

        // Tombol Tambah
        view.getBtnAdd().addActionListener(e -> showAddDialog());

        // Tombol Edit
        view.getBtnEdit().addActionListener(e -> showEditDialog());

        // Tombol Delete
        view.getBtnDelete().addActionListener(e -> deleteLapangan());
        
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

    // TAMBAH DATA (CREATE)
    private void showAddDialog() {
        // Buka dialog form
        FieldFormDialog dialog = new FieldFormDialog(view);
        dialog.setVisible(true);

        // Setelah dialog ditutup, cek apakah user klik 'Simpan'
        if (dialog.isSucceeded()) {
            // Ambil data dari form inputan dialog
            Lapangan l = new Lapangan(
                dialog.getNama(),
                dialog.getLokasi(),
                dialog.getTipe(),
                dialog.getHarga()
                // Status default 'Available' diatur di Constructor Entity
            );
            l.setStatus(dialog.getStatus()); // Ambil status dari combo box

            // Kirim ke DAO
            if (dao.insertLapangan(l)) {
                JOptionPane.showMessageDialog(view, "Berhasil menambahkan lapangan!");
                loadData(); // Refresh tabel otomatis
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // EDIT DATA (UPDATE)
    private void showEditDialog() {
        // Cek baris mana yang dipilih user
        int selectedRow = view.getTableLapangan().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Pilih baris yang mau diedit dulu!");
            return;
        }

        // Ambil data dari tabel untuk mengisi form edit
        int id = (int) view.getTableLapangan().getValueAt(selectedRow, 0);
        String nama = (String) view.getTableLapangan().getValueAt(selectedRow, 1);
        String lokasi = (String) view.getTableLapangan().getValueAt(selectedRow, 2);
        String tipe = (String) view.getTableLapangan().getValueAt(selectedRow, 3);
        double harga = (double) view.getTableLapangan().getValueAt(selectedRow, 4);
        String status = (String) view.getTableLapangan().getValueAt(selectedRow, 5);

        // Buka dialog dan isi datanya
        FieldFormDialog dialog = new FieldFormDialog(view);
        dialog.setNama(nama);
        dialog.setLokasi(lokasi);
        dialog.setTipe(tipe);
        dialog.setHarga(harga);
        dialog.setStatus(status);
        dialog.setTitle("Edit Lapangan");
        dialog.setVisible(true);

        // Proses Simpan Perubahan
        if (dialog.isSucceeded()) {
            Lapangan l = new Lapangan(
                id, // ID Lama (PENTING untuk WHERE clause update)
                dialog.getNama(),
                dialog.getLokasi(),
                dialog.getTipe(),
                dialog.getHarga(),
                dialog.getStatus()
            );

            if (dao.updateLapangan(l)) {
                JOptionPane.showMessageDialog(view, "Data berhasil diupdate!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal update data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // HAPUS DATA (DELETE)
    private void deleteLapangan() {
        int selectedRow = view.getTableLapangan().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Pilih baris yang mau dihapus dulu!");
            return;
        }

        // Konfirmasi Hapus
        int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus lapangan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) view.getTableLapangan().getValueAt(selectedRow, 0);
            
            if (dao.deleteLapangan(id)) {
                JOptionPane.showMessageDialog(view, "Lapangan dihapus!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal hapus (Mungkin sedang ada booking aktif).", "Error", JOptionPane.ERROR_MESSAGE);
            }
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