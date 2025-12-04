USE field_booking_db;
DROP TABLE IF EXISTS fields_db;

CREATE TABLE fields_db(
    field_id INT(15) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name_field VARCHAR(100) NOT NULL,
    type_field VARCHAR(50) NOT NULL,
    location_field VARCHAR(255) NOT NULL,
    price_per_hour DECIMAL(19, 4) NOT NULL,
    status_field ENUM('Available', 'Maintenance', 'Booked') DEFAULT 'Available' NOT NULL
);