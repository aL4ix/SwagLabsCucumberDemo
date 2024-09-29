package swaglabs.pages;

import notaframework.InjectedContext;
import notaframework.Page;

public class ProductsPage extends Page {
    static private final String title = "//span[@data-test=\"title\"]";
    static private final String addToCart = "//button[@data-test=\"add-to-cart-sauce-labs-backpack\"]";
    static private final String shoppingCart = "//a[@data-test=\"shopping-cart-link\"]";


    public ProductsPage(InjectedContext injectedContext) {
        super(injectedContext.getBrowser(), injectedContext);
    }

    public ProductsPage waitForPageToLoad() {
        browser.waitForVisibilityOfElement(title);
        return this;
    }

    public ProductsPage addProductToTheCart(String productName) {
        browser.click(addToCart);
        return this;
    }

    public ShoppingCartPage goToTheShoppingCart() {
        browser.click(shoppingCart);
        return new ShoppingCartPage(injectedContext);
    }
}
