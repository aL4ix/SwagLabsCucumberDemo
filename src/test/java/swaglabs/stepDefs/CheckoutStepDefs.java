package swaglabs.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import swaglabs.pages.ShoppingCartPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutStepDefs {
    ShoppingCartPage shoppingCartPage;

    public CheckoutStepDefs(ShoppingCartPage shoppingCartPage) {
        this.shoppingCartPage = shoppingCartPage;
    }

    @And("I checkout")
    public void iCheckout() {
        shoppingCartPage.waitForPageToLoad().checkout();
    }

    @And("I enter {string} {string} {string} into the checkout information")
    public void iEnterIntoTheCheckoutInformation(String firstName, String lastName, String zipcode) {
        shoppingCartPage.enterCheckoutInfo(firstName, lastName, zipcode);
    }

    @When("I finish the checkout")
    public void iFinishTheCheckout() {
        shoppingCartPage.finishCheckout();
    }

    @Then("I see the purchase confirmation")
    public void iSeeThePurchaseConfirmation() {
        assertTrue(shoppingCartPage.isPurchaseConfirmationVisible());
    }
}
