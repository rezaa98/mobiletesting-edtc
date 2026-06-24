Feature: Klik Indomaret Checkout via Virtual Account
  As a customer of Klik Indomaret
  I want to purchase a product and pay using Virtual Account
  So that I can complete my order successfully

  @checkout @e2e
  Scenario: Purchase product with Virtual Account payment
    Given user is on the login page
    When user logs in with valid credentials
    Then user should be on the home page

    When user searches for a product
    Then search results should be displayed

    When user opens the product detail page
    Then product detail page should be displayed
    And user captures the product price

    When user adds the product to cart
    Then product should be added to cart successfully

    When user proceeds to cart
    And user chooses delivery method
    Then delivery fee should be displayed
    And user captures the shipping fee and insurance fee

    When user clicks the "Beli" button
    Then checkout page should be displayed

    When user verifies the price calculation
    Then total price should equal product price plus shipping fee plus insurance fee

    When user selects "Virtual Account" as payment method in "Pembayaran" section
    And user clicks the "Bayar sekarang" button
    Then user should be redirected to order success page
