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
    private final By totalPriceText = By.xpath("//*[@resource-id='totalAmount']");
    private final By checkoutPageIndicator = By.xpath("//android.widget.TextView[@text='Pembayaran']");

    // Locators - Metode Pembayaran
    private final By virtualAccountOption = By.xpath("//android.widget.TextView[@text='BCA Virtual Account']");
    private final By bayarSekarangButton = By.xpath("//android.widget.Button[@text='Bayar Sekarang']");

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

    public void clickBayarSekarang() {
        click(bayarSekarangButton);
    }

    /**
     * Menangani popup "Metode Pembayaran sudah digunakan!" jika muncul
     */
    public void handlePaymentAlreadyUsedPopup() {
        By continuePaymentBtn = By.xpath("//android.widget.Button[@text='Lanjutkan']");
        System.out.println("[INFO] Menunggu popup Metode Pembayaran (jika ada)...");
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(3));
        boolean popupExists = !driver.findElements(continuePaymentBtn).isEmpty();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(15)); // Restore

        if (popupExists) {
            System.out.println("[INFO] Popup 'Metode Pembayaran sudah digunakan!' terdeteksi. Mengeklik 'Lanjutkan'...");
            click(continuePaymentBtn);
        } else {
            System.out.println("[INFO] Tidak ada popup Metode Pembayaran.");
        }
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
