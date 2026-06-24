package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage - Kelas dasar untuk semua Page Object.
 *
 * Menyediakan metode-metode umum seperti click, type, waitForElement
 * agar tidak perlu ditulis ulang di setiap halaman.
 * Semua Page Object lain mewarisi (extends) kelas ini.
 */
public abstract class BasePage {

    protected AndroidDriver driver;
    protected WebDriverWait wait;

    private static final int DEFAULT_TIMEOUT = 20;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /**
     * Menunggu elemen terlihat di layar, lalu mengembalikannya.
     *
     * @param locator Locator elemen (By.id, By.xpath, dll)
     * @return WebElement yang sudah terlihat
     */
    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Menunggu elemen bisa diklik, lalu mengkliknya.
     *
     * @param locator Locator elemen
     */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Menunggu elemen terlihat, menghapus isi lama, lalu mengetikkan teks baru.
     *
     * @param locator Locator elemen input
     * @param text    Teks yang akan diketik
     */
    protected void type(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Mendapatkan teks dari elemen.
     *
     * @param locator Locator elemen
     * @return Teks elemen
     */
    protected String getText(By locator) {
        return waitForElement(locator).getText();
    }

    /**
     * Mengecek apakah elemen ditampilkan di layar.
     *
     * @param locator Locator elemen
     * @return true jika elemen terlihat
     */
    protected boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Melakukan scroll ke bawah pada layar.
     * Menggunakan UiScrollable untuk menemukan elemen yang belum terlihat.
     *
     * @param visibleText Teks yang terlihat pada elemen target
     */
    protected void scrollToText(String visibleText) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().textContains(\"" + visibleText + "\"))"));
    }

    /**
     * Menunggu beberapa detik secara eksplisit (gunakan hanya jika diperlukan).
     *
     * @param seconds Durasi tunggu dalam detik
     */
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
