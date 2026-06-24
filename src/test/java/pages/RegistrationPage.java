package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * RegistrationPage - Page Object untuk halaman registrasi (Daftar).
 */
public class RegistrationPage extends BasePage {

    // Locators
    private final By phoneField = By.xpath("//android.widget.EditText[not(@password='true')]");
    // Tombol lanjut setelah memasukkan nomor HP
    private final By lanjutBtn = By.xpath("//android.widget.TextView[@text='Lanjut']"); 

    // Form Pendaftaran Akun
    private final By namaField = By.xpath("//android.widget.EditText[@text='Nama Lengkap *']");
    private final By passwordField = By.xpath("//android.widget.EditText[@text='Kata Sandi *']");
    private final By confirmPasswordField = By.xpath("//android.widget.EditText[@text='Konfirmasi Kata Sandi *']");
    private final By emailField = By.xpath("//android.widget.EditText[@text='Email (Opsional)']");
    
    // Syarat dan Ketentuan Checkbox
    private final By tncCheckbox = By.id("com.indomaret.klikindomaret:id/e4j");
    // Tombol Simpan dan Daftar
    private final By simpanDaftarBtn = By.id("com.indomaret.klikindomaret:id/amb");

    // Halaman Sukses
    private final By successMessage = By.xpath("//android.widget.TextView[@text='Akun Berhasil Terdaftar']");
    private final By mulaiBelanjaBtn = By.id("com.indomaret.klikindomaret:id/amb");

    public RegistrationPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(phoneField);
    }

    public void enterPhoneNumber(String phoneNumber) {
        type(phoneField, phoneNumber);
    }

    public void clickLanjut() {
        // Klik tombol Lanjut setelah memasukkan nomor (asumsi ada di halaman awal)
        // Terkadang menggunakan tombol enter di keyboard atau tombol id spesifik
        click(By.id("com.indomaret.klikindomaret:id/28r")); // Menggunakan ID loginBtn yang lama jika sama
    }

    public void fillRegistrationForm(String nama, String password, String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(namaField));
        
        type(namaField, nama);
        type(passwordField, password);
        type(confirmPasswordField, password);
        type(emailField, email);
    }

    public void checkTermsAndSubmit() {
        click(tncCheckbox);
        click(simpanDaftarBtn);
    }

    public boolean isRegistrationSuccess() {
        return isDisplayed(successMessage);
    }

    public void clickMulaiBelanja() {
        click(mulaiBelanjaBtn);
    }
}
