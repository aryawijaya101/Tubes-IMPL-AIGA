package controller.login;

import model.entity.User;
import model.user.UserDAO;
import view.register.RegisterView;

import javax.swing.*;

public class RegisterController {
    private RegisterView view;
    private UserDAO dao;

    public RegisterController(RegisterView view) {
        this.view = view;
        this.dao = new UserDAO();

        initController();
    }

    private void initController() {
        // Tombol Daftar
        view.getBtnRegister().addActionListener(e -> prosesRegister());

        // Tombol Batal (Tutup window)
        view.getBtnBatal().addActionListener(e -> view.dispose());
    }

    private void prosesRegister() {
        // 1. Ambil data dari form
        String nama = view.getNama();
        String email = view.getEmail();
        String password = view.getPassword();
        String phone = view.getPhone();

        // 2. Validasi Input Kosong
        if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Harap isi semua kolom!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Buat Object User
        // Asumsi Constructor User: (id, nama, email, password, phone, role)
        // ID diisi 0 karena auto increment di DB
        User userBaru = new User(0, nama, email, password, phone, "Member");

        // 4. Simpan ke Database
        boolean sukses = dao.insertUser(userBaru);

        if (sukses) {
            JOptionPane.showMessageDialog(view, "Registrasi Berhasil! Silakan Login.");
            view.dispose(); // Tutup form register
        } else {
            JOptionPane.showMessageDialog(view, "Registrasi Gagal. Coba lagi atau cek koneksi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
