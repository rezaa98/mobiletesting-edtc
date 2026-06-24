package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * AddressPage - Page Object untuk halaman Pengisian Alamat Pengiriman.
 */
public class AddressPage extends BasePage {

    // Locators Layar Pilih Alamat (Kosong)
    private final By tambahAlamatBaruBtn = By.xpath("//android.widget.TextView[contains(@text, 'Tambah Alamat Baru') or contains(@text, 'Alamat Baru')]");

    // Locators Layar Cari Lokasi
    private final By headerTitle = By.xpath("//android.widget.TextView[@text='Cari Lokasi' or @text='Tambah Alamat']");
    private final By searchLocationField = By.xpath("//android.widget.EditText[@text='Cari lokasi / gedung / nama jalan' or contains(@text, 'Cari lokasi')]");
    private final By gunakanLokasiBtn = By.id("com.indomaret.klikindomaret:id/39c");
    private final By firstSearchResult = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]");

    // Locators Layar Peta (Map Pin)
    private final By pilihTitikLokasiBtn = By.id("com.indomaret.klikindomaret:id/amb");

    // Locators Layar Detail Alamat
    private final By labelAlamatField = By.xpath("//android.widget.EditText[@text='Label Alamat *' or contains(@text, 'Label')]");
    private final By alamatLengkapField = By.xpath("//android.widget.EditText[@text='Alamat Lengkap *' or contains(@text, 'Alamat Lengkap')]");
    private final By namaPenerimaField = By.xpath("//android.widget.EditText[@text='Nama Penerima *' or contains(@text, 'Nama')]");
    private final By nomorHandphoneField = By.xpath("//android.widget.EditText[@text='Nomor Handphone *' or contains(@text, 'Nomor Handphone')]");
    private final By simpanGunakanBtn = By.id("com.indomaret.klikindomaret:id/c8e");

    public AddressPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        // Jika masih di layar list alamat kosong, klik Tambah Alamat Baru
        if (isDisplayedFast(tambahAlamatBaruBtn)) {
            System.out.println("[INFO] Mengklik tombol 'Tambah Alamat Baru +'...");
            click(tambahAlamatBaruBtn);
            waitForSeconds(2); // tunggu layar Cari Lokasi
        }
        return isDisplayed(headerTitle);
    }

    public void chooseLocationFromMap() {
        System.out.println("[INFO] Mencari lokasi...");
        type(searchLocationField, "Jakarta");
        waitForSeconds(3); // Tunggu hasil pencarian muncul
        
        System.out.println("[INFO] Mengklik hasil pencarian pertama...");
        click(firstSearchResult);
        waitForSeconds(3); // Tunggu peta di-load
        
        // Selalu tunggu dan klik Pilih Titik Lokasi (Default wait 20s)
        System.out.println("[INFO] Menunggu dan mengklik tombol 'Pilih Titik Lokasi' di peta...");
        click(pilihTitikLokasiBtn);
        waitForSeconds(3); // Tunggu animasi pindah ke Detail Alamat
    }

    /**
     * Mengisi form detail alamat setelah lokasi dipilih dari peta.
     */
    public void fillAddressDetails(String label, String detail, String penerima, String phone) {
        System.out.println("[INFO] Menunggu dan mengisi Label Alamat...");
        type(labelAlamatField, label);
        
        System.out.println("[INFO] Menunggu dan mengisi Alamat Lengkap...");
        type(alamatLengkapField, detail);
        
        System.out.println("[INFO] Menunggu dan mengisi Nama Penerima...");
        type(namaPenerimaField, penerima);
        
        // Sembunyikan keyboard dulu karena bisa menutupi field berikutnya
        try { driver.hideKeyboard(); } catch (Exception e) {}
        waitForSeconds(1);
        
        System.out.println("[INFO] Menunggu dan mengisi Nomor Handphone (jika ada)...");
        try {
            // Sembunyikan keyboard dulu karena bisa menutupi field berikutnya
            try { driver.hideKeyboard(); } catch (Exception e) {}
            waitForSeconds(1);
            
            // Cek keberadaan element Nomor Handphone tanpa mempedulikan isDisplayed
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));
            boolean isPhoneExist = !driver.findElements(nomorHandphoneField).isEmpty();
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(15)); // Restore
            
            if (isPhoneExist) {
                System.out.println("[INFO] Field Nomor Handphone ditemukan, mengetik...");
                type(nomorHandphoneField, phone);
                try { driver.hideKeyboard(); } catch (Exception e) {}
                waitForSeconds(1);
            } else {
                System.out.println("[INFO] Field Nomor Handphone tidak muncul di layar, diabaikan.");
            }
        } catch (Exception e) {
            System.out.println("[INFO] Terjadi error saat mengisi Nomor Handphone, diabaikan.");
        }
    }

    public void saveAddress() {
        click(simpanGunakanBtn);
        waitForSeconds(5); // Tunggu proses simpan dan redirect kembali ke cart/checkout
    }
}
