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
    private final By successMessage = By.id("com.indomaret.klikindomaret:id/tv_success_order");
    private final By orderNumber = By.id("com.indomaret.klikindomaret:id/tv_order_number");
    private final By successPageIndicator = By.id("com.indomaret.klikindomaret:id/iv_success_icon");

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

    /**
     * Mendapatkan nomor pesanan.
     *
     * @return Teks nomor pesanan
     */
    public String getOrderNumber() {
        return getText(orderNumber);
    }
}
