# 📱 Klik Indomaret - Android E2E Mobile Testing

Proyek ini adalah **Automated End-to-End Testing** untuk aplikasi Android **Klik Indomaret** menggunakan **Java**, **Appium**, **Cucumber (BDD)**, dan **TestNG**.

## 📋 Skenario Yang Diuji

| No | Step | Deskripsi |
|:---:|---|---|
| 1 | Login | Login ke aplikasi Klik Indomaret |
| 2 | Search | Cari produk tertentu |
| 3 | Product Detail | Buka halaman detail produk |
| 4 | Add to Cart | Tambahkan produk ke keranjang |
| 5 | Delivery | Pilih metode pengiriman |
| 6 | Beli | Klik tombol "Beli" |
| 7 | Payment | Pilih metode "Virtual Account" di bagian Pembayaran |
| 8 | Bayar | Klik tombol "Bayar sekarang" |
| 9 | Success | Redirect ke halaman order success |

### ✅ Validasi Kalkulasi Harga
Test ini **wajib** membandingkan hasil kalkulasi:
```
Total Harga = Harga Produk + Ongkos Kirim + Biaya Asuransi
```
Nilai dari UI Android akan dibandingkan dengan kalkulasi manual di kode Java.

---

## 🏗️ Struktur Proyek

```
Mobile Testing/
├── app/                              # APK Klik Indomaret
│   └── klik-indomaret.apk
├── src/test/
│   ├── java/
│   │   ├── pages/                    # Page Object Models (POM)
│   │   │   ├── BasePage.java         # Base class (click, type, wait, scroll)
│   │   │   ├── LoginPage.java
│   │   │   ├── HomePage.java
│   │   │   ├── ProductPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutPage.java
│   │   │   ├── PaymentPage.java
│   │   │   └── SuccessPage.java
│   │   ├── tests/                    # Test Code
│   │   │   ├── steps/
│   │   │   │   └── CheckoutSteps.java  # Cucumber Step Definitions
│   │   │   └── runners/
│   │   │       └── TestRunner.java     # TestNG + Cucumber Runner
│   │   ├── fixtures/                 # Test Data
│   │   │   └── TestData.java         # JSON test data reader
│   │   └── utils/                    # Utilities
│   │       ├── DriverManager.java    # Appium driver setup
│   │       └── PriceCalculator.java  # Price parsing & calculation
│   └── resources/
│       ├── features/
│       │   └── Checkout.feature      # BDD Scenario
│       ├── testdata.json             # Dummy credentials & search data
│       └── testng.xml                # TestNG suite config
└── pom.xml                           # Maven dependencies
```

---

## 🛠️ Tech Stack

| Teknologi | Versi | Kegunaan |
|---|---|---|
| Java | 11 | Bahasa pemrograman |
| Appium Java Client | 8.6.0 | Mobile automation framework library |
| Selenium | 4.13.0 | WebDriver API (bundled with Appium) |
| Cucumber | 7.20.1 | BDD test framework |
| TestNG | 7.10.2 | Test runner & assertions |
| Maven | 3.x | Build & dependency management |

---

## ⚙️ Prasyarat

1. **Java JDK 11+** terinstal
2. **Maven 3.x** terinstal
3. **Appium Server** terinstal:
   ```bash
   npm install -g appium
   appium driver install uiautomator2
   ```
4. **Android SDK** + **Android Emulator** (via Android Studio)
5. **APK File**: Download dari [APKPure](https://apkpure.com/id/klik-indomaret/com.indomaret.klikindomaret) dan letakkan di `app/klik-indomaret.apk`

---

## 🚀 Cara Menjalankan

### 1. Jalankan Appium Server
```bash
appium
```

### 2. Jalankan Android Emulator
Buka Android Studio → AVD Manager → Start emulator

### 3. Jalankan Test
```bash
cd "Mobile Testing"
mvn clean test
```

### 4. Lihat Laporan
Setelah test selesai, laporan HTML tersedia di:
```
target/surefire-reports/index.html
```

---

## 📊 Output Kalkulasi

Saat test berjalan, output konsol akan menampilkan:
```
====================================
  VERIFIKASI KALKULASI HARGA
====================================
  Harga Produk     : Rp 15.000
  Ongkos Kirim     : Rp 9.000
  Biaya Asuransi   : Rp 1.000
====================================
  Total Kalkulasi  : Rp 25.000
  Total di UI      : Rp 25.000
====================================
[PASSED] ✅ Total harga sesuai antara kalkulasi dan UI!
```

---

## 📝 Catatan Penting

- **Element Locators**: Locator ID (`By.id(...)`) pada Page Objects mungkin perlu disesuaikan dengan versi APK yang diunduh. Gunakan **Appium Inspector** untuk menemukan locator yang tepat.
- **Dummy Credentials**: Login menggunakan data dummy di `testdata.json`. Sesuaikan dengan akun valid Anda.
- **Network**: Pastikan emulator/device terhubung ke internet untuk proses checkout.
