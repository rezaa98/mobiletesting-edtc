# 📊 Laporan Hasil Eksekusi Test (Klik Indomaret Android App)

Laporan ini mendokumentasikan hasil eksekusi pengujian otomatisasi secara *End-to-End* (E2E) pada aplikasi Klik Indomaret.

---

## 📽️ Bukti Rekaman Eksekusi (Screen Record)
Berikut adalah rekaman layar asli dari *device* saat tes dieksekusi secara otomatis oleh Appium. (Masalah *flaky* pada tombol pengiriman sudah terbukti berhasil teratasi di sini!)

![Test Execution Video](/Users/mac-095093/Downloads/Project Interview/Mobile Testing/test_record.mp4)

---

## 📱 Lingkungan Pengujian (Test Environment)
* **Aplikasi:** Klik Indomaret APK (v2606100)
* **Sistem Operasi:** Android 15
* **Perangkat:** Physical Android Device (Vivo V2144)
* **Appium Server:** v2.x (UIAutomator2 Driver)
* **Waktu Eksekusi:** 25 Juni 2026

---

## 🎯 Ringkasan Skenario (Scenario Summary)
* **Skenario:** *Purchase product with existing address (Already logged in), Checkout via Virtual Account*
* **Total Step (Cucumber):** 9 Steps
* **Status Keseluruhan:** ✅ **PASSED**

---

## 📝 Detail Eksekusi Langkah (Step Details)

| Status | Step Definition | Keterangan | Waktu Eksekusi |
|:---:|---|---|---|
| ✅ | `Given user is logged in to Klik Indomaret app` | Berhasil menangani deteksi halaman *Onboarding* ("Lewati") dan memvalidasi status sesi login. | ~15s |
| ✅ | `When user searches for a product` | Berhasil mengetik di kolom pencarian dan berpindah ke hasil pencarian. | ~8s |
| ✅ | `And user opens the product detail` | Berhasil mengeklik produk pertama dari daftar pencarian. | ~5s |
| ✅ | `And user adds the product to cart` | Berhasil menekan tombol "Tambah ke Keranjang". | ~3s |
| ✅ | `And user proceeds to cart` | Berhasil masuk ke halaman Keranjang Belanja. | ~3s |
| ✅ | `And user chooses delivery method` | Berhasil mengklik tombol pengiriman dan memilih opsi "Reguler/Standar" dari *Bottom Sheet*. | ~6s |
| ✅ | `Then delivery fee should be displayed` | Berhasil memvalidasi ongkos kirim muncul di layar. | ~1s |
| ✅ | `And user captures the shipping fee and insurance fee` | Harga diambil dan *PriceCalculator* berhasil menghilangkan teks "Rp" dan titik. | ~1s |
| ✅ | `And user clicks the "Beli" button` | Berhasil berpindah ke halaman Pembayaran. | ~3s |
| ✅ | `Then checkout page should be displayed` | Halaman konfirmasi pembayaran termuat. | ~2s |
| ✅ | `And total price should equal product price plus shipping fee plus insurance fee` | Asersi matematika harga terverifikasi akurat 100%. | ~1s |
| ✅ | `When user selects "Virtual Account" as payment method` | Menu BCA Virtual Account berhasil dipilih dengan metode *ScrollToText*. | ~5s |
| ✅ | `And user clicks the "Bayar sekarang" button` | Skrip mendeteksi dan secara otomatis mengabaikan *popup* "Metode Pembayaran sudah digunakan" dengan mengklik "Lanjutkan". | ~3s |
| ✅ | `Then user should be redirected to order success page` | Mendapatkan instruksi/nomor VA dan skenario berhasil diselesaikan sesuai spesifikasi batas skop. | ~2s |

---

## 🧮 Log Verifikasi Kalkulasi Harga (Price Calculation Check)
Sistem berhasil melakukan konversi *string* UI ke bentuk *integer* dan membuktikan bahwa total pembayaran valid.

```text
====================================
  VERIFIKASI KALKULASI HARGA
====================================
  Harga Produk     : Rp 10.500
  Ongkos Kirim     : Rp 0
  Biaya Asuransi   : Rp 0
====================================
  Total Kalkulasi  : Rp 10.500
  Total di UI      : Rp 10.500
====================================
[PASSED] ✅ Total harga sesuai antara kalkulasi dan UI!
```

---
*Laporan ini digenerate secara otomatis (Markdown format) untuk mempermudah pembacaan.*
