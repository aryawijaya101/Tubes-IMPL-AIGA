package view.login;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    // Constructor tetap sama seperti sebelumnya...
    public LoginView() {
        setTitle("Login System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Judul
        JLabel lblTitle = new JLabel("LOGIN APLIKASI");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Input Email
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        add(txtEmail, gbc);

        // Input Password
        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        // Tombol
        btnLogin = new JButton("Masuk");
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(btnLogin, gbc);

        // tombol register
        btnRegister = new JButton("Belum punya akun? Daftar disini");
        btnRegister.setBorderPainted(false);
        btnRegister.setContentAreaFilled(false);
        btnRegister.setFocusPainted(false);
        btnRegister.setForeground(Color.BLUE);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // layout tombol register
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(btnRegister, gbc);
    }

    public String getEmail() { return txtEmail.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnRegister() { return btnRegister; }
}
