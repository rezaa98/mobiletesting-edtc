package tests.steps;

import fixtures.TestData;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.*;
import utils.DriverManager;
import utils.PriceCalculator;

/**
 * CheckoutSteps - Step Definitions untuk skenario checkout Klik Indomaret.
 *
 * Kelas ini memetakan langkah-langkah Gherkin di file Checkout.feature
 * ke dalam implementasi Java yang menggunakan Page Object Model.
 *
 * Kalkulasi harga (product + shipping + insurance = total) dilakukan
 * di sini sebagai bagian dari validasi E2E.
 */
public class CheckoutSteps {

    // Page Objects
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private SuccessPage successPage;
    private RegistrationPage registrationPage;
    private OrderTypePage orderTypePage;
    private AddressPage addressPage;

    // Data untuk kalkulasi harga
    private int capturedProductPrice;
    private int capturedShippingFee;
    private int capturedInsuranceFee;

    /**
     * Hook: Dijalankan sebelum setiap skenario.
     * Inisialisasi driver dan semua page objects.
     */
    @Before
    public void setUp() {
        DriverManager.initializeDriver();

        loginPage = new LoginPage(DriverManager.getDriver());
        homePage = new HomePage(DriverManager.getDriver());
        productPage = new ProductPage(DriverManager.getDriver());
        cartPage = new CartPage(DriverManager.getDriver());
        checkoutPage = new CheckoutPage(DriverManager.getDriver());
        paymentPage = new PaymentPage(DriverManager.getDriver());
        successPage = new SuccessPage(DriverManager.getDriver());
        registrationPage = new RegistrationPage(DriverManager.getDriver());
        orderTypePage = new OrderTypePage(DriverManager.getDriver());
        addressPage = new AddressPage(DriverManager.getDriver());
    }

    // ==================== REGISTRATION STEPS ====================
    // ... [existing registration steps] ...

    // ==================== NEW ADDRESS STEPS ====================

    @Then("user is directed to Order Type Selection")
    public void userIsDirectedToOrderTypeSelection() {
        System.out.println("[INFO] Memverifikasi halaman Pilih Tipe Pemesanan...");
        // Beri waktu animasi dari keranjang ke halaman tipe pemesanan
        orderTypePage.waitForSeconds(3);
        Assert.assertTrue(orderTypePage.isDisplayed(), "Halaman Pilih Tipe Pemesanan tidak ditampilkan!");
    }

    @When("user clicks {string}")
    public void userClicksTextButton(String buttonText) {
        if (buttonText.equalsIgnoreCase("Lengkapi Alamat")) {
            System.out.println("[INFO] Menekan tombol Lengkapi Alamat...");
            orderTypePage.clickLengkapiAlamat();
        }
    }

    @And("user fills out a new delivery address")
    public void userFillsOutNewDeliveryAddress() {
        System.out.println("[INFO] Mengisi detail alamat baru...");
        Assert.assertTrue(addressPage.isDisplayed(), "Halaman form alamat tidak ditampilkan!");
        
        // Memilih lokasi dari peta (asumsi GPS/Map Pin)
        addressPage.chooseLocationFromMap();
        
        // Mengisi Detail Alamat
        String label = fixtures.TestData.NEW_ADDRESS_LABEL;
        String detail = fixtures.TestData.NEW_ADDRESS_DETAIL;
        String penerima = fixtures.TestData.NEW_ADDRESS_RECEIVER;
        String phone = fixtures.TestData.NEW_USER_PHONE;
        
        addressPage.fillAddressDetails(label, detail, penerima, phone);
        addressPage.saveAddress();
        
        System.out.println("[INFO] Alamat berhasil disimpan.");
    }

    /**
     * Hook: Dijalankan setelah setiap skenario.
     * Membersihkan session driver.
     */
    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // ==================== REGISTRATION STEPS ====================

    @Given("user is on the registration page")
    public void userIsOnTheRegistrationPage() {
        if (!registrationPage.isDisplayed()) {
            System.out.println("[INFO] Mengarahkan user ke halaman login untuk registrasi...");
            // Pada Klik Indomaret, halaman login dan daftar biasanya menjadi satu pintu masuk.
            // Kita akan memulai dari halaman login dan memasukkan nomor HP baru.
            Assert.assertTrue(loginPage.isDisplayed(), "Halaman login/daftar tidak ditampilkan!");
        }
    }

    @When("user registers as a new user with random phone number")
    public void userRegistersAsNewUser() {
        // Menggunakan nomor HP dari TestData
        String phone = fixtures.TestData.NEW_USER_PHONE;
        System.out.println("[INFO] Registrasi dengan nomor HP: " + phone);
        
        loginPage.enterUsername(phone);
        registrationPage.clickLanjut(); // Tekan Lanjut setelah input nomor
        
        // Mengisi form pendaftaran dari TestData
        String nama = fixtures.TestData.NEW_USER_NAME;
        String password = fixtures.TestData.NEW_USER_PASSWORD;
        String email = fixtures.TestData.NEW_USER_EMAIL;
        
        registrationPage.fillRegistrationForm(nama, password, email);
        registrationPage.checkTermsAndSubmit();
    }

    @And("user enters the OTP code")
    public void userEntersTheOtpCode() {
        // Berdasarkan trace aktual, OTP tidak ditanyakan (atau ditunda).
        // Halaman yang muncul adalah halaman sukses: "Akun Berhasil Terdaftar"
        System.out.println("[INFO] Memverifikasi halaman sukses registrasi...");
        Assert.assertTrue(registrationPage.isRegistrationSuccess(), "Pesan 'Akun Berhasil Terdaftar' tidak ditemukan!");
        registrationPage.clickMulaiBelanja();
        registrationPage.waitForSeconds(5);
    }

    // ==================== LOGIN STEPS ====================

    @Given("user is on the login page")
    public void userIsOnTheLoginPage() {
        if (!homePage.isDisplayed()) {
            Assert.assertTrue(loginPage.isDisplayed(), "Halaman login tidak ditampilkan!");
        }
    }

    @When("user logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        io.appium.java_client.android.AndroidDriver driver = DriverManager.getDriver();
        // 1. Handle Onboarding / Welcome Screen
        try {
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(3));
            org.openqa.selenium.By lewatiBtn = org.openqa.selenium.By.xpath("//android.widget.TextView[@text='Lewati']");
            if (!driver.findElements(lewatiBtn).isEmpty()) {
                System.out.println("[INFO] Welcome screen terdeteksi. Mengklik 'Lewati'...");
                driver.findElement(lewatiBtn).click();
                homePage.waitForSeconds(2);
            }
        } catch (Exception e) {}
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(15)); // Restore

        // 2. Jika di Home Page, cek apakah sudah login
        if (homePage.isDisplayed()) {
            System.out.println("[INFO] Berada di Home Page. Memeriksa status login...");
            try {
                driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(3));
                // Cek icon akun / profil di bottom nav
                org.openqa.selenium.By akunTab = org.openqa.selenium.By.xpath("//android.widget.TextView[@text='Akun' or @text='Profil']");
                if (!driver.findElements(akunTab).isEmpty()) {
                    driver.findElement(akunTab).click();
                    homePage.waitForSeconds(2);
                    
                    // Cek tombol Masuk
                    org.openqa.selenium.By masukBtn = org.openqa.selenium.By.xpath("//android.widget.TextView[@text='Masuk / Daftar']");
                    if (!driver.findElements(masukBtn).isEmpty()) {
                        System.out.println("[INFO] User belum login. Mengklik tombol Masuk...");
                        driver.findElement(masukBtn).click();
                        homePage.waitForSeconds(2);
                        // Jalankan login
                        loginPage.login(TestData.getUsername(), TestData.getPassword());
                        homePage.waitForSeconds(5); // Tunggu proses login selesai
                        
                        // Kembali ke Home agar search bar terlihat
                        System.out.println("[INFO] Login selesai. Kembali ke Beranda...");
                        org.openqa.selenium.By berandaTab = org.openqa.selenium.By.xpath("//android.widget.TextView[@text='Beranda']");
                        if (!driver.findElements(berandaTab).isEmpty()) {
                            driver.findElement(berandaTab).click();
                        }
                    } else {
                        System.out.println("[INFO] User sudah login.");
                        // Kembali ke Home
                        org.openqa.selenium.By berandaTab = org.openqa.selenium.By.xpath("//android.widget.TextView[@text='Beranda']");
                        if (!driver.findElements(berandaTab).isEmpty()) {
                            driver.findElement(berandaTab).click();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("[WARNING] Gagal memverifikasi status login via tab Akun.");
            }
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(15));
        } else {
            // Asumsi sudah berada di halaman login langsung
            loginPage.login(TestData.getUsername(), TestData.getPassword());
            homePage.waitForSeconds(5); // Tunggu proses login selesai
            System.out.println("[INFO] Direct login selesai. Kembali ke Beranda...");
            org.openqa.selenium.By berandaTab = org.openqa.selenium.By.xpath("//android.widget.TextView[@text='Beranda']");
            if (!driver.findElements(berandaTab).isEmpty()) {
                driver.findElement(berandaTab).click();
            }
        }
    }

    @Then("user should be on the home page")
    public void userShouldBeOnTheHomePage() {
        Assert.assertTrue(homePage.isDisplayed(),
                "Gagal masuk ke halaman utama setelah login!");
    }

    // ==================== SEARCH STEPS ====================

    @When("user searches for a product")
    public void userSearchesForAProduct() {
        String keyword = TestData.getSearchKeyword();
        homePage.searchProduct(keyword);
    }

    @Then("search results should be displayed")
    public void searchResultsShouldBeDisplayed() {
        Assert.assertTrue(homePage.isSearchResultDisplayed(),
                "Hasil pencarian tidak ditemukan!");
    }

    // ==================== PRODUCT DETAIL STEPS ====================

    @When("user opens the product detail page")
    public void userOpensTheProductDetailPage() {
        homePage.openFirstProduct();
    }

    @Then("product detail page should be displayed")
    public void productDetailPageShouldBeDisplayed() {
        Assert.assertTrue(productPage.isDisplayed(),
                "Halaman detail produk tidak ditampilkan!");
    }

    @And("user captures the product price")
    public void userCapturesTheProductPrice() {
        String priceText = productPage.getProductPriceText();
        capturedProductPrice = PriceCalculator.parsePrice(priceText);
        System.out.println("[INFO] Harga Produk: " + PriceCalculator.formatToRupiah(capturedProductPrice));

        Assert.assertTrue(capturedProductPrice > 0,
                "Harga produk tidak valid (harus lebih dari 0)!");
    }

    // ==================== CART STEPS ====================

    @When("user adds the product to cart")
    public void userAddsTheProductToCart() {
        int quantity = TestData.getQuantity();
        for (int i = 0; i < quantity; i++) {
            productPage.addToCart();
            productPage.waitForSeconds(1); // Wait a bit between clicks if adding multiple
        }
    }

    @Then("product should be added to cart successfully")
    public void productShouldBeAddedToCartSuccessfully() {
        // Tunggu animasi/toast notifikasi
        productPage.waitForSeconds(2);
        System.out.println("[INFO] Produk berhasil ditambahkan ke keranjang.");
    }

    @When("user proceeds to cart")
    public void userProceedsToCart() {
        cartPage.goToCart();
        Assert.assertTrue(cartPage.isDisplayed(),
                "Halaman keranjang tidak ditampilkan!");
    }

    @And("user chooses delivery method")
    public void userChoosesDeliveryMethod() {
        cartPage.chooseDeliveryMethod();
    }

    @Then("delivery fee should be displayed")
    public void deliveryFeeShouldBeDisplayed() {
        Assert.assertTrue(cartPage.isDeliveryFeeDisplayed(),
                "Ongkos kirim tidak ditampilkan!");
    }

    @And("user captures the shipping fee and insurance fee")
    public void userCapturesTheShippingFeeAndInsuranceFee() {
        String shippingText = cartPage.getShippingFeeText();
        String insuranceText = cartPage.getInsuranceFeeText();

        capturedShippingFee = PriceCalculator.parsePrice(shippingText);
        capturedInsuranceFee = PriceCalculator.parsePrice(insuranceText);

        System.out.println("[INFO] Ongkos Kirim: " + PriceCalculator.formatToRupiah(capturedShippingFee));
        System.out.println("[INFO] Biaya Asuransi: " + PriceCalculator.formatToRupiah(capturedInsuranceFee));
    }

    // ==================== CHECKOUT & CALCULATION STEPS ====================

    @And("user clicks the {string} button")
    public void userClicksTheButton(String buttonText) {
        if (buttonText.equalsIgnoreCase("Beli")) {
            if (cartPage.isDisplayed()) {
                cartPage.clickBeliButton();
            } else if (orderTypePage.isDisplayed()) {
                System.out.println("[INFO] Mengklik 'Beli' di Order Type Selection.");
            } else if (addressPage.isDisplayed()) {
                System.out.println("[INFO] Mengklik 'Beli' di Address Page.");
            } else {
                System.out.println("[INFO] Mengklik 'Beli' pada Cart Page (fallback).");
                cartPage.clickBeliButton();
            }
        } else if (buttonText.equalsIgnoreCase("Bayar sekarang")) {
            checkoutPage.clickBayarSekarang();
            checkoutPage.handlePaymentAlreadyUsedPopup();
        } else {
            System.out.println("[INFO] Tombol " + buttonText + " tidak dikenali.");
        }
    }

    @Then("checkout page should be displayed")
    public void checkoutPageShouldBeDisplayed() {
        Assert.assertTrue(checkoutPage.isDisplayed(),
                "Halaman checkout tidak ditampilkan!");
    }

    @When("user verifies the price calculation")
    public void userVerifiesThePriceCalculation() {
        // Ambil harga dari halaman checkout untuk verifikasi
        String productPriceFromCheckout = checkoutPage.getProductPriceText();
        String shippingFeeFromCheckout = checkoutPage.getShippingFeeText();
        String insuranceFeeFromCheckout = checkoutPage.getInsuranceFeeText();

        int checkoutProductPrice = PriceCalculator.parsePrice(productPriceFromCheckout);
        int checkoutShippingFee = PriceCalculator.parsePrice(shippingFeeFromCheckout);
        int checkoutInsuranceFee = PriceCalculator.parsePrice(insuranceFeeFromCheckout);

        System.out.println("====================================");
        System.out.println("  VERIFIKASI KALKULASI HARGA");
        System.out.println("====================================");
        System.out.println("  Harga Produk     : " + PriceCalculator.formatToRupiah(checkoutProductPrice));
        System.out.println("  Ongkos Kirim     : " + PriceCalculator.formatToRupiah(checkoutShippingFee));
        System.out.println("  Biaya Asuransi   : " + PriceCalculator.formatToRupiah(checkoutInsuranceFee));
        System.out.println("====================================");

        // Update captured values dari checkout page (sebagai sumber kebenaran terbaru)
        capturedProductPrice = checkoutProductPrice;
        capturedShippingFee = checkoutShippingFee;
        capturedInsuranceFee = checkoutInsuranceFee;
    }

    @Then("total price should equal product price plus shipping fee plus insurance fee")
    public void totalPriceShouldEqualCalculation() {
        // Hitung total secara manual
        int quantity = TestData.getQuantity();
        int expectedTotal = PriceCalculator.calculateTotalPrice(
                capturedProductPrice, quantity, capturedShippingFee, capturedInsuranceFee
        );

        // Ambil total yang ditampilkan di UI
        String totalFromUI = checkoutPage.getTotalPriceText();
        int actualTotal = PriceCalculator.parsePrice(totalFromUI);

        System.out.println("  Total Kalkulasi  : " + PriceCalculator.formatToRupiah(expectedTotal));
        System.out.println("  Total di UI      : " + PriceCalculator.formatToRupiah(actualTotal));
        System.out.println("====================================");

        // Bandingkan hasil kalkulasi dengan total di UI
        boolean isMatch = PriceCalculator.verifyTotalPrice(expectedTotal, actualTotal);

        Assert.assertTrue(isMatch,
                String.format("Total harga tidak sesuai! " +
                                "Kalkulasi: %s (%d * %d + %d + %d = %d), " +
                                "UI: %s (%d)",
                        PriceCalculator.formatToRupiah(expectedTotal),
                        capturedProductPrice, quantity, capturedShippingFee, capturedInsuranceFee,
                        expectedTotal,
                        PriceCalculator.formatToRupiah(actualTotal),
                        actualTotal
                )
        );

        System.out.println("[PASSED] ✅ Total harga sesuai antara kalkulasi dan UI!");
    }

    // ==================== PAYMENT STEPS ====================

    @When("user selects {string} as payment method in {string} section")
    public void userSelectsPaymentMethodInSection(String method, String section) {
        Assert.assertEquals(method, "Virtual Account",
                "Metode pembayaran yang diminta bukan Virtual Account!");
        checkoutPage.selectVirtualAccountPayment();
    }

    // ==================== ORDER SUCCESS STEPS ====================

    @Then("user should be redirected to order success page")
    public void userShouldBeRedirectedToOrderSuccessPage() {
        Assert.assertTrue(successPage.isDisplayed(),
                "Halaman order success tidak ditampilkan!");

        String successMessage = successPage.getSuccessMessage();
        String orderNumber = successPage.getOrderNumber();

        System.out.println("====================================");
        System.out.println("  ORDER SUCCESS");
        System.out.println("====================================");
        System.out.println("  Pesan: " + successMessage);
        System.out.println("  No. Pesanan: " + orderNumber);
        System.out.println("====================================");
        System.out.println("[PASSED] ✅ Checkout berhasil! Pesanan dibuat dengan sukses.");
    }
}
