package utils;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Gauge;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final String USERNAME = System.getenv("browserStackUsername");
    private final String AUTOMATE_KEY = System.getenv("browserStackAccessKey");
    private final String REMOTE_URL = String.format("https://%s:%s@hub-cloud.browserstack.com/wd/hub", USERNAME, AUTOMATE_KEY);

    @BeforeScenario(tags = "web")
    public void driverSetUp() throws MalformedURLException {
        WebDriver driver = null;
        String browser = System.getenv("browser");

        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty(System.getenv("driverProperty"), System.getenv("driverPath"));
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(true);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                System.setProperty(System.getenv("driverProperty"), System.getenv("driverPath"));
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(true);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                System.setProperty(System.getenv("driverProperty"), System.getenv("driverPath"));
                driver = new EdgeDriver();
                break;
            case "remote":
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("name", "Google Search tests");
                caps.setCapability("browserstack.debug", "true");
                caps.setCapability("acceptSslCerts", true);
                caps.setCapability("browserstack.geoLocation", "GB");
                caps.setCapability("browser", System.getenv("remoteBrowser"));
                caps.setCapability("os", System.getenv("operatingSystem"));
                caps.setCapability("os_version", System.getenv("operatingSystemVersion"));
                driver = new RemoteWebDriver(new URL(REMOTE_URL), caps);
                break;
        }
        setDriver(driver);
    }

    @AfterScenario(tags = "web")
    public void cleanUpBrowserAfterScenario() {
        Gauge.captureScreenshot();
        getDriver().quit();
        deleteDriver();
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    private void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    private void deleteDriver() {
        driverThreadLocal.remove();
    }
}
