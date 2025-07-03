/* Cara membuat db dari terminal: sqlite3 src/database/vbook.db < src/database/villa_booking.sql */

CREATE TABLE `villas` (
  `id` INTEGER PRIMARY KEY,
  `name` TEXT NOT NULL,
  `description` text NOT NULL,
  `address` text NOT NULL
);

CREATE TABLE `room_types` (
  `id` INTEGER PRIMARY KEY,
  `villa` INTEGER NOT NULL,
  `name` TEXT NOT NULL,
  `quantity` INTEGER DEFAULT 1,
  `capacity` INTEGER DEFAULT 1,
  `price` INTEGER NOT NULL,
  `bed_size` TEXT NOT NULL /* hanya bernilai: double, queen, king */,
  `has_desk` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */,
  `has_ac` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */,
  `has_tv` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */,
  `has_wifi` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */,
  `has_shower` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */,
  `has_hotwater` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */,
  `has_fridge` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */
);

CREATE TABLE `customers` (
  `id` INTEGER PRIMARY KEY,
  `name` TEXT NOT NULL,
  `email` TEXT NOT NULL,
  `phone` TEXT
);

CREATE TABLE `bookings` (
  `id` INTEGER PRIMARY KEY,
  `customer` INTEGER,
  `room_type` INTEGER,
  `checkin_date` TEXT NOT NULL, /* timestamp dalam format YYYY-MM-DD hh:mm:ss */
  `checkout_date` TEXT NOT NULL, /* timestamp dalam format YYYY-MM-DD hh:mm:ss */
  `price` INTEGER,
  `voucher` INTEGER,
  `final_price` INTEGER,
  `payment_status` TEXT DEFAULT 'waiting' /* hanya bernilai: waiting, failed, success */,
  `has_checkedin` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */,
  `has_checkedout` INTEGER DEFAULT 0 /* hanya bernilai 0 dan 1 (boolean) */
);

CREATE TABLE `reviews` (
  `booking` INTEGER PRIMARY KEY,
  `star` INTEGER NOT NULL,
  `title` TEXT NOT NULL,
  `content` TEXT NOT NULL
);

CREATE TABLE `vouchers` (
  `id` INTEGER PRIMARY KEY,
  `code` TEXT NOT NULL,
  `description` TEXT NOT NULL,
  `discount` REAL NOT NULL,
  `start_date` TEXT NOT NULL, /* timestamp dalam format YYYY-MM-DD hh:mm:ss */
  `end_date` TEXT NOT NULL /* timestamp dalam format YYYY-MM-DD hh:mm:ss */
);

INSERT INTO villas (id, name, description, address) VALUES
(1, 'Villa megah', 'Villa mewah dengan pemandangan pegunungan.', 'jalan raya kubu'),
(2, 'Villa biasa', 'Villa dengan taman dan kolam renang.', 'jalan raya bedugul');

INSERT INTO room_types (id, villa, name, quantity, capacity, price, bed_size, has_desk, has_ac, has_tv, has_wifi, has_shower, has_hotwater, has_fridge) VALUES
(1, 1, 'Ruang vvip', 3, 2, 750000, 'queen', 1, 1, 1, 1, 1, 1, 1),
(2, 2, 'Ruang standar', 2, 2, 500000, 'double', 0, 1, 1, 1, 1, 0, 0);

INSERT INTO customers (id, name, email, phone) VALUES
(1, 'dekna', 'dekna@gmail.com', '081234567890'),
(2, 'sanjaya', 'sanjaya@gmail.com', '08989898989');

INSERT INTO bookings (id, customer, room_type, checkin_date, checkout_date, price, voucher, final_price, payment_status, has_checkedin, has_checkedout) VALUES
(1, 1, 1, '2025-07-10 14:00:00', '2025-07-12 12:00:00', 1500000, 1, 1200000, 'success', 1, 1),
(2, 2, 2, '2025-07-15 15:00:00', '2025-07-17 11:00:00', 1000000, 2, 850000, 'waiting', 0, 0);

INSERT INTO reviews (booking, star, title, content) VALUES
(1, 5, 'Sangat Nyaman', 'fasilitas lengkap dan pelayanan ramah.'),
(2, 4, 'Cukup Memuaskan', 'Kamar bersih dan lokasi strategis, cuma agak bauk.');

INSERT INTO vouchers (id, code, description, discount, start_date, end_date) VALUES
(1, 'DISKON20', 'Diskon 20% untuk semua kamar', 0.20, '2025-06-01 00:00:00', '2025-08-31 23:59:59'),
(2, 'HEMAT15', 'Diskon 15% untuk pemesanan 2 malam', 0.15, '2025-06-15 00:00:00', '2025-09-30 23:59:59');