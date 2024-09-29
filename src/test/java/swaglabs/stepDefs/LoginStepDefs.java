package swaglabs.stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import notaframework.InjectedContext;
import swaglabs.pages.LoginPage;

public class LoginStepDefs {
    private final InjectedContext injectedContext;
    private Scenario scenario;
    private final LoginPage loginPage;

    public LoginStepDefs(LoginPage loginPage, InjectedContext injectedContext) {
        this.loginPage = loginPage;
        this.injectedContext = injectedContext;
    }

    @Given("I opened the site")
    public void iOpenedTheSite() {
        loginPage.goToLoginPage("https://www.saucedemo.com/");
    }

    @When("I login with user {string} with password {string}")
    public void iLoginWithUserWithPassword(String user, String pass) {
        loginPage.login(user, pass);
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @After
    public void after() {
        if (scenario.isFailed()) {
            scenario.log("Failed");
        }
        injectedContext.getBrowser().quit();
    }
}
