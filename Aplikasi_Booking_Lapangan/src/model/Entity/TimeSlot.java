package model.Entity;

import java.sql.Time;

public class TimeSlot {
    private Time jamMulai;
    private Time jamSelesai;
    private boolean available;

    public TimeSlot() {}

    public TimeSlot(Time jamMulai, Time jamSelesai, boolean available) {
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.available = available;
    }

    public int getDuration() {
        // Mengubah selisih waktu menjadi jam (int)
        long diff = jamSelesai.getTime() - jamMulai.getTime();
        return (int) (diff / (1000 * 60 * 60));
    }

    public Time getJamMulai() { return jamMulai; }
    public void setJamMulai(Time jamMulai) { this.jamMulai = jamMulai; }

    public Time getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(Time jamSelesai) { this.jamSelesai = jamSelesai; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return jamMulai + " - " + jamSelesai + (available ? " (Available)" : " (Booked)");
    }
}
