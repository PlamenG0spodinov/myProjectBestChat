CREATE TABLE IF NOT EXISTS channel_members (
    id INT PRIMARY KEY AUTO_INCREMENT ,
    channel_id INT ,
    user_id INT ,
    is_active INT DEFAULT 1,
    FOREIGN KEY (channel_id) REFERENCES channels(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);