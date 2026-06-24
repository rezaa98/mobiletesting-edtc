package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * HomePage - Page Object untuk halaman utama Klik Indomaret.
 *
 * Merepresentasikan elemen-elemen UI dan aksi pada halaman utama
 * termasuk search bar dan navigasi ke fitur lainnya.
 */
public class HomePage extends BasePage {

    // Locators
    private final By homeIndicator = By.xpath("//android.widget.TextView[@text='Beranda']");
    private final By searchBar = By.id("com.indomaret.klikindomaret:id/dtl");
    
    // Updated locators from search results page inspection
    private final By searchInputBox = By.xpath("//android.widget.EditText");
    private final By firstSearchResult = By.id("com.indomaret.klikindomaret:id/e85");

    public HomePage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Mengecek apakah halaman utama sedang ditampilkan.
     *
     * @return true jika halaman utama terlihat
     */
    public boolean isDisplayed() {
        return isDisplayed(homeIndicator);
    }

    /**
     * Mengetikkan kata kunci pencarian pada search bar.
     *
     * @param keyword Kata kunci pencarian produk
     */
    public void searchProduct(String keyword) {
        click(searchBar);
        // Wait for the actual search input to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputBox));
        type(searchInputBox, keyword + "\\n"); // Using \n to simulate pressing enter/search
    }

    /**
     * Mengecek apakah hasil pencarian muncul.
     *
     * @return true jika ada hasil pencarian yang tampil
     */
    public boolean isSearchResultDisplayed() {
        return isDisplayed(firstSearchResult);
    }

    /**
     * Membuka detail produk pertama dari hasil pencarian.
     */
    public void openFirstProduct() {
        click(firstSearchResult);
    }
}
