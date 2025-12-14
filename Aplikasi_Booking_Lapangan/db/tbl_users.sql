USE field_booking_db;
DROP TABLE IF EXISTS tbl_users;

CREATE TABLE tbl_users(
    user_id INT(16) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nama_lengkap VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    role ENUM('Admin', 'Member', 'Owner', 'Karyawan') NOT NULL
);