package controller.user;

import model.user.UserDAO;
import model.entity.User;
import view.user.ManageUserView;
import view.user.UserFormDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ManageUserController {
    private ManageUserView view; // <--- Ubah tipe data
    private UserDAO dao;

    public ManageUserController(ManageUserView view) { // <--- Ubah parameter
        this.view = view;
        this.dao = new UserDAO();

        initController();
        loadData();
    }

    private void initController() {
        view.getBtnRefresh().addActionListener(e -> loadData());
        view.getBtnAdd().addActionListener(e -> addUser());
        view.getBtnEdit().addActionListener(e -> editUser());
        view.getBtnDelete().addActionListener(e -> deleteUser());
        view.getBtnSearch().addActionListener(e -> searchUser());
    }

    // --- LOGIC: LOAD DATA ---
    private void loadData() {
        List<User> list = dao.getAllUsers();
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (User u : list) {
            model.addRow(new Object[]{
                    u.getUserId(), u.getNama(), u.getEmail(), u.getPhone(), u.getRole()
            });
        }
    }

    // --- LOGIC: TAMBAH USER ---
    private void addUser() {
        UserFormDialog dialog = new UserFormDialog(view);
        dialog.setTitle("Tambah User Baru");
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            User newUser = new User(
                    dialog.getNama(), dialog.getEmail(), dialog.getPassword(),
                    dialog.getPhone(), dialog.getRole()
            );

            if (dao.insertUser(newUser)) {
                JOptionPane.showMessageDialog(view, "User berhasil ditambahkan!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menambah user.");
            }
        }
    }

    // --- LOGIC: EDIT USER ---
    private void editUser() {
        int row = view.getTableUser().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih user yang mau diedit!");
            return;
        }

        // Ambil data dari tabel
        int id = (int) view.getTableUser().getValueAt(row, 0);
        String nama = (String) view.getTableUser().getValueAt(row, 1);
        String email = (String) view.getTableUser().getValueAt(row, 2);
        String phone = (String) view.getTableUser().getValueAt(row, 3);
        String role = (String) view.getTableUser().getValueAt(row, 4);

        UserFormDialog dialog = new UserFormDialog(view);
        dialog.setForm(nama, email, phone, role);
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            User updatedUser = new User(
                    id, dialog.getNama(), dialog.getEmail(),
                    "", // Password tidak berubah
                    dialog.getPhone(), dialog.getRole()
            );

            if (dao.updateUser(updatedUser)) {
                JOptionPane.showMessageDialog(view, "User berhasil diupdate!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal update.");
            }
        }
    }

    // --- LOGIC: HAPUS USER ---
    private void deleteUser() {
        int row = view.getTableUser().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih user yang mau dihapus!");
            return;
        }

        int id = (int) view.getTableUser().getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus user ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteUser(id)) {
                JOptionPane.showMessageDialog(view, "User dihapus.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal hapus.");
            }
        }
    }

    // --- LOGIC: SEARCH ---
    private void searchUser() {
        String keyword = view.getTxtSearch().getText().toLowerCase();
        // (Bisa implementasi filter manual di sini jika mau)
        loadData(); // Reset data dulu
    }
}
