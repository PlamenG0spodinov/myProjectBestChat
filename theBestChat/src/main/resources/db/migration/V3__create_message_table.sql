CREATE TABLE IF NOT EXISTS messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT ,
    sender_id INT ,
    channel_id INT,
    recipient_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE
);

