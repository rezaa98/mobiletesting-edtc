package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * ProductPage - Page Object untuk halaman detail produk.
 *
 * Merepresentasikan elemen-elemen UI dan aksi pada halaman detail produk
 * termasuk menampilkan harga dan tombol tambah ke keranjang.
 */
public class ProductPage extends BasePage {

    // Locators
    private final By productName = By.id("com.indomaret.klikindomaret:id/4si");
    private final By productPrice = By.id("com.indomaret.klikindomaret:id/28j");
    private final By addToCartButton = By.id("com.indomaret.klikindomaret:id/9kj");
    private final By plusButton = By.id("com.indomaret.klikindomaret:id/1ed");
    private final By addToCartSuccessMessage = By.id("dummy_success_message");

    public ProductPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Mengecek apakah halaman detail produk sedang ditampilkan.
     *
     * @return true jika nama produk terlihat
     */
    public boolean isDisplayed() {
        return isDisplayed(productName);
    }

    /**
     * Mendapatkan nama produk dari halaman detail.
     *
     * @return Nama produk
     */
    public String getProductName() {
        return getText(productName);
    }

    /**
     * Mendapatkan teks harga produk dari halaman detail.
     * Teks ini masih berformat "Rp XX.XXX" dan perlu di-parse oleh PriceCalculator.
     *
     * @return Teks harga produk (contoh: "Rp 15.000")
     */
    public String getProductPriceText() {
        return getText(productPrice);
    }

    /**
     * Menekan tombol "Tambah ke Keranjang" atau tombol "+" jika barang sudah ada.
     */
    public void addToCart() {
        if (isDisplayedFast(addToCartButton)) {
            click(addToCartButton);
        } else if (isDisplayedFast(plusButton)) {
            click(plusButton);
        } else {
            // Jika keduanya tidak ada, biarkan click() asli melempar exception
            click(addToCartButton);
        }
    }

    /**
     * Mengecek apakah pesan sukses tambah ke keranjang muncul.
     *
     * @return true jika pesan sukses terlihat
     */
    public boolean isAddToCartSuccessful() {
        return isDisplayed(addToCartSuccessMessage);
    }
}
