package notaframework;

public class InjectedContext {
    private Browser browser = null;
    private final String browserName;

    public InjectedContext() {
        browserName = "firefox";
//        browserName = "android";
    }

    public Browser getBrowser() {
        if (browser == null) {
            browser = new Browser(browserName);
        }
        return browser;
    }
}
