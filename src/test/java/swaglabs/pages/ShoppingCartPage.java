package swaglabs.pages;

import notaframework.InjectedContext;
import notaframework.Page;

public class ShoppingCartPage extends Page {
    static private final String title = "//span[@data-test=\"title\"]";
    static private final String checkoutButton = "//button[@data-test=\"checkout\"]";
    static private final String firstInput = "//input[@data-test=\"firstName\"]";
    static private final String lastInput = "//input[@data-test=\"lastName\"]";
    static private final String zipcodeInput = "//input[@data-test=\"postalCode\"]";
    static private final String continueButton = "//input[@data-test=\"continue\"]";
    static private final String finishButton = "//button[@data-test=\"finish\"]";
    static private final String completeHeader = "//h2[@data-test=\"complete-header\"]";


    public ShoppingCartPage(InjectedContext injectedContext) {
        super(injectedContext.getBrowser(), injectedContext);
    }

    public ShoppingCartPage waitForPageToLoad() {
        browser.waitForVisibilityOfElement(title);
        return this;
    }

    public ShoppingCartPage checkout() {
        browser.click(checkoutButton);
        return this;
    }

    public ShoppingCartPage enterCheckoutInfo(String firstName, String lastName, String zipcode) {
        browser.sendKeys(firstInput, firstName);
        browser.sendKeys(lastInput, lastName);
        browser.sendKeys(zipcodeInput, zipcode);
        browser.click(continueButton);
        return this;
    }

    public ShoppingCartPage finishCheckout() {
        browser.click(finishButton);
        return this;
    }

    public boolean isPurchaseConfirmationVisible() {
        return browser.isVisibilityOfElement(completeHeader);
    }
}
