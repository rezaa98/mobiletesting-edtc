package utils;

/**
 * PriceCalculator - Utility class untuk parsing harga dari teks UI Android
 * dan melakukan kalkulasi total belanja.
 *
 * Contoh teks dari UI: "Rp 15.000", "Rp15.000", "Rp 1.250.000"
 * Akan dikonversi menjadi integer: 15000, 15000, 1250000
 */
public class PriceCalculator {

    /**
     * Mengekstrak nilai angka dari teks harga Indonesia.
     * Membuang semua karakter non-digit (Rp, titik, spasi, dll).
     *
     * @param priceText Teks harga dari UI Android (contoh: "Rp 15.000")
     * @return Nilai harga dalam integer (contoh: 15000)
     */
    public static int parsePrice(String priceText) {
        if (priceText == null || priceText.trim().isEmpty()) {
            return 0;
        }
        // Hapus semua karakter kecuali digit
        String numericOnly = priceText.replaceAll("[^0-9]", "");
        if (numericOnly.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(numericOnly);
    }

    /**
     * Menghitung total harga berdasarkan komponen-komponen belanja.
     *
     * @param productPrice Harga produk
     * @param quantity     Kuantitas produk
     * @param shippingFee  Ongkos kirim
     * @param insuranceFee Biaya asuransi
     * @return Total harga yang harus dibayar
     */
    public static int calculateTotalPrice(int productPrice, int quantity, int shippingFee, int insuranceFee) {
        return (productPrice * quantity) + shippingFee + insuranceFee;
    }

    /**
     * Membandingkan total harga yang dihitung secara manual
     * dengan total harga yang ditampilkan di UI.
     *
     * @param expectedTotal Total hasil kalkulasi manual
     * @param actualTotal   Total yang ditampilkan di UI aplikasi
     * @return true jika keduanya sama
     */
    public static boolean verifyTotalPrice(int expectedTotal, int actualTotal) {
        return expectedTotal == actualTotal;
    }

    /**
     * Format angka menjadi format mata uang Indonesia.
     *
     * @param amount Nilai numerik
     * @return String terformat (contoh: "Rp 15.000")
     */
    public static String formatToRupiah(int amount) {
        return String.format("Rp %,d", amount).replace(",", ".");
    }
}
