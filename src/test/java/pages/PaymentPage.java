package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * PaymentPage - Page Object untuk halaman konfirmasi pembayaran.
 *
 * Merepresentasikan elemen-elemen UI dan aksi pada halaman
 * konfirmasi pembayaran termasuk tombol "Bayar sekarang".
 */
public class PaymentPage extends BasePage {

    // Locators
    private final By bayarSekarangButton = By.xpath("//*[@resource-id='payButton']");
    private final By paymentPageIndicator = By.xpath("//android.widget.TextView[@text='Pembayaran']");

    public PaymentPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Mengecek apakah halaman pembayaran sedang ditampilkan.
     *
     * @return true jika halaman pembayaran terlihat
     */
    public boolean isDisplayed() {
        return isDisplayed(paymentPageIndicator);
    }

    /**
     * Menekan tombol "Bayar sekarang" untuk menyelesaikan pembayaran.
     */
    public void clickBayarSekarang() {
        scrollToText("Bayar Sekarang");
        click(bayarSekarangButton);
    }
}
