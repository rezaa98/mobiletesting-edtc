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
    private final By productName = By.id("com.indomaret.klikindomaret:id/tv_product_name");
    private final By productPrice = By.id("com.indomaret.klikindomaret:id/tv_product_price");
    private final By addToCartButton = By.id("com.indomaret.klikindomaret:id/btn_add_to_cart");
    private final By addToCartSuccessMessage = By.id("com.indomaret.klikindomaret:id/tv_success_message");

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
     * Menekan tombol "Tambah ke Keranjang".
     */
    public void addToCart() {
        click(addToCartButton);
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
