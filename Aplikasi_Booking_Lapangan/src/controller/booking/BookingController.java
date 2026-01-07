package controller.booking;

import model.entity.Booking;
import model.booking.BookingDAO;
import model.entity.User;
import model.user.UserDAO;
import model.entity.Lapangan;
import model.lapangan.LapanganDAO;
import view.booking.KelolaBookingView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookingController {
    
    private KelolaBookingView view;
    private BookingDAO bookingDAO;
    private UserDAO userDAO;
    private LapanganDAO lapanganDAO;

    public BookingController(KelolaBookingView view) {
        this.view = view;
        this.bookingDAO = new BookingDAO();
        this.userDAO = new UserDAO();
        this.lapanganDAO = new LapanganDAO();

        initController();
        loadData();
    }

    private void initController() {
        // Refresh Data
        view.getBtnRefresh().addActionListener(e -> loadData());

        // Tombol Filter
        view.getBtnFilter().addActionListener(e -> loadData()); // Load data akan otomatis baca filter

        // Tombol Aksi Status
        view.getBtnConfirm().addActionListener(e -> updateStatus("Confirmed"));
        view.getBtnCancel().addActionListener(e -> updateStatus("Cancelled"));
        view.getBtnComplete().addActionListener(e -> updateStatus("Completed"));
    }

    // LOAD DATA (READ + JOIN MANUAL)
    private void loadData() {
        // Ambil filter status dari ComboBox
        String selectedStatus = (String) view.getComboFilterStatus().getSelectedItem();
        
        // Ambil semua data booking
        List<Booking> list = bookingDAO.getAllBookings();
        
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0); // Bersihkan tabel

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

        for (Booking b : list) {
            // FILTER CLIENT-SIDE
            // Jika filter bukan "Semua" DAN status booking tidak sama dengan filter -> Skip
            if (!selectedStatus.equals("Semua") && !b.getStatus().equalsIgnoreCase(selectedStatus)) {
                continue;
            }

            // Ambil Nama User (Relasi ke UserDAO)
            User u = userDAO.getUserById(b.getUserId());
            String namaUser = (u != null) ? u.getNama() : "Unknown (ID:" + b.getUserId() + ")";

            // Ambil Nama Lapangan (Relasi ke LapanganDAO)
            Lapangan l = lapanganDAO.getLapanganById(b.getFieldId());
            String namaLapangan = (l != null) ? l.getName() : "Unknown (ID:" + b.getFieldId() + ")";

            // Masukkan ke Tabel
            Object[] row = {
                b.getBookingId(),
                namaUser,           // Tampilkan Nama
                namaLapangan,       // Tampilkan Lapangan
                sdfDate.format(b.getBookingDate()),
                sdfTime.format(b.getStartTime()),
                sdfTime.format(b.getEndTime()),
                b.getTotalPrice(),
                b.getStatus()
            };
            model.addRow(row);
        }
    }

    // UPDATE STATUS
    private void updateStatus(String newStatus) {
        int selectedRow = view.getTableBooking().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Pilih booking dulu!");
            return;
        }

        // Ambil ID Booking dari kolom ke-0
        int bookingId = (int) view.getTableBooking().getValueAt(selectedRow, 0);
        String currentStatus = (String) view.getTableBooking().getValueAt(selectedRow, 7);

        // Validasi Logika Bisnis
        // Misal: Tidak bisa cancel kalau sudah Completed
        if (currentStatus.equals("Completed") && newStatus.equals("Cancelled")) {
            JOptionPane.showMessageDialog(view, "Booking yang sudah selesai tidak bisa dibatalkan!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
            "Ubah status booking menjadi " + newStatus + "?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (bookingDAO.updateStatus(bookingId, newStatus)) {
                JOptionPane.showMessageDialog(view, "Status berhasil diubah!");
                loadData(); // Refresh tabel
            } else {
                JOptionPane.showMessageDialog(view, "Gagal update status.");
            }
        }
    }
}