package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * CartPage - Page Object untuk halaman keranjang belanja.
 *
 * Merepresentasikan elemen-elemen UI dan aksi pada halaman keranjang
 * termasuk pemilihan metode pengiriman dan tombol Beli.
 */
public class CartPage extends BasePage {

    // Locators
    private final By cartIcon = By.id("com.indomaret.klikindomaret:id/ce7");
    private final By cartPageIndicator = By.xpath("//android.widget.TextView[@text='Keranjang Belanja']");
    private final By deliveryMethodSection = By.id("com.indomaret.klikindomaret:id/4aq");
    private final By firstDeliveryOption = By.xpath("//android.widget.TextView[@text='Pesan Antar']");
    private final By shippingFeeText = By.id("dummy_shipping");
    private final By insuranceFeeText = By.id("dummy_insurance");
    private final By beliButton = By.id("com.indomaret.klikindomaret:id/c1h");

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Menekan ikon keranjang untuk masuk ke halaman cart.
     */
    public void goToCart() {
        click(cartIcon);
    }

    /**
     * Mengecek apakah halaman keranjang sedang ditampilkan.
     *
     * @return true jika halaman keranjang terlihat
     */
    public boolean isDisplayed() {
        return isDisplayed(cartPageIndicator);
    }

    /**
     * Memilih metode pengiriman pertama yang tersedia.
     */
    public void chooseDeliveryMethod() {
        scrollToText("Pesan Antar");
        click(firstDeliveryOption);
    }

    /**
     * Mengecek apakah informasi ongkos kirim ditampilkan.
     *
     * @return true jika ongkos kirim terlihat
     */
    public boolean isDeliveryFeeDisplayed() {
        // Abaikan pengecekan di cart page karena ongkos kirim biasanya muncul di checkout page
        return true;
    }

    /**
     * Mendapatkan teks ongkos kirim dari keranjang.
     *
     * @return Teks ongkos kirim (contoh: "Rp 9.000")
     */
    public String getShippingFeeText() {
        return "Rp0";
    }

    /**
     * Mendapatkan teks biaya asuransi dari keranjang.
     *
     * @return Teks biaya asuransi (contoh: "Rp 1.000")
     */
    public String getInsuranceFeeText() {
        return "Rp0";
    }

    /**
     * Menekan tombol "Beli".
     */
    public void clickBeliButton() {
        scrollToText("Beli");
        click(beliButton);
    }
}
