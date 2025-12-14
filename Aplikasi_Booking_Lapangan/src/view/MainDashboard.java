package view;

import model.entity.User;

import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {
    private JButton btnKelolaLapangan, btnKelolaJadwal, btnKelolaBooking, btnLaporan, btnLogout;
    private JLabel lblWelcome;

    public MainDashboard(User user) {
        setTitle("Dashboard - " + user.getRole());
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.setBackground(Color.DARK_GRAY);
        lblWelcome = new JLabel("Selamat Datang, " + user.getNama() + " (" + user.getRole() + ")");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(lblWelcome);
        add(header, BorderLayout.NORTH);

        // Menu Panel (Grid tombol)
        JPanel menuPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        btnKelolaLapangan = new JButton("Kelola Lapangan");
        btnKelolaJadwal = new JButton("Kelola Maintenance");
        btnKelolaBooking = new JButton("Data Booking");
        btnLaporan = new JButton("Laporan Keuangan");
        btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);

        // Logic Tampilan Berdasarkan Role (SRS: Akses Dashboard sesuai peran)
        if (user.getRole().equalsIgnoreCase("Admin")) {
            menuPanel.add(btnKelolaLapangan);
            menuPanel.add(btnKelolaJadwal);
            menuPanel.add(btnKelolaBooking);
            menuPanel.add(btnLaporan);
        } else if (user.getRole().equalsIgnoreCase("Karyawan")) {
            menuPanel.add(btnKelolaLapangan);
            menuPanel.add(btnKelolaJadwal);
        } else {
            // Member/Guest
            menuPanel.add(new JLabel("Menu Member belum dibuat"));
        }

        menuPanel.add(btnLogout);
        add(menuPanel, BorderLayout.CENTER);
    }

    // Getter Buttons
    public JButton getBtnKelolaLapangan() { return btnKelolaLapangan; }
    public JButton getBtnKelolaJadwal() { return btnKelolaJadwal; }
    public JButton getBtnKelolaBooking() { return btnKelolaBooking; }
    public JButton getBtnLogout() { return btnLogout; }
}
