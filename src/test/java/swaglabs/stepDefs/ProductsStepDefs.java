package swaglabs.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import swaglabs.pages.ProductsPage;

public class ProductsStepDefs {

    public ProductsStepDefs(ProductsPage productsPage) {
        this.productsPage = productsPage;
    }

    ProductsPage productsPage;
    @Given("I add product {string} to the cart")
    public void iAddProductToTheCart(String productName) {
        productsPage.waitForPageToLoad().addProductToTheCart(productName);
    }

    @And("I go to the shopping cart")
    public void iGoToTheShoppingCart() {
        productsPage.goToTheShoppingCart();
    }
}
