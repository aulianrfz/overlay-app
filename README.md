# Overlay App

Aplikasi Android Native dengan arsitektur MVP (Model–View–Presenter) yang menampilkan floating overlay service di atas Activity utama.
Dikembangkan menggunakan Java Android Native

---
## 🎯 Fitur Utama
MainActivity
- Berisi 1 tombol untuk menyalakan floating service overlay.
- Berisi 1 TextView untuk menerima data dari service melalui callback interface (bukan Intent).
FloatingViewService
- Menampilkan layout kecil dengan 4 tombol:
- Tombol A → Mengirimkan string "Kirim string dari service" ke Activity melalui callback interface.
- Tombol B → Menutup / mematikan Activity utama.
- Tombol C → Menutup / menghentikan Service overlay.
- Tombol D → Dapat disentuh, ditahan, dan digeser untuk memindahkan posisi layout overlay mengikuti pergerakan jari.

---
## ⚙️ Arsitektur
Aplikasi ini menggunakan MVP Pattern:
View (MainActivity) ↔ Presenter (MainPresenter) ↔ Model (MessageModel)
                               ↕
                        FloatingViewService
- View: Menangani tampilan dan input pengguna.
- Presenter: Mengatur alur logika dan komunikasi antara View ↔ Model ↔ Service.
- Model: Menyimpan data (misalnya pesan yang diterima dari service).
- Service: Menyediakan overlay interaktif dan mengirimkan event ke Presenter melalui interface callback.

---
## 🚀 Gambaran Aplikasi



---
## 🧩 Cara Menjalankan
1. Clone repository: git clone https://github.com/aulianrfz/overlay-app.git
2. Buka di Android Studio.
3. Pastikan izin SYSTEM_ALERT_WINDOW diberikan.
4. Jalankan aplikasi, tekan tombol di Activity → overlay akan muncul.
