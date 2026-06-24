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
    private final By usernameField = By.id("com.indomaret.klikindomaret:id/et_username");
    private final By passwordField = By.id("com.indomaret.klikindomaret:id/et_password");
    private final By loginButton = By.id("com.indomaret.klikindomaret:id/btn_login");
    private final By loginPageIndicator = By.id("com.indomaret.klikindomaret:id/tv_login");

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Mengecek apakah halaman login sedang ditampilkan.
     *
     * @return true jika halaman login terlihat
     */
    public boolean isDisplayed() {
        return isDisplayed(loginPageIndicator);
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
        click(loginButton);
    }

    /**
     * Melakukan aksi login secara lengkap (isi username, password, tekan login).
     *
     * @param username Username pengguna
     * @param password Password pengguna
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
}
