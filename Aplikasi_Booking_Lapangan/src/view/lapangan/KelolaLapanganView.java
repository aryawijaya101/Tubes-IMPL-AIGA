package view.lapangan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KelolaLapanganView extends JFrame {
    // Komponen GUI
    private JTable tableLapangan;
    private DefaultTableModel tableModel;
    private JButton btnRefresh, btnAdd, btnEdit, btnDelete;
    private JTextField txtSearch;
    private JButton btnSearch;

    public KelolaLapanganView() {
        setTitle("Kelola Data Lapangan (Admin/Karyawan)");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Agar tidak menutup seluruh aplikasi
        setLocationRelativeTo(null); // Posisi di tengah layar
        setLayout(new BorderLayout());

        // PANEL ATAS (JUDUL & SEARCH)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel("Daftar Lapangan Olahraga");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Cari");

        topPanel.add(lblTitle);
        topPanel.add(Box.createHorizontalStrut(50)); // Spasi
        topPanel.add(new JLabel("Cari Nama:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // PANEL TENGAH (TABEL DATA)
        // Kolom sesuai Entity Lapangan
        String[] columnNames = {"ID", "Nama Lapangan", "Lokasi", "Tipe", "Harga/Jam", "Status"};
        
        // Model tabel agar tidak bisa diedit langsung di sel-nya
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tableLapangan = new JTable(tableModel);
        tableLapangan.setRowHeight(25); // Supaya baris agak renggang enak dibaca
        JScrollPane scrollPane = new JScrollPane(tableLapangan);
        
        add(scrollPane, BorderLayout.CENTER);

        // PANEL BAWAH (TOMBOL AKSI)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnRefresh = new JButton("Refresh Data");
        btnAdd = new JButton("Tambah Lapangan");
        btnEdit = new JButton("Edit Lapangan");
        btnDelete = new JButton("Hapus Lapangan");
        
        // Warna tombol (Opsional, biar bagus dikit)
        btnAdd.setBackground(new Color(46, 204, 113)); // Hijau
        btnAdd.setForeground(Color.WHITE);
        
        btnDelete.setBackground(new Color(231, 76, 60)); // Merah
        btnDelete.setForeground(Color.WHITE);

        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // GETTER (Supaya Controller bisa akses komponen ini)
    public JTable getTableLapangan() { return tableLapangan; }
    public DefaultTableModel getTableModel() { return tableModel; }
    
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
}
