package model.Entity;

import java.sql.Timestamp;

public class Rating     {
    private int ratingId;       // PK, Auto Increment
    private int bookingId;      // FK, Menunjuk ke booking mana yang dinilai
    private int score;          // Nilai bintang (1-5)
    private String review;      // Komentar teks
    private String status;      // Sesuai State Diagram Hal 30: 'Submitted', 'Published', 'Rejected'
    private Timestamp createdAt;


    public Rating() {
    }

    public Rating(int ratingId, int bookingId, int score, String review, String status, Timestamp createdAt) {
        this.ratingId = ratingId;
        this.bookingId = bookingId;
        this.score = score;
        this.review = review;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Rating(int bookingId, int score, String review) {
        this.bookingId = bookingId;
        this.score = score;
        this.review = review;
        this.status = "Submitted";
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getRatingId() { return ratingId; }
    public void setRatingId(int ratingId) { this.ratingId = ratingId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
