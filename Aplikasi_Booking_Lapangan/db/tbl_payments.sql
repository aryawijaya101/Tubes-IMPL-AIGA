USE field_booking_db;
DROP TABLE IF EXISTS tbl_payments;

CREATE TABLE tbl_payments(
    payment_id INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    booking_id INT(20),
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    method_payment ENUM('Transfer', 'Cash', 'Debit Card', 'e-Wallet', 'QR') NOT NULL,
    status_payment ENUM('Unpaid', 'Pending Verification', 'Paid', 'Failed') DEFAULT 'Unpaid' NOT NULL,
    CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id) REFERENCES tbl_bookings(booking_id) ON DELETE RESTRICT
);