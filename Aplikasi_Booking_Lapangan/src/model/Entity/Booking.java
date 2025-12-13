package model.entity;

import java.sql.Timestamp;
import java.sql.Date;

public class booking {
    private int bookingId;
    private int userId;
    private int fieldId;
    private Date bookingDate;
    private Timestamp startTime;
    private Timestamp endTime;
    private double totalPrice;
    private String status;


    public booking() {}


    public booking(int bookingId, int userId, int fieldId, Date bookingDate, Timestamp startTime, Timestamp endTime, double totalPrice, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.fieldId = fieldId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public booking(int userId, int fieldId, Date bookingDate, Timestamp startTime, Timestamp endTime, double totalPrice) {
        this.userId = userId;
        this.fieldId = fieldId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.status = "Pending";
    }


    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getFieldId() { return fieldId; }
    public void setFieldId(int fieldId) { this.fieldId = fieldId; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public Timestamp getStartTime() { return startTime; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }

    public Timestamp getEndTime() { return endTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
