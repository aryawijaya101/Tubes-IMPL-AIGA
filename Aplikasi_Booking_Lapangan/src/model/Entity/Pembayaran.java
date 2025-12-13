package model.Entity;

import java.sql.Timestamp;

public class Pembayaran {
    private int paymentId;
    private int bookingId;
    private Timestamp paymentDate;
    private double amount;
    private String method;
    private String status;

    public Pembayaran() {}

    public Pembayaran(int paymentId, int bookingId, Timestamp paymentDate, double amount, String method, String status) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public Pembayaran(int bookingId, double amount, String method) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.status = "Pending Verification";
        this.paymentDate = new Timestamp(System.currentTimeMillis());
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
