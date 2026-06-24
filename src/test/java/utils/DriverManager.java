package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * DriverManager - Singleton untuk mengelola instance AndroidDriver Appium.
 *
 * Menggunakan ThreadLocal agar mendukung parallel execution di masa depan.
 * Konfigurasi DesiredCapabilities diatur di sini agar bersih dan terpusat.
 */
public class DriverManager {

    private static final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    // Konfigurasi Appium Server
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

    // Konfigurasi Device / Emulator
    private static final String DEVICE_NAME = "Physical Android Device";
    private static final String UDID = "adb-345311634900047-71AzuX._adb-tls-connect._tcp";
    private static final String PLATFORM_NAME = "Android";
    private static final String AUTOMATION_NAME = "UiAutomator2";

    // Konfigurasi Aplikasi
    private static final String APP_PACKAGE = "com.indomaret.klikindomaret";
    private static final String APP_ACTIVITY = "com.indomaret.klikindomaret.ngsCexmFBytBw";
    private static final String APK_PATH = "app/klik-indomaret.apk";

    /**
     * Inisialisasi AndroidDriver dengan UiAutomator2Options.
     * Driver akan otomatis menginstal APK ke emulator jika belum ada.
     */
    public static void initializeDriver() {
        if (driver.get() == null) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName(PLATFORM_NAME);
            options.setDeviceName(DEVICE_NAME);
            options.setUdid(UDID);
            options.setAutomationName(AUTOMATION_NAME);
            options.setAppPackage(APP_PACKAGE);
            options.setAppActivity(APP_ACTIVITY);

            // Set APK path jika file APK tersedia (Dinonaktifkan karena APK berupa split/xapk)
            // File apkFile = new File(APK_PATH);
            // if (apkFile.exists()) {
            //     options.setApp(apkFile.getAbsolutePath());
            // }

            // Timeout settings
            options.setNewCommandTimeout(Duration.ofSeconds(300));
            options.setAutoGrantPermissions(true);
            options.setNoReset(true);

            try {
                driver.set(new AndroidDriver(new URL(APPIUM_SERVER_URL), options));
                driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            } catch (MalformedURLException e) {
                throw new RuntimeException("URL Appium Server tidak valid: " + APPIUM_SERVER_URL, e);
            }
        }
    }

    /**
     * Mendapatkan instance driver aktif.
     *
     * @return AndroidDriver yang sedang berjalan
     */
    public static AndroidDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    /**
     * Menutup dan membersihkan session driver.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
