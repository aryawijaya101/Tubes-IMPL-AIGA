package model.entity;

import java.sql.Date;

public class Maintenance {
    private int maintenanceId;  // PK
    private int fieldId;        // FK ke Lapangan
    private Date scheduledDate; // Tanggal perbaikan
    private String description; // Keterangan perbaikan
    private String status;      // Scheduled, In Progress, Completed

    // 1. Constructor Kosong
    public Maintenance() {}

    // 2. Constructor Lengkap (Dari Database)
    public Maintenance(int maintenanceId, int fieldId, Date scheduledDate, String description, String status) {
        this.maintenanceId = maintenanceId;
        this.fieldId = fieldId;
        this.scheduledDate = scheduledDate;
        this.description = description;
        this.status = status;
    }

    // 3. Constructor Insert (Jadwal Baru)
    public Maintenance(int fieldId, Date scheduledDate, String description) {
        this.fieldId = fieldId;
        this.scheduledDate = scheduledDate;
        this.description = description;
        this.status = "Scheduled";
    }

    // Getter & Setter
    public int getMaintenanceId() { return maintenanceId; }
    public void setMaintenanceId(int maintenanceId) { this.maintenanceId = maintenanceId; }

    public int getFieldId() { return fieldId; }
    public void setFieldId(int fieldId) { this.fieldId = fieldId; }

    public Date getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(Date scheduledDate) { this.scheduledDate = scheduledDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
