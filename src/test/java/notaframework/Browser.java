package notaframework;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

public class Browser {
    public static final int TIME_OUT_IN_SECONDS = 30;
    public static final int SHORT_TIME_OUT_IN_SECONDS = 5;
    public WebDriver driver;
    private final String browserName;

    public Browser(String name) throws WebDriverException {
        browserName = name.toLowerCase();

        if ("firefox".equals(browserName)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.startsWith("chrome")) {
            WebDriverManager.chromedriver().setup();
            if (browserName.contains("device")) {
                Map<String, Object> deviceMetrics = new HashMap<>();
                deviceMetrics.put("width", 1024);
                deviceMetrics.put("height", 768);
                deviceMetrics.put("pixelRatio", 2.0);

                Map<String, Object> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceMetrics", deviceMetrics);
                mobileEmulation.put("userAgent", "Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                driver = new ChromeDriver(chromeOptions);
            } else {
                boolean enableSpecialArguments = false;
                if (enableSpecialArguments) {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless", "--disable-dev-shm-usage", "--no-sandbox", "--disable-gpu");
                    driver = new ChromeDriver(chromeOptions);
                } else {
                    driver = new ChromeDriver();
                }
            }
        } else if ("android".equals(browserName)) {
            try {
                URL remoteAddress = new URL("http://127.0.0.1:4723/");

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("deviceName", "emulator-5554");
                ChromeOptions options = new ChromeOptions();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                UiAutomator2Options appiumOptions = new UiAutomator2Options().merge(capabilities);
                System.out.println(appiumOptions.asMap());
                driver = new AndroidDriver(remoteAddress, appiumOptions);

            } catch (MalformedURLException e) {
                throw new WebDriverException(e);
            }
        } else {
            throw new WebDriverException(String.format("Could not understand desired browser: %s", browserName));
        }
//        driver.manage().window().maximize();
    }

    private WebElement findElementWithPresenceOfElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        scrollIntoView(element);
        return element;
    }

    private WebElement findElementWithVisibilityOfElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        scrollIntoView(element);
        return element;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private WebElement findElementWithClickAbilityOfElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return element;
    }

    public boolean isVisibilityOfElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(SHORT_TIME_OUT_IN_SECONDS));
        boolean result = true;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            result = false;
        }
        return result;
    }

    protected boolean isInvisibilityOfElement(String xpath, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        boolean result = true;
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            result = false;
        }
        return result;
    }

    @SuppressWarnings("unused")
    public boolean isInvisibilityOfElementShortWait(String xpath) {
        return isInvisibilityOfElement(xpath, SHORT_TIME_OUT_IN_SECONDS);
    }

    @SuppressWarnings("unused")
    public boolean isInvisibilityOfElementNormalWait(String xpath) {
        return isInvisibilityOfElement(xpath, TIME_OUT_IN_SECONDS);
    }

    @SuppressWarnings("unused")
    public void waitForVisibilityOfElement(String xpath) {
        findElementWithVisibilityOfElement(xpath);
    }

    @SuppressWarnings("unused")
    public void waitForClickAbilityOfElement(String xpath) {
        findElementWithClickAbilityOfElement(xpath);
    }

    @SuppressWarnings("unused")
    public void waitForInvisibilityOfElement(String xpath) {
        if (!isInvisibilityOfElement(xpath, TIME_OUT_IN_SECONDS)) {
            throw new TimeoutException(String.format("Element %s didn't disappear after %s seconds", xpath, TIME_OUT_IN_SECONDS));
        }
    }

    @SuppressWarnings("unused")
    public void waitForPresenceOfElement(String xpath) {
        findElementWithPresenceOfElement(xpath);
    }

    public void sendKeys(String xpath, String text) {
        sendKeys(xpath, text, true);
    }

    @SuppressWarnings("unused")
    public void sendKeysButFirstClearManually(String xpath, String text) {
        clearManually(xpath);
        sendKeys(xpath, text, true);
    }

    public void sendKeys(String xpath, String text, boolean waitForVisibility) {
        WebElement element;
        if (waitForVisibility) {
            element = findElementWithVisibilityOfElement(xpath);
        } else {
            element = findElementWithPresenceOfElement(xpath);
        }
        element.sendKeys(text);
    }

    @SuppressWarnings("unused")
    public void sendKeysByKey(String xpath, String keys) {
        WebElement element = findElementWithVisibilityOfElement(xpath);
        for (String key : keys.split("\\|")) {
            Keys seleniumKey = Keys.valueOf(key);
            element.sendKeys(seleniumKey);
        }
    }

    @SuppressWarnings("unused")
    public void actionsSendKeys(String keys) {
        Actions actions = new Actions(driver);
        for (String key : keys.split("\\|")) {
            Keys seleniumKey = Keys.valueOf(key);
            actions.sendKeys(seleniumKey);
        }
        actions.build().perform();
    }

    @SuppressWarnings("unused")
    public void clear(String xpath) {
        WebElement element = findElementWithVisibilityOfElement(xpath);
        element.clear();
    }

    public void clearManually(String xpath) {
        WebElement element = findElementWithVisibilityOfElement(xpath);
        element.sendKeys(Keys.END);
        element.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        element.sendKeys(Keys.BACK_SPACE);
        try {
            element.sendKeys("");
        } catch (StaleElementReferenceException e) {
            element = findElementWithVisibilityOfElement(xpath);
            element.sendKeys("");
        }
    }

    private void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @SuppressWarnings("unused")
    public void scrollBy(String xpath, int x, int y) {
        WebElement element = findElementWithPresenceOfElement(xpath);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollBy(arguments[1], arguments[2]);", element, x, y);
    }

    private WebElement getClickableElement(String xpath) {
        WebElement element = findElementWithClickAbilityOfElement(xpath);
        scrollIntoView(element);
        return element;
    }

    public void click(String xpath) {
        WebElement element = getClickableElement(xpath);
        try {
            element.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            element = getClickableElement(xpath);
            element.click();
        }
    }

    @SuppressWarnings("unused")
    public void actionsDoubleClick(String xpath) {
        WebElement element = getClickableElement(xpath);
        try {
            new Actions(driver).doubleClick(element).perform();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            element = getClickableElement(xpath);
            new Actions(driver).doubleClick(element).perform();
        }
    }

    @SuppressWarnings("unused")
    public String getText(String xpath) {
        WebElement element = findElementWithVisibilityOfElement(xpath);
        return element.getText();
    }

    public void get(String url) {
        driver.get(url);
    }

    public void quit() {
        driver.quit();
    }

    @SuppressWarnings({"UnnecessaryLocalVariable", "unused"})
    public File getScreenshot() {
        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
        File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
        return screenshotFile;
    }

    private String getAttributeOfElement(WebElement element, String attribute) {
        String value;
        if ("".equals(attribute)) {
            value = element.getText();
        } else {
            value = element.getAttribute(attribute);
        }
        return value;
    }

    @SuppressWarnings("unused")
    public String getAttribute(String xpath, String attribute) {
        WebElement element = findElementWithPresenceOfElement(xpath);
        return getAttributeOfElement(element, attribute);
    }

    public List<String> getAttributeOfElements(SearchContext searchContext, String xpath, String attribute) {
        ArrayList<String> results = new ArrayList<>();
        for (WebElement element : searchContext.findElements(By.xpath(xpath))) {
            results.add(getAttributeOfElement(element, attribute));
        }
        return results;
    }

    @SuppressWarnings("unused")
    public List<String> getAttributeOfElements(String xpath, String attribute) {
        waitForPresenceOfElement(xpath);
        return getAttributeOfElements(driver, xpath, attribute);
    }

    @SuppressWarnings("unused")
    public void setAttributeOfElement(String xpath, String attribute, String value) {
        WebElement element = findElementWithPresenceOfElement(xpath);
        setAttributeOfElement(element, attribute, value);
    }

    public void setAttributeOfElement(WebElement element, String attribute, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attribute, value);
    }

    private List<WebElement> findElements(SearchContext searchContext, String xpath) {
        return searchContext.findElements(By.xpath(xpath));
    }

    @SuppressWarnings("unused")
    public List<WebElement> findElements(String xpath) {
        return findElements(driver, xpath);
    }

    @SuppressWarnings("unused")
    public void selectByVisibleText(String xpath, String text) {
        WebElement element = findElementWithClickAbilityOfElement(xpath);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    @SuppressWarnings("unused")
    public void navigateBack() {
        driver.navigate().back();
    }

    @SuppressWarnings("unused")
    public boolean isSelected(String xpath) {
        WebElement element = findElementWithVisibilityOfElement(xpath);
        return element.isSelected();
    }

    @SuppressWarnings("unused")
    public void moveToElement(String xpath) {
        WebElement element = findElementWithPresenceOfElement(xpath);
        new Actions(driver).moveToElement(element).perform();
    }

    @SuppressWarnings("unused")
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @SuppressWarnings("unused")
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @SuppressWarnings("unused")
    public void switchToWindow(String nameOrHandle) {
        driver.switchTo().window(nameOrHandle);
    }

    @SuppressWarnings("unused")
    public Object executeScript(String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script);
    }

    @SuppressWarnings("unused")
    public void navigateRefresh() {
        driver.navigate().refresh();
    }

    @SuppressWarnings("unused")
    static public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    static public String formatXPath(String xpath, Object... args) {
        Object[] processedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof String stringArg) {
                if (stringArg.contains("\"") && stringArg.contains("'")) {
                    stringArg = String.format("concat(%s)", String.join(", '\"',", stringArg.split("\"")));
                } else if (stringArg.contains("\"")) {
                    stringArg = String.format("'%s'", stringArg);
                } else {
                    stringArg = String.format("\"%s\"", stringArg);
                }
                arg = stringArg;
            }
            processedArgs[i] = arg;
        }
        return String.format(xpath, processedArgs);
    }

    @Override
    public String toString() {
        return "Browser{" +
                "name='" + browserName + '\'' +
                '}';
    }
}
