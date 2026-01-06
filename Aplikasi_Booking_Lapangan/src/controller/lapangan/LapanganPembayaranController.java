package controller.lapangan;

import model.entity.Booking;
import model.entity.Pembayaran;
import model.entity.User;
import model.booking.BookingDAO;
import model.pembayaran.PembayaranDAO;
import view.lapangan.PembayaranLapangan;

import javax.swing.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LapanganPembayaranController {

    private PembayaranLapangan view;
    private User currentUser;
    private int fieldId;
    private BookingDAO bookingDAO;
    private PembayaranDAO pembayaranDAO;

    public LapanganPembayaranController(PembayaranLapangan view, User user, int fieldId) {
        this.view = view;
        this.currentUser = user;
        this.fieldId = fieldId;
        this.bookingDAO = new BookingDAO();
        this.pembayaranDAO = new PembayaranDAO();

        // Pasang aksi tombol Bayar ke method processPayment
        this.view.getBtnPay().addActionListener(e -> processPayment());
    }

    private void processPayment() {
        try {
            // 1. Ambil Data dari View
            String dateStr = view.getDateInput(); // YYYY-MM-DD
            String startStr = view.getStartTime(); // HH:mm
            String endStr = view.getEndTime();     // HH:mm
            double total = view.getTotalPrice();
            String method = view.getPaymentMethod();

            if (total <= 0) {
                JOptionPane.showMessageDialog(view, "Jam tidak valid!");
                return;
            }

            // 2. Konversi Waktu
            SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dateStart = sdfFull.parse(dateStr + " " + startStr);
            Date dateEnd = sdfFull.parse(dateStr + " " + endStr);
            Date dateOnly = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            Timestamp tsStart = new Timestamp(dateStart.getTime());
            Timestamp tsEnd = new Timestamp(dateEnd.getTime());

            // 3. Buat Object Booking
            Booking newBooking = new Booking();
            newBooking.setUserId(currentUser.getUserId());
            newBooking.setFieldId(fieldId);
            newBooking.setBookingDate(new java.sql.Date(dateOnly.getTime()));
            newBooking.setStartTime(tsStart);
            newBooking.setEndTime(tsEnd);
            newBooking.setTotalPrice(total);
            newBooking.setStatus("Confirmed");

            // Simpan Booking
            if (bookingDAO.insertBooking(newBooking)) {

                // 4. Ambil ID Booking yang baru dibuat
                int bookingId = bookingDAO.getLastBookingIdByUser(currentUser.getUserId());

                // 5. Buat Object Pembayaran
                Pembayaran newPay = new Pembayaran();
                newPay.setBookingId(bookingId);
                newPay.setAmount(total);
                newPay.setMethod(method);
                newPay.setPaymentDate(new Timestamp(System.currentTimeMillis()));
                newPay.setStatus("Paid  ");

                // Simpan Pembayaran
                if (pembayaranDAO.insertPembayaran(newPay)) {
                    JOptionPane.showMessageDialog(view, "Pembayaran Berhasil! Data Tersimpan.");
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Booking sukses, tapi pembayaran gagal disimpan.");
                }

            } else {
                JOptionPane.showMessageDialog(view, "Gagal Booking! Mungkin jadwal bentrok.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
}