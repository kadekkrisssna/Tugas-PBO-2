# Tugas-PBO-2

# NAMA KELOMPOK
- Kadek Krisna Wira Sanjaya (2405551177)
- Dhani Satriawan (2405551129)
- Tyo Putra Kharianta (2405551162)
- I Putu Eka Aditha Divayana (2405551181)

Project kali ini adalah API untuk melakukan pemesanan villa yang berbasis bahasa pemrograman java, API disini digunakan untuk PUT, POST, GET, dan DELETE yang nantinya akan memanipulasi database yang sudah dibuat. Pengujian dari API disini menggunakan aplikasi POSTMAN.


# Villa Booking API (Java + SQLite)

Sebuah aplikasi **REST API sederhana** untuk sistem pemesanan vila menggunakan **Java (tanpa framework)** dan **SQLite**.

## Struktur Proyek

```
src/
 controller/
 handler/
 model/
 database/
Server.java
```

---

## Cara Menjalankan Program

1. **Clone repository**
   ```bash
   git clone https://github.com/namamu/nama-repo.git
   cd nama-repo
   ```

2. **Buat database dan isi struktur awal**
   ```bash
   sqlite3 vbook.db < villa_booking.sql
   ```

3. **Compile semua file Java**
   ```bash
   javac -cp ".;lib/*" -d out src/**/*.java
   ```

4. **Jalankan server**
   ```bash
   java -cp ".;out;lib/*" Server
   ```

5. Server berjalan di:
   ```
   http://localhost:8080
   ```

---

## Daftar Endpoint

### Villas

- `GET /villas` — semua vila
- `GET /villas/{id}` — detail vila
- `POST /villas` — tambah vila
- `PUT /villas/{id}` — edit vila
- `DELETE /villas/{id}` — hapus vila
- `GET /villas/{id}/rooms` — daftar kamar
- `GET /villas/{id}/bookings` — booking untuk vila
- `GET /villas/{id}/reviews` — review untuk vila
- `GET /villas?ci_date=YYYY-MM-DD&co_date=YYYY-MM-DD` — cari ketersediaan vila

### Customers

- `GET /customers` — semua customer
- `GET /customers/{id}` — detail customer
- `POST /customers` — register
- `PUT /customers/{id}` — update
- `DELETE /customers/{id}` — hapus
- `GET /customers/{id}/bookings` — booking customer
- `GET /customers/{id}/reviews` — review customer
- `POST /customers/{id}/bookings` — buat booking
- `POST /customers/{id}/bookings/{booking_id}/reviews` — tambah review

### Vouchers

- `GET /vouchers` — semua voucher
- `GET /vouchers/{id}` — detail voucher
- `POST /vouchers` — buat voucher
- `PUT /vouchers/{id}` — update voucher
- `DELETE /vouchers/{id}` — hapus voucher

---

## Screenshot Postman

Berikut adalah contoh screenshot penggunaan setiap endpoint API menggunakan Postman:

### `POST /customers`

![POST /customers](screenshots/post_customers.png)

---

### `GET /villas`

![GET /villas](screenshots/get_villas.png)

---

### `POST /customers/{id}/bookings`

![POST bookings](screenshots/post_booking.png)

---

### `POST /customers/{id}/bookings/{id}/reviews`

![POST review](screenshots/post_review.png)

---

### `GET /villas?ci_date=2025-07-01&co_date=2025-07-05`

![Availability](screenshots/available_villas.png)

> ⚠️ Tambahkan screenshot lain di folder `/screenshots` dan sesuaikan namanya.

---

## 📌 Validasi dan Error Handling

- Semua **request/response dalam format JSON**
- `404` jika ID tidak ditemukan
- `400` jika data tidak lengkap
- Validasi format email, nomor telepon
- Semua error ditangani melalui Exception dan ditampilkan dalam format JSON:
  ```json
  {
    "error": "Customer not found"
  }
  ```

---

## 🛠 Tools yang Digunakan

- Java 11+
- SQLite
- Postman (untuk pengujian endpoint)
- Jackson (JSON parser)

---

## 📝 Lisensi

MIT License — bebas digunakan untuk pembelajaran dan pengembangan lanjutan.
