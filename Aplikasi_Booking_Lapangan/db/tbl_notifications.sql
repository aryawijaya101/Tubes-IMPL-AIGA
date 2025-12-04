USE field_booking_db;
DROP TABLE IF EXISTS notifications_db;

CREATE TABLE notifications_db (
    notification_id INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT(16),
    massage_notif TEXT NOT NULL,
    sent_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    type_notif VARCHAR(20) NOT NULL,
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users_db(user_id) ON DELETE RESTRICT
);