-- =========================
-- ROLES
-- =========================
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_TRAINER');
INSERT INTO roles (id, name) VALUES (3, 'ROLE_ADMIN');


-- =========================
-- USERS
-- =========================
INSERT INTO users (id, first_name, last_name, email, password, role_id, create_at, update_at)
VALUES (1, 'John', 'Doe', 'john@example.com', 'password123', 1, CURRENT_DATE, CURRENT_DATE);

INSERT INTO users (id, first_name, last_name, email, password, role_id, create_at, update_at)
VALUES (2, 'Jane', 'Smith', 'jane@example.com', 'password123', 1, CURRENT_DATE, CURRENT_DATE);

INSERT INTO users (id, first_name, last_name, email, password, role_id, create_at, update_at)
VALUES (3, 'Mike', 'Trainer', 'mike@example.com', 'password123', 2, CURRENT_DATE, CURRENT_DATE);

INSERT INTO users (id, first_name, last_name, email, password, role_id, create_at, update_at)
VALUES (4, 'Sarah', 'Trainer', 'sarah@example.com', 'password123', 2, CURRENT_DATE, CURRENT_DATE);

INSERT INTO users (id, first_name, last_name, email, password, role_id, create_at, update_at)
VALUES (5, 'Alex', 'Johnson', 'alex@example.com', 'password123', 1, CURRENT_DATE, CURRENT_DATE);


-- =========================
-- TRAINERS (niet elke user)
-- =========================
INSERT INTO trainers (id, user_id, specialization, experience, description, create_at, update_at)
VALUES (1, 3, 'Strength Training', '5 years', 'Specialist in strength and conditioning', CURRENT_DATE, CURRENT_DATE);

INSERT INTO trainers (id, user_id, specialization, experience, description, create_at, update_at)
VALUES (2, 4, 'Yoga', '3 years', 'Yoga and flexibility coach', CURRENT_DATE, CURRENT_DATE);


-- =========================
-- SESSIONS
-- =========================
INSERT INTO sessions (id, trainer_id, location, start_time, end_time, date, available, create_at, update_at)
VALUES (1, 1, 'Gym Room A', '2026-04-01T10:00:00', '2026-04-01T11:00:00', '2026-04-01', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO sessions (id, trainer_id, location, start_time, end_time, date, available, create_at, update_at)
VALUES (2, 1, 'Gym Room B', '2026-04-02T14:00:00', '2026-04-02T15:00:00', '2026-04-02', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO sessions (id, trainer_id, location, start_time, end_time, date, available, create_at, update_at)
VALUES (3, 2, 'Yoga Studio', '2026-04-03T09:00:00', '2026-04-03T10:00:00', '2026-04-03', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- =========================
-- BOOKINGS
-- =========================
INSERT INTO bookings (id, session_id, status, date, create_at, update_at)
VALUES (1, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO bookings (id, session_id, status, date, create_at, update_at)
VALUES (2, 3, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- =========================
-- USER BOOKINGS (ManyToMany)
-- =========================
INSERT INTO user_bookings (user_id, booking_id) VALUES (1, 1);
INSERT INTO user_bookings (user_id, booking_id) VALUES (2, 1);
INSERT INTO user_bookings (user_id, booking_id) VALUES (5, 2);


-- =========================
-- TRAINING SCHEDULE
-- =========================
INSERT INTO training_schedule (id, trainer_id, user_id, session_id, file_name, contents, create_at, update_at)
VALUES (1, 1, 1, 1, 'schedule1.pdf', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO training_schedule (id, trainer_id, user_id, session_id, file_name, contents, create_at, update_at)
VALUES (2, 2, 2, 3, 'schedule2.pdf', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);