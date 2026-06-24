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
    private final By cartIcon = By.id("com.indomaret.klikindomaret:id/iv_cart");
    private final By cartPageIndicator = By.id("com.indomaret.klikindomaret:id/tv_cart_title");
    private final By deliveryMethodSection = By.id("com.indomaret.klikindomaret:id/rv_delivery_method");
    private final By firstDeliveryOption = By.id("com.indomaret.klikindomaret:id/rb_delivery_option");
    private final By shippingFeeText = By.id("com.indomaret.klikindomaret:id/tv_shipping_fee");
    private final By insuranceFeeText = By.id("com.indomaret.klikindomaret:id/tv_insurance_fee");
    private final By beliButton = By.id("com.indomaret.klikindomaret:id/btn_beli");

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
        scrollToText("Pengiriman");
        click(firstDeliveryOption);
    }

    /**
     * Mengecek apakah informasi ongkos kirim ditampilkan.
     *
     * @return true jika ongkos kirim terlihat
     */
    public boolean isDeliveryFeeDisplayed() {
        return isDisplayed(shippingFeeText);
    }

    /**
     * Mendapatkan teks ongkos kirim dari keranjang.
     *
     * @return Teks ongkos kirim (contoh: "Rp 9.000")
     */
    public String getShippingFeeText() {
        return getText(shippingFeeText);
    }

    /**
     * Mendapatkan teks biaya asuransi dari keranjang.
     *
     * @return Teks biaya asuransi (contoh: "Rp 1.000")
     */
    public String getInsuranceFeeText() {
        return getText(insuranceFeeText);
    }

    /**
     * Menekan tombol "Beli".
     */
    public void clickBeliButton() {
        scrollToText("Beli");
        click(beliButton);
    }
}
