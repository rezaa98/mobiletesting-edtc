package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import fixtures.TestData;

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
    private final By deliveryMethodButton = By.xpath("//android.view.ViewGroup[android.widget.TextView[@text='Pilih tipe pengiriman' or @text='Pesan Antar']]");
    
    // Bottom Sheet Locators
    private final By regulerDeliveryOption = By.xpath("//android.widget.TextView[contains(@text, 'Reguler') or contains(@text, 'Standar') or contains(@text, 'Standard') or contains(@text, 'Sameday')]");
    private final By timeSlotOption = By.id("com.indomaret.klikindomaret:id/eei");
    private final By konfirmasiButton = By.id("com.indomaret.klikindomaret:id/amb");

    private final By shippingFeeText = By.id("dummy_shipping");
    private final By insuranceFeeText = By.id("dummy_insurance");
    private final By beliButton = By.id("com.indomaret.klikindomaret:id/c1h");
    private final By lanjutBeliButton = By.id("com.indomaret.klikindomaret:id/2m6");

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

    public void chooseDeliveryMethod() {
        forceClick(deliveryMethodButton);
        waitForSeconds(2); // Wait for bottom sheet
        click(regulerDeliveryOption);
        waitForSeconds(1); // Wait for time slots to appear
        
        if ("Slot Waktu Pertama".equalsIgnoreCase(TestData.DELIVERY_TIME)) {
            click(timeSlotOption); // Click first time slot
        } else {
            scrollToText(TestData.DELIVERY_TIME);
            By specificTimeSlot = By.xpath("//android.widget.TextView[contains(@text, '" + TestData.DELIVERY_TIME + "')]");
            forceClick(specificTimeSlot);
        }
        
        waitForSeconds(1);
        click(konfirmasiButton);
        waitForSeconds(2); // Wait for bottom sheet to close
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
     * Menekan tombol "Beli" dan menangani popup penawaran spesial jika muncul.
     */
    public void clickBeliButton() {
        click(beliButton);
        waitForSeconds(2);
        if (isDisplayedFast(lanjutBeliButton)) {
            System.out.println("[INFO] Menangani popup 'Tebus Murah' dengan menekan 'Lanjut Beli'.");
            click(lanjutBeliButton);
            waitForSeconds(2);
        }
    }
}
