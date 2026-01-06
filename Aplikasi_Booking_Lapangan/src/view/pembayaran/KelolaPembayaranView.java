package view.pembayaran;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KelolaPembayaranView extends JFrame {
    private JTable tabelPembayaran;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private JButton btnCetak;
    private JLabel lblTotalPemasukan; // Label untuk menampilkan total uang masuk

    public KelolaPembayaranView() {
        setTitle("Laporan Keuangan / Data Pembayaran");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =================================================================
        // 1. PANEL ATAS (JUDUL & TOTAL)
        // =================================================================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        topPanel.setBackground(new Color(245, 245, 245));

        JLabel lblTitle = new JLabel("Riwayat Pemasukan");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));

        lblTotalPemasukan = new JLabel("Total Masuk: Rp 0");
        lblTotalPemasukan.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalPemasukan.setForeground(new Color(39, 174, 96)); // Warna Hijau Duit

        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(lblTotalPemasukan, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // =================================================================
        // 2. PANEL TENGAH (TABEL)
        // =================================================================
        // Kolom disesuaikan dengan Entity Pembayaran
        String[] kolom = {"ID Bayar", "ID Booking", "Tanggal Bayar", "Metode", "Status", "Jumlah (Rp)"};

        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Agar tidak bisa diedit sembarangan
            }
        };

        tabelPembayaran = new JTable(tableModel);
        tabelPembayaran.setRowHeight(30);

        // Atur lebar kolom biar rapi
        tabelPembayaran.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID Bayar
        tabelPembayaran.getColumnModel().getColumn(2).setPreferredWidth(150); // Tanggal
        tabelPembayaran.getColumnModel().getColumn(3).setPreferredWidth(120); // Metode

        JScrollPane scrollPane = new JScrollPane(tabelPembayaran);
        add(scrollPane, BorderLayout.CENTER);

        // =================================================================
        // 3. PANEL BAWAH (TOMBOL)
        // =================================================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Refresh Data");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);

        btnCetak = new JButton("Cetak Laporan (PDF)");
        btnCetak.setBackground(new Color(230, 126, 34));
        btnCetak.setForeground(Color.WHITE);

        bottomPanel.add(btnCetak);
        bottomPanel.add(btnRefresh);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // GETTER (PENTING: Agar Controller bisa akses komponen ini)
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }

    public JButton getBtnCetak() { return btnCetak; }
    public JTable getTabelPembayaran() { return tabelPembayaran; }

    // Setter untuk mengubah teks Total Pemasukan
    public void setTotalPemasukan(String text) {
        lblTotalPemasukan.setText(text);
    }
}
