package controller.lapangan;

import model.entity.Lapangan;
import model.lapangan.LapanganDAO;
import view.lapangan.ManageFieldView;
import view.lapangan.FieldFormDialog;

// IMPORT TAMBAHAN
import model.booking.BookingDAO;
import model.entity.Booking;
import model.maintenance.MaintenanceDAO;
import model.entity.Maintenance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.sql.Timestamp; // <--- PENTING: Import Timestamp

public class LapanganController {
    private ManageFieldView view;
    private LapanganDAO dao;

    public LapanganController(ManageFieldView view) {
        this.view = view;
        this.dao = new LapanganDAO();

        initController();
        loadData();
    }

    private void initController() {
        view.getBtnRefresh().addActionListener(e -> {
            view.getTxtSearch().setText("");
            loadData();
        });
        view.getBtnAdd().addActionListener(e -> showAddDialog());
        view.getBtnEdit().addActionListener(e -> showEditDialog());
        view.getBtnDelete().addActionListener(e -> deleteLapangan());
        view.getBtnSearch().addActionListener(e -> processSearch());
    }

    private void loadData() {
        List<Lapangan> list = dao.getAllLapangan();
        updateTable(list);
    }

    private void updateTable(List<Lapangan> list) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

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

    private void showAddDialog() {
        FieldFormDialog dialog = new FieldFormDialog(view);
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            Lapangan l = new Lapangan(
                    dialog.getNama(),
                    dialog.getLokasi(),
                    dialog.getTipe(),
                    dialog.getHarga()
            );
            l.setStatus(dialog.getStatus());

            // Gunakan method return ID
            int newFieldId = dao.insertLapanganReturnId(l);

            if (newFieldId > 0) {
                // LOGIKA OTOMATISASI
                if (l.getStatus().equalsIgnoreCase("Maintenance")) {
                    addAutoMaintenance(newFieldId);
                }
                else if (l.getStatus().equalsIgnoreCase("Booked")) {
                    addAutoBooking(newFieldId, l.getPricePerHour());
                }

                JOptionPane.showMessageDialog(view, "Berhasil menambahkan lapangan!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditDialog() {
        int selectedRow = view.getTableLapangan().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Pilih baris yang mau diedit dulu!");
            return;
        }

        // Ambil data lama dari tabel
        int id = (int) view.getTableLapangan().getValueAt(selectedRow, 0);
        String nama = (String) view.getTableLapangan().getValueAt(selectedRow, 1);
        String lokasi = (String) view.getTableLapangan().getValueAt(selectedRow, 2);
        String tipe = (String) view.getTableLapangan().getValueAt(selectedRow, 3);
        double harga = Double.parseDouble(view.getTableLapangan().getValueAt(selectedRow, 4).toString());
        String oldStatus = (String) view.getTableLapangan().getValueAt(selectedRow, 5); // Simpan Status Lama

        // Buka Form Edit
        FieldFormDialog dialog = new FieldFormDialog(view);
        dialog.setNama(nama);
        dialog.setLokasi(lokasi);
        dialog.setTipe(tipe);
        dialog.setHarga(harga);
        dialog.setStatus(oldStatus);
        dialog.setTitle("Edit Lapangan");
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            // Ambil Status Baru
            String newStatus = dialog.getStatus();

            Lapangan l = new Lapangan(
                    id,
                    dialog.getNama(),
                    dialog.getLokasi(),
                    dialog.getTipe(),
                    dialog.getHarga(),
                    newStatus
            );

            if (dao.updateLapangan(l)) {
                // LOGIKA OTOMATISASI SAAT EDIT

                if (!oldStatus.equalsIgnoreCase("Maintenance") && newStatus.equalsIgnoreCase("Maintenance")) {
                    addAutoMaintenance(id);
                }

                else if (!oldStatus.equalsIgnoreCase("Booked") && newStatus.equalsIgnoreCase("Booked")) {
                    addAutoBooking(id, l.getPricePerHour());
                }

                JOptionPane.showMessageDialog(view, "Data berhasil diupdate!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal update data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteLapangan() {
        int selectedRow = view.getTableLapangan().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Pilih baris yang mau dihapus dulu!");
            return;
        }

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

    private void processSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        if (keyword.isEmpty()) {
            loadData();
        } else {
            List<Lapangan> list = dao.cariLapangan(keyword);
            updateTable(list);
        }
    }

    // HELPER METHODS (AUTO INSERT)
    private void addAutoMaintenance(int fieldId) {
        try {
            MaintenanceDAO maintDAO = new MaintenanceDAO();
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

            Maintenance m = new Maintenance(
                    0, fieldId, today, "Perbaikan Awal (Otomatis)", "In Progress"
            );

            maintDAO.insertMaintenance(m);
        } catch (Exception e) {
            System.err.println("Gagal Auto Maintenance: " + e.getMessage());
        }
    }

    private void addAutoBooking(int fieldId, double price) {
        try {
            BookingDAO bookingDAO = new BookingDAO();
            long currentMillis = System.currentTimeMillis();

            java.sql.Date today = new java.sql.Date(currentMillis);

            // UBAH JADI TIMESTAMP (Agar cocok dengan Entity Booking asli kamu)
            Timestamp now = new Timestamp(currentMillis);
            Timestamp later = new Timestamp(currentMillis + (3600 * 1000)); // +1 jam

            int adminUserId = 1;

            Booking b = new Booking(
                    0, adminUserId, fieldId, today, now, later, price, "Confirmed"
            );

            bookingDAO.insertBooking(b);
        } catch (Exception e) {
            System.err.println("Gagal Auto Booking: " + e.getMessage());
            e.printStackTrace();
        }
    }
}