package fixtures;

/**
 * TestData - Kelas untuk mengonfigurasi data uji secara langsung di dalam kode.
 *
 * Pengguna cukup mengubah nilai-nilai variabel di bawah ini untuk mengganti
 * skenario pengujian tanpa harus menyentuh logika utama atau file JSON.
 */
public class TestData {

    // ==========================================
    // ⚙️ KONFIGURASI PENGGUNA (Ubah di sini)
    // ==========================================

    // --- Login ---
    public static final String USERNAME = "rezaaa.ym@gmail.com";
    public static final String PASSWORD = "IniPassword12345";

    // --- Pencarian Produk ---
    public static final String SEARCH_KEYWORD = "sarimie";

    // --- Keranjang ---
    public static final int QUANTITY = 3; // Total produk yang mau dicheckout

    // --- Pengiriman ---
    // Catatan: Saat ini skrip otomatis memilih slot waktu pengiriman pertama yang
    // tersedia.
    // Jika Anda ingin memilih spesifik (contoh: "10:00 - 12:00"),
    // pastikan menambahkan implementasinya di CartPage.java nanti.
    public static final String DELIVERY_TIME = "Slot Waktu Pertama";

    // --- Pembayaran ---
    public static final String PAYMENT_METHOD = "BCA Virtual Account";

    // --- Registrasi Pengguna Baru ---
    public static final String NEW_USER_PHONE = "081414343403";
    public static final String NEW_USER_NAME = "Test User Registration";
    public static final String NEW_USER_EMAIL = "testuser_auto@gmail.com";
    public static final String NEW_USER_PASSWORD = "Password123";

    // --- Pengisian Alamat Baru ---
    public static final String NEW_ADDRESS_LABEL = "Rumah Test Automation";
    public static final String NEW_ADDRESS_DETAIL = "Jalan Sudirman No. 1, Apartemen A, Tower B";
    public static final String NEW_ADDRESS_RECEIVER = "Test User Automation";
    public static final String NEW_ADDRESS_PHONE = "081414343403";

    // ==========================================
    // 🛠 METODE AKSES (Tidak perlu diubah)
    // ==========================================

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getSearchKeyword() {
        return SEARCH_KEYWORD;
    }

    public static int getQuantity() {
        return QUANTITY;
    }

    public static String getPaymentMethod() {
        return PAYMENT_METHOD;
    }
}
