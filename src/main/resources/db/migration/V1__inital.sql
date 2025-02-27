CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    beds INT NOT NULL,
    description VARCHAR(200),
    price FLOAT NOT NULL
);

CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    room_id INT NOT NULL REFERENCES rooms(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    booking_reference VARCHAR(50) NOT NULL
);

--SEEDING

-- Inserts some initial rooms
INSERT INTO rooms (name, beds, description, price) VALUES ('Room A', 3, 'ocean view room', 5800.7);
INSERT INTO rooms (name, beds, description, price) VALUES ('Room B', 2, 'woods view room', 4800.7);
INSERT INTO rooms (name, beds, description, price) VALUES ('Room C', 1, 'no window room', 850.7);

-- Inserts some initial bookings
INSERT INTO bookings (room_id, start_date, end_date, booking_reference)
VALUES (1, '2025-03-01', '2025-03-05', 'REF1');

INSERT INTO bookings (room_id, start_date, end_date, booking_reference)
VALUES (2, '2025-03-01', '2025-03-05', 'REF2');