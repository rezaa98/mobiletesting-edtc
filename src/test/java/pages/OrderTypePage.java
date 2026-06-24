package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * OrderTypePage - Page Object untuk halaman Pemilihan Tipe Pemesanan.
 * Muncul setelah menekan Beli di Keranjang.
 */
public class OrderTypePage extends BasePage {

    // Locators
    private final By headerTitle = By.xpath("//android.widget.TextView[@text='Pilih Tipe Pemesanan' or contains(@text, 'Pemesanan')]");
    private final By pesanAntarOption = By.xpath("//android.widget.TextView[contains(@text, 'Pesan Antar')]");
    private final By lengkapiAlamatBtn = By.xpath("//android.widget.TextView[contains(@text, 'Lengkapi Alamat') or contains(@text, 'Tambahkan alamat')]");

    public OrderTypePage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(headerTitle);
    }

    public void clickPesanAntar() {
        click(pesanAntarOption);
    }

    private final By ubahAlamatBtn = By.xpath("//android.widget.TextView[@text='Ubah Alamat']");

    public void clickLengkapiAlamat() {
        if (isDisplayedFast(lengkapiAlamatBtn)) {
            click(lengkapiAlamatBtn);
        } else if (isDisplayedFast(ubahAlamatBtn)) {
            System.out.println("[INFO] Tombol 'Lengkapi Alamat' tidak ada. Mengklik 'Ubah Alamat' sebagai gantinya...");
            click(ubahAlamatBtn);
        } else {
            // Biarkan throw exception jika tidak ditemukan keduanya
            click(lengkapiAlamatBtn);
        }
    }
}
