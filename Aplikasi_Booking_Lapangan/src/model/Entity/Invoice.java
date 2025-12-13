package model.Entity;

import java.sql.Date;

public class Invoice {
    private String invoiceNumber; // Format: INV-2025-001
    private Date issueDate;
    private double totalAmount;
    private String customerName;  // Nama Penyewa (Dari tabel User)
    private String fieldName;     // Nama Lapangan (Dari tabel Field)
    private String status;        // Lunas/Belum

    public Invoice() {}

    public Invoice(String invoiceNumber, Date issueDate, double totalAmount, String customerName, String fieldName, String status) {
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.customerName = customerName;
        this.fieldName = fieldName;
        this.status = status;
    }

    // Getter & Setter
    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

