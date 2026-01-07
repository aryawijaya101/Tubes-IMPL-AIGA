package view.booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KelolaBookingView extends JFrame {

    private JTable tableBooking;
    private DefaultTableModel tableModel;
    private JButton btnRefresh, btnConfirm, btnCancel, btnComplete;
    private JComboBox<String> comboFilterStatus;
    private JButton btnFilter;

    public KelolaBookingView() {
        setTitle("Kelola Data Booking (Admin)");
        setSize(1000, 600); // Lebih lebar karena kolomnya banyak
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // PANEL ATAS (FILTER)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel("Daftar Booking Masuk");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Filter berdasarkan Status
        String[] statuses = {"Semua", "Pending", "Confirmed", "Cancelled", "Completed"};
        comboFilterStatus = new JComboBox<>(statuses);
        btnFilter = new JButton("Filter");

        topPanel.add(lblTitle);
        topPanel.add(Box.createHorizontalStrut(50));
        topPanel.add(new JLabel("Status:"));
        topPanel.add(comboFilterStatus);
        topPanel.add(btnFilter);

        add(topPanel, BorderLayout.NORTH);

        // PANEL TENGAH (TABEL)
        // Kolom sesuai Entity Booking + Nama User + Nama Lapangan
        String[] columnNames = {
            "ID", "Customer", "Lapangan", "Tanggal", "Jam Mulai", "Jam Selesai", "Total (Rp)", "Status"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableBooking = new JTable(tableModel);
        tableBooking.setRowHeight(25);
        
        // Atur lebar kolom agar rapi
        tableBooking.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tableBooking.getColumnModel().getColumn(1).setPreferredWidth(120); // Customer
        tableBooking.getColumnModel().getColumn(2).setPreferredWidth(120); // Lapangan
        tableBooking.getColumnModel().getColumn(3).setPreferredWidth(80);  // Tanggal
        
        JScrollPane scrollPane = new JScrollPane(tableBooking);
        add(scrollPane, BorderLayout.CENTER);

        // PANEL BAWAH (TOMBOL AKSI)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnRefresh = new JButton("Refresh Data");
        
        btnConfirm = new JButton("Konfirmasi"); // Ubah status jadi Confirmed
        btnConfirm.setBackground(new Color(46, 204, 113)); // Hijau
        btnConfirm.setForeground(Color.WHITE);

        btnCancel = new JButton("Batalkan"); // Ubah status jadi Cancelled
        btnCancel.setBackground(new Color(231, 76, 60)); // Merah
        btnCancel.setForeground(Color.WHITE);
        
        btnComplete = new JButton("Selesai Main"); // Ubah status jadi Completed
        btnComplete.setBackground(new Color(52, 152, 219)); // Biru
        btnComplete.setForeground(Color.WHITE);

        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnConfirm);
        bottomPanel.add(btnComplete);
        bottomPanel.add(btnCancel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // GETTER
    public JTable getTableBooking() { return tableBooking; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnConfirm() { return btnConfirm; }
    public JButton getBtnCancel() { return btnCancel; }
    public JButton getBtnComplete() { return btnComplete; }
    public JComboBox<String> getComboFilterStatus() { return comboFilterStatus; }
    public JButton getBtnFilter() { return btnFilter; }
}