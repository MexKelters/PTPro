-------------------------------------------------
-- ROLES (One-to-One met users)
-------------------------------------------------
INSERT INTO roles (id, name)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER'),
    (3, 'ROLE_TRAINER');

-------------------------------------------------
-- USERS
-------------------------------------------------
INSERT INTO users (id, first_name, last_name, email, password, role_id, create_at, update_at)
VALUES
    (1, 'Jan', 'Jansen', 'jan@test.com', '$2a$10$pass1', 1, '2026-02-17', '2026-02-17'),
    (2, 'Piet', 'Pieters', 'piet@test.com', '$2a$10$pass2', 2, '2026-02-17', '2026-02-17'),
    (3, 'Sara', 'Visser', 'sara@test.com', '$2a$10$pass3', 3, '2026-02-17', '2026-02-17');

-- Let op: nu hebben we slechts 3 users, omdat je alleen 3 roles hebt (One-to-One)
-- Wil je meer users, dan moeten er extra roles worden toegevoegd!

-------------------------------------------------
-- TRAINERS (als user 3 een trainer is)
-------------------------------------------------
INSERT INTO trainers (id, user_id, specialization, experience, description, create_at, update_at)
VALUES
    (1, 3, 'Fitness', '5 jaar', 'Personal trainer fitness', '2026-02-17 10:00:00', '2026-02-17 10:00:00');

-------------------------------------------------
-- SESSIONS
-------------------------------------------------
INSERT INTO sessions (id, trainer_id, location, start_time, end_time, date, available, create_at, update_at)
VALUES
    (1, 1, 'Gym A', '2026-02-20 09:00:00', '2026-02-20 10:00:00', '2026-02-20', true, '2026-02-17 10:00:00', '2026-02-17 10:00:00'),
    (2, 1, 'Gym B', '2026-02-21 11:00:00', '2026-02-21 12:00:00', '2026-02-21', true, '2026-02-17 10:00:00', '2026-02-17 10:00:00');

-------------------------------------------------
-- BOOKINGS
-------------------------------------------------
INSERT INTO bookings (id, session_id, status, date, create_at, update_at)
VALUES
    (1, 1, true, '2026-02-20 09:00:00', '2026-02-17 10:00:00', '2026-02-17 10:00:00');

-------------------------------------------------
-- USER_BOOKINGS
-------------------------------------------------
INSERT INTO user_bookings (user_id, booking_id)
VALUES
    (3, 1);