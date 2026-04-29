-- =========================
-- ROLES (vaste IDs, blijven altijd 1-3)
-- =========================
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_TRAINER');
INSERT INTO roles (id, name) VALUES (3, 'ROLE_USER');


-- =========================
-- USERS (geen vaste IDs, automatisch gegenereerd)
-- =========================
INSERT INTO users (first_name, last_name, email, password, role_id, create_at, update_at)
VALUES ('John', 'Doe', 'john@example.com', 'password123', 1, CURRENT_DATE, CURRENT_DATE);

INSERT INTO users (first_name, last_name, email, password, role_id, create_at, update_at)
VALUES ('Jane', 'Smith', 'jane@example.com', 'password123', 3, CURRENT_DATE, CURRENT_DATE);

INSERT INTO users (first_name, last_name, email, password, role_id, create_at, update_at)
VALUES ('Mike', 'Trainer', 'mike@example.com', 'password123', 2, CURRENT_DATE, CURRENT_DATE);

INSERT INTO users (first_name, last_name, email, password, role_id, create_at, update_at)
VALUES ('Sarah', 'Trainer', 'sarah@example.com', 'password123', 3, CURRENT_DATE, CURRENT_DATE);

INSERT INTO users (first_name, last_name, email, password, role_id, create_at, update_at)
VALUES ('Alex', 'Johnson', 'alex@example.com', 'password123', 3, CURRENT_DATE, CURRENT_DATE);


-- =========================
-- TRAINERS
-- =========================
INSERT INTO trainers (user_id, specialization, experience, description, create_at, update_at)
VALUES ((SELECT id FROM users WHERE email = 'mike@example.com'),
        'Strength Training', '5 years', 'Specialist in strength and conditioning', CURRENT_DATE, CURRENT_DATE);

INSERT INTO trainers (user_id, specialization, experience, description, create_at, update_at)
VALUES ((SELECT id FROM users WHERE email = 'sarah@example.com'),
        'Yoga', '3 years', 'Yoga and flexibility coach', CURRENT_DATE, CURRENT_DATE);


-- =========================
-- SESSIONS
-- =========================
INSERT INTO sessions (trainer_id, location, start_time, end_time, date, available, create_at, update_at)
VALUES ((SELECT id FROM trainers WHERE user_id = (SELECT id FROM users WHERE email = 'mike@example.com')),
        'Gym Room A', '2026-04-01T10:00:00', '2026-04-01T11:00:00', '2026-04-01', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO sessions (trainer_id, location, start_time, end_time, date, available, create_at, update_at)
VALUES ((SELECT id FROM trainers WHERE user_id = (SELECT id FROM users WHERE email = 'mike@example.com')),
        'Gym Room B', '2026-04-02T14:00:00', '2026-04-02T15:00:00', '2026-04-02', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO sessions (trainer_id, location, start_time, end_time, date, available, create_at, update_at)
VALUES ((SELECT id FROM trainers WHERE user_id = (SELECT id FROM users WHERE email = 'sarah@example.com')),
        'Yoga Studio', '2026-04-03T09:00:00', '2026-04-03T10:00:00', '2026-04-03', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- =========================
-- BOOKINGS
-- =========================
INSERT INTO bookings (session_id, status, date, create_at, update_at)
VALUES ((SELECT id FROM sessions WHERE location = 'Gym Room A'),
        true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO bookings (session_id, status, date, create_at, update_at)
VALUES ((SELECT id FROM sessions WHERE location = 'Yoga Studio'),
        false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- =========================
-- USER BOOKINGS (ManyToMany)
-- =========================
INSERT INTO user_bookings (user_id, booking_id)
VALUES ((SELECT id FROM users WHERE email = 'john@example.com'),
        (SELECT id FROM bookings WHERE session_id = (SELECT id FROM sessions WHERE location = 'Gym Room A')));

INSERT INTO user_bookings (user_id, booking_id)
VALUES ((SELECT id FROM users WHERE email = 'jane@example.com'),
        (SELECT id FROM bookings WHERE session_id = (SELECT id FROM sessions WHERE location = 'Gym Room A')));

INSERT INTO user_bookings (user_id, booking_id)
VALUES ((SELECT id FROM users WHERE email = 'alex@example.com'),
        (SELECT id FROM bookings WHERE session_id = (SELECT id FROM sessions WHERE location = 'Yoga Studio')));


-- =========================
-- TRAINING SCHEDULE
-- =========================
INSERT INTO training_schedule (trainer_id, user_id, session_id, file_name, contents, create_at, update_at)
VALUES ((SELECT id FROM trainers WHERE user_id = (SELECT id FROM users WHERE email = 'mike@example.com')),
        (SELECT id FROM users WHERE email = 'john@example.com'),
        (SELECT id FROM sessions WHERE location = 'Gym Room A'),
        'schedule1.pdf', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO training_schedule (trainer_id, user_id, session_id, file_name, contents, create_at, update_at)
VALUES ((SELECT id FROM trainers WHERE user_id = (SELECT id FROM users WHERE email = 'sarah@example.com')),
        (SELECT id FROM users WHERE email = 'jane@example.com'),
        (SELECT id FROM sessions WHERE location = 'Yoga Studio'),
        'schedule2.pdf', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);