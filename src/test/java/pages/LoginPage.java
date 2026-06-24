package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * LoginPage - Page Object untuk halaman login Klik Indomaret.
 *
 * Merepresentasikan elemen-elemen UI dan aksi pada halaman login.
 */
public class LoginPage extends BasePage {

    // Locators
    private final By usernameField = By.xpath("//android.widget.EditText[not(@password='true')]");
    private final By lanjutBtn = By.id("com.indomaret.klikindomaret:id/28r");

    private final By passwordField = By.xpath("//android.widget.EditText[@password='true']");
    private final By loginBtn = By.id("com.indomaret.klikindomaret:id/28r");

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Mengecek apakah halaman login sedang ditampilkan.
     *
     * @return true jika halaman login terlihat
     */
    public boolean isDisplayed() {
        return isDisplayed(usernameField);
    }

    /**
     * Memasukkan username ke field login.
     *
     * @param username Username pengguna
     */
    public void enterUsername(String username) {
        type(usernameField, username);
    }

    /**
     * Memasukkan password ke field login.
     *
     * @param password Password pengguna
     */
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    /**
     * Menekan tombol login.
     */
    public void clickLoginButton() {
        click(loginBtn);
    }

    /**
     * Melakukan aksi login secara lengkap (isi username, password, tekan login).
     *
     * @param username Username pengguna
     * @param password Password pengguna
     */
    public void login(String username, String password) {
        type(usernameField, username);
        click(lanjutBtn);
        
        // Wait for password field to appear, then input password and click "Masuk"
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        type(passwordField, password);
        click(loginBtn);
    }
}
