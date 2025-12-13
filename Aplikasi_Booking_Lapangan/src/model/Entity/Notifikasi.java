package model.Entity;

import java.sql.Timestamp;

public class Notifikasi {
    private int notificationId; // PK
    private int userId;         // FK ke User
    private String message;     // Isi Pesan
    private String type;        // Info, Warning, Alert
    private Timestamp sentAt;   // Waktu kirim

    public Notifikasi() {}

    public Notifikasi(int notificationId, int userId, String message, String type, Timestamp sentAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.sentAt = sentAt;
    }

    public Notifikasi(int userId, String message, String type) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.sentAt = new Timestamp(System.currentTimeMillis());
    }

    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Timestamp getSentAt() { return sentAt; }
    public void setSentAt(Timestamp sentAt) { this.sentAt = sentAt; }
}
