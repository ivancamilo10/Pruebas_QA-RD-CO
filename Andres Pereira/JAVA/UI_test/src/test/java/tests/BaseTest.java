package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final String baseUrl = "https://qa-practice.netlify.app/auth_ecommerce";

    // Modo demo (pausas cortas para mostrar en vivo)
    protected boolean DEMO_MODE = Boolean.parseBoolean(System.getProperty("DEMO_MODE", isCI() ? "false" : "true"));
    protected long DELAY_MS = Long.getLong("DEMO_DELAY_MS", isCI() ? 0L : 300L);

    private static boolean isCI() {
        String tf = System.getenv("TF_BUILD");
        String ci = System.getenv("CI");
        String headlessProp = System.getProperty("HEADLESS");
        return "True".equalsIgnoreCase(tf) || "true".equalsIgnoreCase(ci) || "true".equalsIgnoreCase(headlessProp);
    }

    // ---------------- Driver factory (local / remoto / emulación móvil)
    // ----------------
    protected WebDriver createDriver() {
        String browser = System.getProperty("BROWSER", "chrome"); // chrome|firefox|edge
        boolean headless = Boolean.parseBoolean(System.getProperty("HEADLESS", String.valueOf(isCI())));
        boolean remote = Boolean.parseBoolean(System.getProperty("REMOTE", "false"));
        String gridUrl = System.getProperty("GRID_URL", "http://localhost:4444");
        String mobileDev = System.getProperty("MOBILE_DEVICE", ""); // ej: "Pixel 5" (Chrome/Edge)

        MutableCapabilities options;

        switch (browser.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions fx = new FirefoxOptions();
                if (headless)
                    fx.addArguments("-headless");
                options = fx;
            }
            case "edge" -> {
                EdgeOptions ed = new EdgeOptions();
                if (headless)
                    ed.addArguments("--headless=new", "--window-size=1920,1080");
                if (!mobileDev.isBlank()) {
                    Map<String, Object> mobile = new HashMap<>();
                    mobile.put("deviceName", mobileDev); // p.ej. "Pixel 5", "iPhone 12 Pro"
                    ed.setExperimentalOption("mobileEmulation", mobile);
                }
                options = ed;
            }
            default -> { // chrome
                ChromeOptions ch = new ChromeOptions();
                if (headless)
                    ch.addArguments("--headless=new", "--window-size=1920,1080");
                if (!mobileDev.isBlank()) {
                    Map<String, Object> mobile = new HashMap<>();
                    mobile.put("deviceName", mobileDev);
                    ch.setExperimentalOption("mobileEmulation", mobile);
                }
                options = ch;
            }
        }

        if (remote) {
            try {
                return new RemoteWebDriver(new URL(gridUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return switch (browser.toLowerCase()) {
                case "firefox" -> new FirefoxDriver((FirefoxOptions) options);
                case "edge" -> new EdgeDriver((EdgeOptions) options);
                default -> new ChromeDriver((ChromeOptions) options);
            };
        }
    }

    @BeforeEach
    void setUp() {
        driver = createDriver();

        // No maximizamos si estamos emulando un dispositivo móvil
        boolean usingMobile = !System.getProperty("MOBILE_DEVICE", "").isBlank();
        if (!Boolean.getBoolean("HEADLESS") && !usingMobile) {
            try {
                driver.manage().window().maximize();
            } catch (Exception ignored) {
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterEach
    void tearDown() {
        if (driver != null)
            driver.quit();
    }

    // ---------------- Utils de sincronización/UI ----------------
    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void type(By locator, String value) {
        WebElement el = waitVisible(locator);
        el.clear();
        el.sendKeys(value);
        demoPause();
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        demoPause();
    }

    protected void demoPause() {
        if (DEMO_MODE && DELAY_MS > 0) {
            try {
                Thread.sleep(DELAY_MS);
            } catch (InterruptedException ignored) {
            }
        }
    }

    protected void takeScreenshot(String name) {
        try {
            Files.createDirectories(Paths.get("build", "artifacts", "screenshots"));
            Path dst = Paths.get("build", "artifacts", "screenshots", name + ".png");
            Files.copy(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath(),
                    dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ignored) {
        }
    }

    protected void waitDocReady() {
        wait.until(d -> "complete".equals(((JavascriptExecutor) d)
                .executeScript("return document.readyState")));
    }

    protected void switchToFrame(By frameLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    protected void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    protected Object js(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    protected String switchToNewWindow() {
        String original = driver.getWindowHandle();
        wait.until(d -> d.getWindowHandles().size() > 1);
        for (String h : driver.getWindowHandles()) {
            if (!h.equals(original)) {
                driver.switchTo().window(h);
                break;
            }
        }
        return original;
    }

    protected String switchToNewWindow(int expectedCount) {
        String original = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(expectedCount));
        for (String h : driver.getWindowHandles()) {
            if (!h.equals(original)) {
                driver.switchTo().window(h);
                break;
            }
        }
        return original;
    }

    protected void closeCurrentAndReturn(String originalHandle) {
        driver.close();
        driver.switchTo().window(originalHandle);
    }

    protected Alert waitAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    protected String readAlertText() {
        return waitAlert().getText();
    }
}


