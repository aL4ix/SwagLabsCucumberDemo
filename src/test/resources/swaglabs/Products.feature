Feature: Login into the system

  Background: Login into page
    Given I opened the site
    When I login with user "standard_user" with password "secret_sauce"

  Scenario: Buy a product
    Given I add product "Sauce Labs Backpack" to the cart
    And I go to the shopping cart
    And I checkout
    And I enter "First" "Last" "Zipcode" into the checkout information
    When I finish the checkout
    Then I see the purchase confirmation

