
INSERT INTO users (username, password, name, surname, parent_name, role)
VALUES
('admin', 'admin123', 'Admin', 'Adminov', NULL, 'ADMIN'),
('user1', 'user123', 'Ivan', 'Ivanov', 'Ivanovich', 'USER');

INSERT INTO news (title, text, inserted_by, updated_by)
VALUES
('First News', 'This is the first news record', 1, 1),
('Second News', 'This is the second news record', 1, 1);

INSERT INTO comments (text, news_id, inserted_by)
VALUES
('Great news!', 1, 2),
('Thanks for the update', 1, 2),
('Interesting...', 2, 2);
