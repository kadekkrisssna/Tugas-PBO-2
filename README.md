# Tugas-PBO-2

# NAMA KELOMPOK
- Kadek Krisna Wira Sanjaya (2405551177)
- I Putu Eka Aditha Divayana (2405551181)
- Dhani Satriawan (2405551129)
- Tyo Putra Kharianta (2405551162)

Project kali ini adalah API untuk melakukan pemesanan villa yang berbasis bahasa pemrograman java, API disini digunakan untuk PUT, POST, GET, dan DELETE yang nantinya akan memanipulasi database yang sudah dibuat. Pengujian dari API disini menggunakan aplikasi POSTMAN.


# Villa Booking API (Java + SQLite)

Sebuah aplikasi **REST API sederhana** untuk sistem pemesanan vila menggunakan **Java (tanpa framework)** dan **SQLite**.

## Struktur Proyek
```
 src/                           ← Folder utama berisi semua source code Java
  📂 controller/                ← Berisi interaksi dengan database
  📂 handler/                   ← Berisi handler untuk masing-masing endpoint HTTP (GET/POST/PUT/DELETE) dan juga exeption untuk error respone
  📂 model/                     ← Berisi class-model/data (seperti Customer, Villa, Booking, dll)
  📂 database/                  ← Berisi konfigurasi koneksi ke database SQLite
     Server.java                ← Kelas utama untuk menjalankan HTTP server dan daftarkan semua route
```

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

## Screenshot Postman

Berikut adalah contoh screenshot penggunaan setiap endpoint API menggunakan Postman:

## `BAGIAN VILLAS`

- METHOD : `GET`
- ENDPOINT : `/villas`
- FUNGSI : Melihat semua data villa yang terssedia
![Image](https://github.com/user-attachments/assets/08fb805f-48fe-45c1-aa64-42f2a634f0a4)

---

- METHOD : `GET`
- ENDPOINT : `/villas/1`
- FUNGSI : Melihat semua infromasi detail villa dengan id:1
![Image](https://github.com/user-attachments/assets/6f6d46d8-af9a-46ad-b39a-48f5dddfa5aa)

---

- METHOD : `GET`
- ENDPOINT : `/villas/1/rooms`
- FUNGSI : Melihat semua informasi kamar pada villa dengan id:1
![Image](https://github.com/user-attachments/assets/6197fc7d-0c60-45eb-94e1-ec32af0403ae)

---

- METHOD : `GET`
- ENDPOINT : `/villas/1/reviews`
- FUNGSI : Melihat semua reviews pada villa dengan id:1
![Image](https://github.com/user-attachments/assets/6ab2874b-24db-4b08-b569-63184f984589)

---

- METHOD : `GET`
- ENDPOINT : `/villas?ci_date=2025-07-10 14:00:00&co_date=2025-07-12 12:00:00`
- FUNGSI : Melihat semua ketersediaan villa dari tanggal 10 juli (checkin) dan 12 juli (checkout)
![Image](https://github.com/user-attachments/assets/3d7a91ce-7321-4174-bac5-606d0443a3fc)

---

- METHOD : `POST`
- ENDPOINT : `/villas`
- FUNGSI : Menambah viila baru ke daftar
![Image](https://github.com/user-attachments/assets/6ae2f82d-4acd-4c48-8839-6b4984feb6cd)

---

- METHOD : `POST`
- ENDPOINT : `/villas/4/rooms`
- FUNGSI : Menambah tipe kamar pada villas dengan id:4
![Image](https://github.com/user-attachments/assets/79b1c8b8-ee9c-42b0-9289-91ccff896ef5)

---

- METHOD : `PUT`
- ENDPOINT : `/villas/4`
- FUNGSI : Mengubah data villas dengan id:4
![Image](https://github.com/user-attachments/assets/20c784fc-3e62-44eb-bf58-e58680e59143)

---

- METHOD : `PUT`
- ENDPOINT : `/villas/4/rooms/3`
- FUNGSI : Mengubah data kamar pada villas dengan id villa:4 dan id kamar:3
![Image](https://github.com/user-attachments/assets/a3ab620c-2788-499c-b79d-e67bdcb37a9d)

---

- METHOD : `DELETE`
- ENDPOINT : `/villas/4/rooms/3`
- FUNGSI : Menghapus data kamar pada villas dengan id villa:4 dan id kamar:3
![Image](https://github.com/user-attachments/assets/bb6bb05c-4d73-4f5b-9454-9163b3dde97b)

---

- METHOD : `DELETE`
- ENDPOINT : `/villas/4`
- FUNGSI : Menghapus data villas dengan id:4
![Image](https://github.com/user-attachments/assets/7b48dea9-cd69-437a-b404-989c5d673355)

---
---
---

## `BAGIAN CUSTOMERS`

- METHOD : `GET`
- ENDPOINT : `/customers`
- FUNGSI : Menampilkan semua data customers
![Image](https://github.com/user-attachments/assets/38ebfa81-a7e2-4241-80c7-a7c6a69d1ba7)

---

- METHOD : `GET`
- ENDPOINT : `/customers/1`
- FUNGSI : Menampilkan data customers dengan id:1
![Image](https://github.com/user-attachments/assets/616e1282-40b7-45ac-8fe8-6fc3f14f8b1b)

---

- METHOD : `GET`
- ENDPOINT : `/customers/1/bookings`
- FUNGSI : Menampilkan semua data bookings yang dilakukan oleh customers dengan id:1
![Image](https://github.com/user-attachments/assets/77c86c8f-92c9-4837-80c2-088aae8d744e)

---

- METHOD : `GET`
- ENDPOINT : `/customers/1/reviews`
- FUNGSI : Menampilkan semua data reviews yang dilakukan oleh customers dengan id:1
![Image](https://github.com/user-attachments/assets/a78b53e8-af79-4b54-a17e-168ad57879b6)

---

- METHOD : `POST`
- ENDPOINT : `/customers/`
- FUNGSI : Menambahkan data customers baru
![Image](https://github.com/user-attachments/assets/f78a6c82-0691-46c8-8f80-8bcd19a9edad)

---

- METHOD : `POST`
- ENDPOINT : `/customers/bookings/4`
- FUNGSI : Customers dengan id:4 melakukan pemesanan villas
![Image](https://github.com/user-attachments/assets/1e694edc-586f-4a59-8463-91ccedf931ad)

---

- METHOD : `POST`
- ENDPOINT : `/customers/bookings`
- FUNGSI : Customers dengan id:4 memberikan rating
![Image](https://github.com/user-attachments/assets/2ae35709-9bda-4493-9ee2-40b41b6b906d)

---

- METHOD : `PUT`
- ENDPOINT : `/customers/4`
- FUNGSI : Mengubah data customers dengan id:4
![Image](https://github.com/user-attachments/assets/1f49418f-e0cc-4c30-b90b-a253a0f8602f)

---
---
---

## `BAGIAN VOUCHERS`

- METHOD : `GET`
- ENDPOINT : `/vouchers`
- FUNGSI : Menampilkan semua data vouchers
![Image](https://github.com/user-attachments/assets/fa7f2648-03cd-4300-ac13-b19bf48b53f6)

---

- METHOD : `GET`
- ENDPOINT : `/vouchers/2`
- FUNGSI : Menampilkan data vouchers dengan id:2
![Image](https://github.com/user-attachments/assets/f003ff71-5ca6-43f0-87c1-c2842c825a9a)

---

- METHOD : `POST`
- ENDPOINT : `/vouchers`
- FUNGSI : Menambah vouchers baru
![Image](https://github.com/user-attachments/assets/dfef46f3-8f31-449c-82eb-1ea3114dd81e)

---

- METHOD : `PUT`
- ENDPOINT : `/vouchers/3`
- FUNGSI : Mengubah data vouchers dengan id:3
![Image](https://github.com/user-attachments/assets/e61552be-6dc8-4766-baf9-bab1161c333a)

---

- METHOD : `DELETE`
- ENDPOINT : `/vouchers/3`
- FUNGSI : Menghapus data vouchers dengan id:3
![Image](https://github.com/user-attachments/assets/ecf783d1-cf87-4d30-a420-03d80358b849)

----

# Validasi dan Error Handling

- Semua **request/response dalam format JSON**
- `404` jika ID tidak ditemukan
- `400` jika data tidak lengkap
- Validasi format email, nomor telepon
- Semua error ditangani melalui Exception dan ditampilkan dalam format JSON, dan contohnya sebagai berikut:
![image](https://github.com/user-attachments/assets/e86ae8d9-d9e4-4bd7-a4d8-af2462ceebb5)


![Image](https://github.com/user-attachments/assets/75efcc70-67c4-4fa8-8723-216c257905ea)
