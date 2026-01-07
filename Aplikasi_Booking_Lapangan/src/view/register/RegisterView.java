package view.register;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    private JTextField txtNama, txtEmail, txtPhone;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBatal;

    public RegisterView() {
        setTitle("Daftar Akun Baru");
        setSize(400, 450); // Ukuran disesuaikan
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // HEADER
        JLabel lblTitle = new JLabel("Registrasi Member", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitle, BorderLayout.NORTH);

        // FORM PANEL
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15)); // 4 Baris (Nama, Email, Pass, HP)
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 1. Nama Lengkap
        formPanel.add(new JLabel("Nama Lengkap:"));
        txtNama = new JTextField();
        formPanel.add(txtNama);

        // 2. Email
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        // 3. Password
        formPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        // 4. No HP
        formPanel.add(new JLabel("No. Handphone:"));
        txtPhone = new JTextField();
        formPanel.add(txtPhone);

        add(formPanel, BorderLayout.CENTER);

        // TOMBOL PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout());

        btnRegister = new JButton("Daftar Sekarang");
        btnRegister.setBackground(new Color(39, 174, 96));
        btnRegister.setForeground(Color.WHITE);

        btnBatal = new JButton("Batal");
        btnBatal.setBackground(new Color(231, 76, 60));
        btnBatal.setForeground(Color.WHITE);

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBatal);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // GETTER
    public String getNama() { return txtNama.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getPhone() { return txtPhone.getText(); }

    public JButton getBtnRegister() { return btnRegister; }
    public JButton getBtnBatal() { return btnBatal; }
}
