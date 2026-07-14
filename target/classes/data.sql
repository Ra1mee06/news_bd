-- Удаление таблиц при повторном запуске приложения
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS news CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Создание таблиц
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    parent_name VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    edited_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE news (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    text VARCHAR(2000) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    edited_at TIMESTAMP NOT NULL DEFAULT NOW(),
    inserted_by BIGINT NOT NULL REFERENCES users(id),
    updated_by BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(300) NOT NULL,
    news_id BIGINT NOT NULL REFERENCES news(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    edited_at TIMESTAMP NOT NULL DEFAULT NOW(),
    inserted_by BIGINT NOT NULL REFERENCES users(id)
);

-- Тестовые пользователи (пароли: admin123, reporter123, subscriber123)
INSERT INTO users (username, password, name, surname, parent_name, role, created_at, edited_at) VALUES
('admin', '$2a$10$FHp0myKKYLI8gYatA.4YeOVBYiZi4nr9rNczzzmjzHrU7Jy0MCMLm', 'Алексей', 'Смирнов', 'Петрович', 'ADMIN', NOW(), NOW()),
('reporter1', '$2a$10$Fu5N9Z0upUb3Hrx3HashcuPFdrxBWYBcFt9VqeLyspHZ9iA6c0gda', 'Иван', 'Иванов', 'Иванович', 'REPORTER', NOW(), NOW()),
('reporter2', '$2a$10$Fu5N9Z0upUb3Hrx3HashcuPFdrxBWYBcFt9VqeLyspHZ9iA6c0gda', 'Пётр', 'Петров', 'Сергеевич', 'REPORTER', NOW(), NOW()),
('subscriber1', '$2a$10$23SWDPoZOSRQq/fBE/r8EeC3uXis1ftLXi7YnjnhiNIKfPNpzJKpu', 'Мария', 'Сидорова', 'Андреевна', 'SUBSCRIBER', NOW(), NOW());

-- 10 новостей
INSERT INTO news (title, text, inserted_by, updated_by, created_at, edited_at) VALUES
('Открытие нового офиса', 'Компания открыла новый офис в центре города для расширения деятельности.', 2, 2, NOW(), NOW()),
('Обновление программного обеспечения', 'Выпущена новая версия внутренней системы документооборота с улучшенной безопасностью.', 2, 2, NOW(), NOW()),
('Семинар по информационной безопасности', 'Для сотрудников проведён обучающий семинар по основам кибербезопасности.', 3, 3, NOW(), NOW()),
('Итоги квартала', 'Подведены итоги работы за первый квартал: рост показателей на 12%.', 2, 2, NOW(), NOW()),
('Новый проект по автоматизации', 'Запущен пилотный проект автоматизации отчётности в подразделениях.', 3, 3, NOW(), NOW()),
('Партнёрское соглашение', 'Подписано соглашение о сотрудничестве с ведущим IT-интегратором.', 2, 2, NOW(), NOW()),
('Модернизация серверной инфраструктуры', 'Завершена модернизация серверного оборудования и систем хранения данных.', 3, 3, NOW(), NOW()),
('Корпоративный день здоровья', 'Состоялся спортивный день для сотрудников и их семей.', 2, 2, NOW(), NOW()),
('Внедрение системы мониторинга', 'В эксплуатацию введена система мониторинга производительности сервисов.', 3, 3, NOW(), NOW()),
('Планы на следующий год', 'Утверждена стратегия цифровой трансформации на предстоящий период.', 2, 2, NOW(), NOW());

-- По 2 комментария к каждой новости (20 комментариев)
INSERT INTO comments (text, news_id, inserted_by, created_at, edited_at) VALUES
('Отличная новость, поздравляю коллектив!', 1, 4, NOW(), NOW()),
('Ждём подробностей о новом офисе.', 1, 4, NOW(), NOW()),
('Обновление было очень кстати.', 2, 4, NOW(), NOW()),
('Надеюсь, интерфейс стал удобнее.', 2, 4, NOW(), NOW()),
('Полезный семинар, спасибо организаторам.', 3, 4, NOW(), NOW()),
('Хотелось бы больше практических примеров.', 3, 4, NOW(), NOW()),
('Хорошие результаты, так держать!', 4, 4, NOW(), NOW()),
('Какие планы на следующий квартал?', 4, 4, NOW(), NOW()),
('Интересный проект, буду следить за развитием.', 5, 4, NOW(), NOW()),
('Автоматизация сэкономит много времени.', 5, 4, NOW(), NOW()),
('Важное соглашение для компании.', 6, 4, NOW(), NOW()),
('Какие направления сотрудничества планируются?', 6, 4, NOW(), NOW()),
('Наконец-то обновили серверы!', 7, 4, NOW(), NOW()),
('Это повысит стабильность систем.', 7, 4, NOW(), NOW()),
('Было весело, спасибо за организацию!', 8, 4, NOW(), NOW()),
('Хотелось бы подобные мероприятия чаще.', 8, 4, NOW(), NOW()),
('Мониторинг поможет быстрее находить проблемы.', 9, 4, NOW(), NOW()),
('Где можно посмотреть дашборды?', 9, 4, NOW(), NOW()),
('Стратегия выглядит амбициозно.', 10, 4, NOW(), NOW()),
('Какие проекты в приоритете?', 10, 4, NOW(), NOW());
