USE field_booking_db;
DROP TABLE IF EXISTS bookings_db;

CREATE TABLE bookings_db(
    booking_id INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT(16),
    field_id INT(15),
    booking_date DATE NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    total_price DECIMAL(19, 4) NOT NULL,
    status_booking ENUM('Pending', 'Confirmed', 'Cancelled', 'Completed') DEFAULT 'Pending' NOT NULL,
    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users_db(user_id) ON DELETE RESTRICT,
    CONSTRAINT fk_booking_field FOREIGN KEY (field_id) REFERENCES fields_db(field_id) ON DELETE RESTRICT
);