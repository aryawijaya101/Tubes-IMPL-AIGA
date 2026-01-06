package view.lapangan;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer; // Import ini
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RiwayatBooking extends JFrame {

    // Komponen GUI
    private JTable tableRiwayat;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private JTextField txtSearch;
    private JButton btnSearch;

    public RiwayatBooking() {
        setTitle("Riwayat Booking & Pembayaran Saya");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. PANEL ATAS
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

        // 2. PANEL TENGAH (TABEL)
        String[] columnNames = {
                "ID Booking", "Nama Lapangan", "Tanggal Main", "Waktu Main",
                "Total Bayar", "Metode", "Waktu Pembayaran"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            // Tetap pertahankan ini agar sorting angka 10, 11, 12 benar (tidak jadi 1, 10, 11, 2)
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Integer.class;
                return String.class;
            }
        };

        tableRiwayat = new JTable(tableModel);
        tableRiwayat.setRowHeight(30);
        tableRiwayat.setAutoCreateRowSorter(true); // Fitur Sorting Klik Header

        // =================================================================
        // --- SETTING ALIGNMENT (RATA TENGAH) UNTUK ID BOOKING ---
        // =================================================================
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // Ubah jadi JLabel.LEFT jika ingin rata kiri

        // Terapkan ke Kolom 0 (ID Booking)
        tableRiwayat.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // Opsional: Terapkan juga ke Kolom lain biar rapi (Misal Tanggal & Waktu)
        tableRiwayat.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Tanggal
        tableRiwayat.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Waktu Main

        // Atur Lebar Kolom
        tableRiwayat.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableRiwayat.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableRiwayat.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableRiwayat.getColumnModel().getColumn(6).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableRiwayat);
        add(scrollPane, BorderLayout.CENTER);

        // 3. PANEL BAWAH
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