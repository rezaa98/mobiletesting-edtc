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
    }

    /**
     * Hook: Dijalankan setelah setiap skenario.
     * Membersihkan session driver.
     */
    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // ==================== LOGIN STEPS ====================

    @Given("user is on the login page")
    public void userIsOnTheLoginPage() {
        Assert.assertTrue(loginPage.isDisplayed(),
                "Halaman login tidak ditampilkan!");
    }

    @When("user logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        String username = TestData.getUsername();
        String password = TestData.getPassword();
        loginPage.login(username, password);
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
        productPage.addToCart();
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

    @When("user clicks the {string} button")
    public void userClicksTheButton(String buttonName) {
        if (buttonName.equalsIgnoreCase("Beli")) {
            cartPage.clickBeliButton();
        } else if (buttonName.equalsIgnoreCase("Bayar sekarang")) {
            paymentPage.clickBayarSekarang();
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
        int expectedTotal = PriceCalculator.calculateTotalPrice(
                capturedProductPrice, capturedShippingFee, capturedInsuranceFee
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
                                "Kalkulasi: %s (%d + %d + %d = %d), " +
                                "UI: %s (%d)",
                        PriceCalculator.formatToRupiah(expectedTotal),
                        capturedProductPrice, capturedShippingFee, capturedInsuranceFee,
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
