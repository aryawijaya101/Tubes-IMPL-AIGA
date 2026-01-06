package view.lapangan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RiwayatBooking extends JFrame {

    // Komponen GUI
    private JTable tableRiwayat;
    private DefaultTableModel tableModel;
    private JButton btnRefresh; // Tombol CRUD dihapus karena ini halaman history
    private JTextField txtSearch;
    private JButton btnSearch;

    public RiwayatBooking() {
        setTitle("Riwayat Booking & Pembayaran Saya");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =================================================================
        // 1. PANEL ATAS (JUDUL & SEARCH)
        // =================================================================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel("Daftar Transaksi Berhasil");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        txtSearch = new JTextField(20);
        btnSearch = new JButton("Cari ID/Nama");

        topPanel.add(lblTitle);
        topPanel.add(Box.createHorizontalStrut(50));
        topPanel.add(new JLabel("Cari:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // =================================================================
        // 2. PANEL TENGAH (TABEL DATA)
        // =================================================================
        // PERUBAHAN KOLOM SESUAI REQUEST:
        // 1. "Status" diganti "Waktu Main" (Jam Mulai - Selesai)
        // 2. Ditambah "Waktu Pembayaran" di akhir
        String[] columnNames = {
                "ID Booking",
                "Nama Lapangan",
                "Tanggal Main",
                "Waktu Main",      // Ex: "08:00 - 10:00"
                "Total Bayar",
                "Metode",
                "Waktu Pembayaran" // Timestamp pembayaran dilakukan
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Data riwayat tidak bisa diedit langsung
            }
        };

        tableRiwayat = new JTable(tableModel);
        tableRiwayat.setRowHeight(30); // Sedikit lebih tinggi biar rapi

        // Atur lebar kolom agar proporsional
        tableRiwayat.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tableRiwayat.getColumnModel().getColumn(1).setPreferredWidth(150); // Nama Lapangan
        tableRiwayat.getColumnModel().getColumn(3).setPreferredWidth(100); // Waktu Main
        tableRiwayat.getColumnModel().getColumn(6).setPreferredWidth(150); // Waktu Pembayaran

        JScrollPane scrollPane = new JScrollPane(tableRiwayat);
        add(scrollPane, BorderLayout.CENTER);

        // =================================================================
        // 3. PANEL BAWAH
        // =================================================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnRefresh = new JButton("Refresh Data");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);

        bottomPanel.add(btnRefresh);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // GETTER
    public JTable getTableRiwayat() { return tableRiwayat; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
}