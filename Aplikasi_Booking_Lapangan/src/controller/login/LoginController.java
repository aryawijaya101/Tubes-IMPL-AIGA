package controller.login;

import controller.booking.BookingController;
import controller.booking.RiwayatBookingController;
import controller.lapangan.LapanganController;
import controller.lapangan.LapanganUserController;
import controller.maintenance.MaintenanceController;
import controller.pembayaran.PembayaranController;
import model.entity.User;
import model.login.LoginDAO;
import view.MainDashboard;
import view.booking.KelolaBookingView;
import view.lapangan.DaftarLapanganMember;
import view.lapangan.KelolaLapanganView;
import view.lapangan.RiwayatBooking;
import view.login.LoginView;
import view.maintenance.MaintenanceView;
import view.register.RegisterView;

import javax.swing.*;

public class LoginController {

    private LoginView loginView;
    private LoginDAO loginDAO;

    public LoginController(LoginView view) {
        this.loginView = view;
        this.loginDAO = new LoginDAO();

        initController();
    }

    private void initController() {
        loginView.getBtnLogin().addActionListener(e -> processLogin());
        loginView.getBtnRegister().addActionListener(e -> {
            RegisterView regView = new RegisterView();
            new RegisterController(regView);
            regView.setVisible(true);
        });
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
            loginView.dispose();
            openDashboard(user);
        } else {
            JOptionPane.showMessageDialog(loginView, "Email atau Password salah!", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openDashboard(User user) {
        MainDashboard dashboard = new MainDashboard(user);
        dashboard.setVisible(true);

        // LOGIC TOMBOL DASHBOARD

        // Lapangan (Admin/Owner)
        if (dashboard.getBtnKelolaLapangan() != null) {
            dashboard.getBtnKelolaLapangan().addActionListener(e -> {
                KelolaLapanganView view = new KelolaLapanganView();
                new LapanganController(view);
                view.setVisible(true);
            });
        }

        // Jadwal (Admin/Owner)
        if (dashboard.getBtnKelolaJadwal() != null) {
            dashboard.getBtnKelolaJadwal().addActionListener(e -> {
                MaintenanceView view = new MaintenanceView();
                new MaintenanceController(view);
                view.setVisible(true);
            });
        }

        // Booking (Admin)
        if (dashboard.getBtnKelolaBooking() != null) {
            dashboard.getBtnKelolaBooking().addActionListener(e -> {
                KelolaBookingView view = new KelolaBookingView();
                new BookingController(view);
                view.setVisible(true);
            });
        }

        // KHUSUS MEMBER - TAMPILKAN LIST LAPANGAN
        if (dashboard.getBtnTampilkanListLapangan() != null) {
            dashboard.getBtnTampilkanListLapangan().addActionListener(e -> {
                // UPDATE DI SINI: Masukkan 'user' ke dalam constructor
                DaftarLapanganMember view = new DaftarLapanganMember(user);

                new LapanganUserController(view); // Pastikan ini tidak error
                view.setVisible(true);
            });
        }

        // Tampilan Riwayat Booking
        if (dashboard.getBtnTampilkanRiwayatBooking() != null) {
            dashboard.getBtnTampilkanRiwayatBooking().addActionListener(e -> {
                RiwayatBooking view = new RiwayatBooking();

                // Tambahkan parameter 'user' di sini
                // Agar controller tahu history milik siapa yang harus diambil
                new RiwayatBookingController(view, user);

                view.setVisible(true);
            });
        }

        // Logout
        dashboard.getBtnLogout().addActionListener(e -> {
            dashboard.dispose();
            LoginView newLoginView = new LoginView();
            new LoginController(newLoginView);
            newLoginView.setVisible(true);
        });

        if (dashboard.getBtnKelolaPembayaran() != null) {
            dashboard.getBtnKelolaPembayaran().addActionListener(e -> {
                // Panggil View dan Controller Pembayaran
                view.pembayaran.KelolaPembayaranView view = new view.pembayaran.KelolaPembayaranView();
                new PembayaranController(view);
                view.setVisible(true);
            });
        }
    }
}