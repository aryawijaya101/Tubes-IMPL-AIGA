USE field_booking_db;
DROP TABLE IF EXISTS tbl_notifications;

CREATE TABLE tbl_notifications (
    notification_id INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT(16),
    massage_notif TEXT NOT NULL,
    sent_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    type_notif VARCHAR(20) NOT NULL,
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES tbl_users(user_id) ON DELETE RESTRICT
);