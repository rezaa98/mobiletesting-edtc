package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * CheckoutPage - Page Object untuk halaman checkout / pembayaran.
 *
 * Merepresentasikan elemen-elemen UI pada halaman checkout
 * termasuk ringkasan harga, total, dan pemilihan metode pembayaran.
 */
public class CheckoutPage extends BasePage {

    // Locators - Ringkasan Harga
    private final By productPriceText = By.id("com.indomaret.klikindomaret:id/tv_product_price_summary");
    private final By shippingFeeText = By.id("com.indomaret.klikindomaret:id/tv_shipping_fee_summary");
    private final By insuranceFeeText = By.id("com.indomaret.klikindomaret:id/tv_insurance_fee_summary");
    private final By totalPriceText = By.id("com.indomaret.klikindomaret:id/tv_total_price");
    private final By checkoutPageIndicator = By.id("com.indomaret.klikindomaret:id/tv_checkout_title");

    // Locators - Metode Pembayaran
    private final By paymentSection = By.id("com.indomaret.klikindomaret:id/tv_payment_section");
    private final By virtualAccountOption = By.id("com.indomaret.klikindomaret:id/rb_virtual_account");

    public CheckoutPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Mengecek apakah halaman checkout sedang ditampilkan.
     *
     * @return true jika halaman checkout terlihat
     */
    public boolean isDisplayed() {
        return isDisplayed(checkoutPageIndicator);
    }

    /**
     * Mendapatkan teks harga produk di ringkasan checkout.
     *
     * @return Teks harga produk (contoh: "Rp 15.000")
     */
    public String getProductPriceText() {
        return getText(productPriceText);
    }

    /**
     * Mendapatkan teks ongkos kirim di ringkasan checkout.
     *
     * @return Teks ongkos kirim (contoh: "Rp 9.000")
     */
    public String getShippingFeeText() {
        return getText(shippingFeeText);
    }

    /**
     * Mendapatkan teks biaya asuransi di ringkasan checkout.
     *
     * @return Teks biaya asuransi (contoh: "Rp 1.000")
     */
    public String getInsuranceFeeText() {
        return getText(insuranceFeeText);
    }

    /**
     * Mendapatkan teks total harga yang ditampilkan di UI checkout.
     *
     * @return Teks total harga (contoh: "Rp 25.000")
     */
    public String getTotalPriceText() {
        return getText(totalPriceText);
    }

    /**
     * Memilih metode pembayaran "Virtual Account" pada section Pembayaran.
     */
    public void selectVirtualAccountPayment() {
        scrollToText("Pembayaran");
        click(paymentSection);
        waitForSeconds(1);
        scrollToText("Virtual Account");
        click(virtualAccountOption);
    }
}
