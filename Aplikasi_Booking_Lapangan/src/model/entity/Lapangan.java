package model.entity;

public class Lapangan {
    private int fieldId;
    private String name;
    private String location;
    private String type;
    private double pricePerHour;
    private String status;

    public Lapangan() {}

    public Lapangan(int fieldId, String name, String location, String type, double pricePerHour, String status) {
        this.fieldId = fieldId;
        this.name = name;
        this.location = location;
        this.type = type;
        this.pricePerHour = pricePerHour;
        this.status = status;
    }

    public Lapangan(String name, String location, String type, double pricePerHour) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.pricePerHour = pricePerHour;
        this.status = "Available"; // Default status
    }

    public int getFieldId() { return fieldId; }
    public void setFieldId(int fieldId) { this.fieldId = fieldId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return this.name; // Atau variable nama lapangan Anda (misal: name_field / nama)
    }
}
