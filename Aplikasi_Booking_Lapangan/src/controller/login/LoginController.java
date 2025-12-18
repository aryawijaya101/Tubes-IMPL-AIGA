package controller.login;

import controller.booking.BookingController;
import controller.lapangan.FieldController;
import controller.maintenance.MaintenanceController;
import model.entity.User;
import model.login.LoginDAO;
import view.MainDashboard;
import view.booking.ManageBookingView;
import view.lapangan.ManageFieldView;
import view.login.LoginView;
import view.maintenance.MaintenanceView;

import javax.swing.*;

public class LoginController {

    private LoginView loginView;
    private LoginDAO loginDAO;

    public LoginController(LoginView view) {
        this.loginView = view;
        this.loginDAO = new LoginDAO(); // Pakai DAO khusus Login

        // Listener Tombol
        this.loginView.getBtnLogin().addActionListener(e -> processLogin());
    }

    private void processLogin() {
        String email = loginView.getEmail();
        String pass = loginView.getPassword();

        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Mohon isi email dan password!");
            return;
        }

        User user = loginDAO.login(email, pass);

        if (user != null) {
            JOptionPane.showMessageDialog(loginView, "Login Berhasil! Halo, " + user.getNama());
            loginView.dispose(); // Tutup jendela login
            openDashboard(user); // Buka dashboard
        } else {
            JOptionPane.showMessageDialog(loginView, "Email atau Password salah!", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openDashboard(User user) {
        MainDashboard dashboard = new MainDashboard(user);
        dashboard.setVisible(true);

        // --- LOGIC TOMBOL DASHBOARD ---

        // 1. Lapangan
        if (dashboard.getBtnKelolaLapangan() != null) {
            dashboard.getBtnKelolaLapangan().addActionListener(e -> {
                ManageFieldView view = new ManageFieldView();
                new FieldController(view);
                view.setVisible(true);
            });
        }

        // 2. Jadwal
        if (dashboard.getBtnKelolaJadwal() != null) {
            dashboard.getBtnKelolaJadwal().addActionListener(e -> {
                MaintenanceView view = new MaintenanceView();
                new MaintenanceController(view);
                view.setVisible(true);
            });
        }

        // 3. Booking
        if (dashboard.getBtnKelolaBooking() != null) {
            dashboard.getBtnKelolaBooking().addActionListener(e -> {
                ManageBookingView view = new ManageBookingView();
                new BookingController(view);
                view.setVisible(true);
            });
        }

        // 4. Logout (PENTING: Arahkan balik ke LoginController yang baru)
        dashboard.getBtnLogout().addActionListener(e -> {
            dashboard.dispose();
            // Kembali ke Login
            LoginView newLoginView = new LoginView();
            new LoginController(newLoginView); // Panggil Controller ini lagi
            newLoginView.setVisible(true);
        });
    }
}
