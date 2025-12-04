USE field_booking_db;
DROP TABLE IF EXISTS tbl_maintenaces;

CREATE TABLE tbl_maintenaces(
    maintenace_id INT(15) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    field_id INT(15),
    scheduled_date DATE NOT NULL,
    description TEXT NOT NULL,
    status_maintenace ENUM('Scheduled', 'In Progress', 'Completed') DEFAULT 'Scheduled' NOT NULL,
    CONSTRAINT fk_maintenace_field FOREIGN KEY (field_id) REFERENCES tbl_fields(field_id) ON DELETE RESTRICT
);