package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * SuccessPage - Page Object untuk halaman order success.
 *
 * Merepresentasikan elemen-elemen UI pada halaman konfirmasi
 * bahwa pesanan berhasil dibuat.
 */
public class SuccessPage extends BasePage {

    // Locators
    private final By successMessage = By.xpath("//android.widget.TextView[@text='Cek Status Pembayaran' or @text='Belanja Lagi']");
    private final By orderNumber = By.id("com.indomaret.klikindomaret:id/tv_order_number");
    private final By successPageIndicator = By.xpath("//android.widget.TextView[@text='Cek Status Pembayaran' or @text='Belanja Lagi']");

    public SuccessPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Mengecek apakah halaman order success sedang ditampilkan.
     *
     * @return true jika halaman sukses terlihat
     */
    public boolean isDisplayed() {
        return isDisplayed(successPageIndicator);
    }

    /**
     * Mendapatkan pesan sukses.
     *
     * @return Teks pesan sukses
     */
    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public String getOrderNumber() {
        try {
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));
            String number = driver.findElement(orderNumber).getText();
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(15));
            return number;
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(15));
            return "N/A (BCA VA Screen)";
        }
    }
}
