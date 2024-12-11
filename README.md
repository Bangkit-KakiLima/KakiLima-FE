# KakiLima Mobile App

Aplikasi mobile untuk mendukung platform Kaki Lima, yang memungkinkan pengguna untuk melacak lokasi pedagang kaki lima, memesan produk, dan menerima notifikasi secara real-time. Dibangun menggunakan framework Android (Kotlin).

## Daftar Isi

- [Fitur](#fitur)
- [Persyaratan Sistem](#persyaratan-sistem)
- [Instalasi](#instalasi)
- [Konfigurasi](#konfigurasi)
- [Menjalankan Aplikasi](#menjalankan-aplikasi)
- [Struktur Proyek](#struktur-proyek)
- [Kontribusi](#kontribusi)

## Fitur

- **Live Location Tracking**: Memungkinkan pengguna melacak lokasi pedagang kaki lima secara real-time.
- **Pemesanan Produk**: Fitur untuk memesan produk dengan konfirmasi dua pihak (penjual dan pembeli).
- **Penanda Titik Mangkal**: Menampilkan titik-titik mangkal pedagang pada peta.
- **Notifikasi Real-Time**: Pengguna menerima notifikasi saat pedagang mendekati lokasi mereka.
- **Manajemen Profil**: Pengguna dapat mengelola akun mereka.

## Persyaratan Sistem

- Android Studio Dolphin atau lebih baru
- Minimum SDK: 21 (Android 5.0)
- Kotlin 1.8 atau lebih baru

## Instalasi

1. Clone repositori ini:
   ```bash
   git clone https://github.com/username/kakilima-fe.git
   cd kakilima-fe
   ```

2. Buka proyek di Android Studio.

3. Sinkronkan proyek dengan Gradle:
   - Klik `Sync Project with Gradle Files` di Android Studio.

## Konfigurasi

1. Pastikan Anda memiliki file `google-services.json` dari Firebase yang sudah diunduh, dan tempatkan di folder `app/`.

2. Tambahkan konfigurasi API backend di file `gradle.properties`:
   ```properties
   API_BASE_URL=https://api.kakilima.com
   ```

3. Sesuaikan konfigurasi lainnya sesuai kebutuhan.

## Menjalankan Aplikasi

1. Jalankan aplikasi di emulator atau perangkat fisik:
   - Klik tombol `Run` (ikon segitiga hijau) di Android Studio.

2. Pastikan backend tersedia dan berjalan untuk menguji fitur-fitur yang membutuhkan komunikasi server.

## Struktur Proyek

```
kakilima-fe/
├── app/                  # Direktori utama kode aplikasi
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/     # Kode sumber utama
│   │   │   ├── res/      # Sumber daya UI (layout, drawable, dll.)
│   │   │   ├── AndroidManifest.xml  # Konfigurasi aplikasi
├── gradle/               # File konfigurasi Gradle
├── build.gradle.kts      # Konfigurasi build utama
├── settings.gradle.kts   # Konfigurasi pengaturan Gradle
├── .gitignore            # Daftar file yang diabaikan Git
├── google-services.json  # Konfigurasi Firebase (ditempatkan manual)
```

## Kontribusi

Kontribusi sangat dihargai! Untuk berkontribusi:

1. Fork repositori ini.
2. Buat branch fitur baru: `git checkout -b feature-anda`
3. Commit perubahan Anda: `git commit -m 'Menambahkan fitur tertentu'`
4. Push ke branch: `git push origin feature-anda`
5. Ajukan Pull Request.
