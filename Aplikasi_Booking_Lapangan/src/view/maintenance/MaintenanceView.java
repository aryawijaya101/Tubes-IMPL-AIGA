package view.maintenance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MaintenanceView extends JFrame {

    private JTable tableMaintenance;
    private DefaultTableModel tableModel;
    private JButton btnRefresh, btnAdd, btnEdit, btnDelete;
    private JTextField txtSearch;
    private JButton btnSearch;

    public MaintenanceView() {
        setTitle("Kelola Jadwal Maintenance & Operasional");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // PANEL ATAS (JUDUL + SEARCH)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel("Jadwal Perawatan Lapangan");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        txtSearch = new JTextField(15);
        btnSearch = new JButton("Filter Tanggal"); // Bisa dikembangkan jadi filter tanggal

        topPanel.add(lblTitle);
        topPanel.add(Box.createHorizontalStrut(30));
        topPanel.add(new JLabel("Cari:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // PANEL TENGAH (TABEL)
        // Kolom sesuai Entity Maintenance + Nama Lapangan
        String[] columnNames = {"ID", "Nama Lapangan", "Tanggal", "Deskripsi", "Status"};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableMaintenance = new JTable(tableModel);
        tableMaintenance.setRowHeight(25);
        
        // Sedikit styling kolom ID agar kecil
        tableMaintenance.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableMaintenance.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableMaintenance.getColumnModel().getColumn(3).setPreferredWidth(300); // Deskripsi lebar

        JScrollPane scrollPane = new JScrollPane(tableMaintenance);
        add(scrollPane, BorderLayout.CENTER);

        // PANEL BAWAH (TOMBOL)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnRefresh = new JButton("Refresh");
        btnAdd = new JButton("Jadwal Baru");
        btnEdit = new JButton("Edit Jadwal");
        btnDelete = new JButton("Hapus Jadwal");
        
        btnAdd.setBackground(new Color(52, 152, 219)); // Biru
        btnAdd.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(231, 76, 60)); // Merah
        btnDelete.setForeground(Color.WHITE);

        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // GETTER
    public JTable getTableMaintenance() { return tableMaintenance; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
}
