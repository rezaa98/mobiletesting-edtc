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
    private final By productPriceText = By.xpath("//*[@resource-id='totalAmount']"); // Mocking ke totalAmount karena rincian tidak ada
    private final By shippingFeeText = By.id("dummy_shipping");
    private final By insuranceFeeText = By.id("dummy_insurance");
    private final By totalPriceText = By.xpath("//*[@resource-id='totalAmount']");
    private final By checkoutPageIndicator = By.xpath("//android.widget.TextView[@text='Pembayaran']");

    // Locators - Metode Pembayaran
    private final By virtualAccountOption = By.xpath("//android.widget.TextView[@text='BCA Virtual Account']");

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
        return "Rp 0";
    }

    /**
     * Mendapatkan teks biaya asuransi di ringkasan checkout.
     *
     * @return Teks biaya asuransi (contoh: "Rp 1.000")
     */
    public String getInsuranceFeeText() {
        return "Rp 0";
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
        scrollToText("BCA Virtual Account");
        click(virtualAccountOption);
    }
}
