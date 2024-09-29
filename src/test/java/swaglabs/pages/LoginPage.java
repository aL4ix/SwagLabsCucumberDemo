package swaglabs.pages;

import notaframework.InjectedContext;
import notaframework.Page;

public class LoginPage extends Page {
    static private final String loginInput = "//input[@data-test=\"username\"]";
    static private final String passInput = "//input[@data-test=\"password\"]";
    static private final String loginbutton = "//input[@data-test=\"login-button\"]";

    public LoginPage(InjectedContext injectedContext) {
        super(injectedContext.getBrowser(), injectedContext);
    }

    public LoginPage goToLoginPage(String url) {
        browser.get(url);
        browser.waitForVisibilityOfElement(loginbutton);
        return this;
    }

    public ProductsPage login(String user, String pass) {
        browser.sendKeys(loginInput, user);
        browser.sendKeys(passInput, pass);
        browser.click(loginbutton);
        return new ProductsPage(injectedContext);
    }
}
