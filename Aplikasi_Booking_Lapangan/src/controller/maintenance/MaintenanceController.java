package controller.maintenance;

import model.entity.Maintenance;
import model.maintenance.MaintenanceDAO;
import model.entity.Lapangan;
import model.lapangan.LapanganDAO;
import view.maintenance.MaintenanceView;
import view.maintenance.MaintenanceFormDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

public class MaintenanceController {
    private MaintenanceView view;
    private MaintenanceDAO maintenanceDAO;
    private LapanganDAO lapanganDAO; // Kita butuh ini untuk isi ComboBox Lapangan

    public MaintenanceController(MaintenanceView view) {
        this.view = view;
        this.maintenanceDAO = new MaintenanceDAO();
        this.lapanganDAO = new LapanganDAO(); // Inisialisasi DAO Lapangan

        initController();
        loadData();
    }

    private void initController() {
        view.getBtnRefresh().addActionListener(e -> loadData());
        view.getBtnAdd().addActionListener(e -> showAddDialog());
        view.getBtnEdit().addActionListener(e -> showEditDialog());
        view.getBtnDelete().addActionListener(e -> deleteMaintenance());
        view.getBtnSearch().addActionListener(e -> filterData());
    }

    // LOAD DATA (READ)
    private void loadData() {
        List<Maintenance> list = maintenanceDAO.getAllMaintenance();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        // Format tanggal untuk tampilan tabel (biar enak dibaca: dd-MM-yyyy)
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (Maintenance m : list) {
            // Kita butuh Nama Lapangan, tapi di object Maintenance cuma ada ID.
            // Kita ambil detail lapangan pakai LapanganDAO
            Lapangan l = lapanganDAO.getLapanganById(m.getFieldId());
            String namaLapangan = (l != null) ? l.getName() : "Unknown (ID:" + m.getFieldId() + ")";

            Object[] row = {
                m.getMaintenanceId(),
                namaLapangan,         // Tampilkan Nama, bukan ID
                sdf.format(m.getScheduledDate()),
                m.getDescription(),
                m.getStatus()
            };
            model.addRow(row);
        }
    }

    // TAMBAH DATA (CREATE)
    private void showAddDialog() {
        MaintenanceFormDialog dialog = new MaintenanceFormDialog(view);
        
        // ISI COMBOBOX dengan Data Lapangan dari Database
        List<Lapangan> fields = lapanganDAO.getAllLapangan();
        for (Lapangan l : fields) {
            dialog.getComboField().addItem(l);
            // Karena di Lapangan.java ada toString(), otomatis yang muncul adalah Namanya
        }

        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            try {
                // Parsing String ke Util Date dulu (Format Java Biasa)
                java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdfInput.parse(dialog.getDateInput());

                // KONVERSI Util Date -> SQL Date
                Date sqlDate = new Date(utilDate.getTime());

                // Masukkan sqlDate ke Constructor
                Maintenance m = new Maintenance(
                    dialog.getSelectedField().getFieldId(),
                    sqlDate,
                    dialog.getDescription()
                );
                m.setStatus(dialog.getStatus());

                // Simpan
                if (maintenanceDAO.insertMaintenance(m)) {
                    JOptionPane.showMessageDialog(view, "Jadwal berhasil disimpan!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(view, "Gagal simpan database.");
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(view, "Format Tanggal Salah! Gunakan YYYY-MM-DD (Contoh: 2025-12-31)", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // EDIT DATA (UPDATE)
    private void showEditDialog() {
        int selectedRow = view.getTableMaintenance().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Pilih jadwal yang mau diedit dulu!");
            return;
        }

        // Ambil ID dari tabel (Kolom ke-0)
        int id = (int) view.getTableMaintenance().getValueAt(selectedRow, 0);
        String currentDesc = (String) view.getTableMaintenance().getValueAt(selectedRow, 3);
        String currentStatus = (String) view.getTableMaintenance().getValueAt(selectedRow, 4);
        
        // Untuk tanggal dan fieldId, idealnya kita ambil object aslinya lagi dari list/dao
        // Tapi untuk simpel, kita minta user isi ulang atau kita parse dari tabel
        
        MaintenanceFormDialog dialog = new MaintenanceFormDialog(view);
        
        // Isi ComboBox Lapangan
        List<Lapangan> fields = lapanganDAO.getAllLapangan();
        for (Lapangan l : fields) {
            dialog.getComboField().addItem(l);
        }
        
        // Set Data Lama ke Form
        dialog.setDescription(currentDesc);
        dialog.setStatus(currentStatus);
        // (Set tanggal dan lapangan terpilih secara otomatis butuh logic tambahan parsing)

        dialog.setTitle("Edit Jadwal Maintenance");
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            try {
                // Parsing String ke Util Date dulu (Format Java Biasa)
                java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdfInput.parse(dialog.getDateInput());

                // KONVERSI Util Date -> SQL Date
                Date sqlDate = new Date(utilDate.getTime());

                Maintenance m = new Maintenance(
                    id, // ID Lama
                    dialog.getSelectedField().getFieldId(),
                    sqlDate,
                    dialog.getDescription(),
                    dialog.getStatus()
                );

                if (maintenanceDAO.updateMaintenance(m)) {
                    JOptionPane.showMessageDialog(view, "Jadwal berhasil diupdate!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(view, "Gagal update database.");
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(view, "Format Tanggal Salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // HAPUS DATA (DELETE)
    private void deleteMaintenance() {
        int selectedRow = view.getTableMaintenance().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Pilih jadwal yang mau dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Hapus jadwal ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) view.getTableMaintenance().getValueAt(selectedRow, 0);
            if (maintenanceDAO.deleteMaintenance(id)) {
                JOptionPane.showMessageDialog(view, "Data dihapus.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menghapus data.");
            }
        }
    }

    // FILTER / SEARCH
    private void filterData() {
        String keyword = view.getTxtSearch().getText().toLowerCase();
        
        // Ambil semua data dari DAO
        List<Maintenance> list = maintenanceDAO.getAllMaintenance();
        
        // Bersihkan tabel
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        // oop dan Filter secara manual (Client Side Filtering)
        for (Maintenance m : list) {
            // Cek apakah Deskripsi atau Status mengandung kata kunci
            // Kita pakai validasi null agar tidak error jika deskripsi kosong
            String deskripsi = (m.getDescription() != null) ? m.getDescription().toLowerCase() : "";
            String status = (m.getStatus() != null) ? m.getStatus().toLowerCase() : "";

            if (deskripsi.contains(keyword) || status.contains(keyword)) {
                
                // Ambil Nama Lapangan (Sama seperti loadData)
                Lapangan l = lapanganDAO.getLapanganById(m.getFieldId());
                String namaLapangan = (l != null) ? l.getName() : "Unknown";

                Object[] row = {
                    m.getMaintenanceId(),
                    namaLapangan,
                    sdf.format(m.getScheduledDate()),
                    m.getDescription(),
                    m.getStatus()
                };
                model.addRow(row);
            }
        }
    }
}