package view.kelolaUser;

import javax.swing.*;
import java.awt.*;

public class UserFormDialog extends JDialog {
    private JTextField txtNama;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JPasswordField txtPassword;
    private JComboBox<String> comboRole;
    private JButton btnSave, btnCancel;
    private boolean succeeded;

    public UserFormDialog(Frame parent) {
        super(parent, "Form Data User", true);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Input Components
        panel.add(new JLabel("Nama Lengkap:"));
        txtNama = new JTextField(20);
        panel.add(txtNama);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField(20);
        panel.add(txtEmail);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField(20);
        panel.add(txtPassword);

        panel.add(new JLabel("No. Telepon:"));
        txtPhone = new JTextField(20);
        panel.add(txtPhone);

        panel.add(new JLabel("Role Akses:"));
        String[] roles = {"Member", "Admin", "Karyawan", "Owner"};
        comboRole = new JComboBox<>(roles);
        panel.add(comboRole);

        // Buttons
        btnSave = new JButton("Simpan");
        btnCancel = new JButton("Batal");

        btnSave.addActionListener(e -> {
            succeeded = true;
            dispose();
        });

        btnCancel.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public void setForm(String nama, String email, String phone, String role) {
        this.txtNama.setText(nama);
        this.txtEmail.setText(email);
        this.txtPhone.setText(phone);
        this.comboRole.setSelectedItem(role);

        // Disable password karena saat edit, password tidak wajib diganti lewat sini
        disablePasswordEdit();
    }

    // Getter Inputan
    public String getNama() { return txtNama.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getPhone() { return txtPhone.getText().trim(); }
    public String getRole() { return (String) comboRole.getSelectedItem(); }

    // Setter (Mode Edit)
    public void setNama(String v) { txtNama.setText(v); }
    public void setEmail(String v) { txtEmail.setText(v); }
    public void setPassword(String v) { txtPassword.setText(v); }
    public void setPhone(String v) { txtPhone.setText(v); }
    public void setRole(String v) { comboRole.setSelectedItem(v); }

    public void disablePasswordEdit() {
        txtPassword.setEnabled(false);
        txtPassword.setText("unchanged");
    }

    public boolean isSucceeded() { return succeeded; }
}
