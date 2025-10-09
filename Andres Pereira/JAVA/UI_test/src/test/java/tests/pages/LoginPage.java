package tests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.cssSelector("input[placeholder='Email'], input[type='email']");
    private final By passInput  = By.cssSelector("input[placeholder='Password'], input[type='password']");
    private final By submitBtn  = By.xpath("//button[normalize-space()='Submit' or @type='submit']");
    private final By errorBox   = By.id("message");

    // ✅ tomado de tu captura: <a id="logout" ...>Log Out</a>
    private final By logoutLink = By.id("logout");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver; this.wait = wait;
    }

    public void open(String baseUrl) { driver.get(baseUrl); }

    public void login(String email, String password) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passInput).clear();
        driver.findElement(passInput).sendKeys(password);
        driver.findElement(submitBtn).click();
    }

    /** Espera a que ocurra éxito (logout visible) o error. */
    public void waitForResult() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(logoutLink),
                        ExpectedConditions.presenceOfElementLocated(errorBox)
                ));
    }

    /** ¿Login exitoso? (logout presente) */
    public boolean isLoggedIn() {
        return !driver.findElements(logoutLink).isEmpty();
    }

    public String errorText() {
        if (driver.findElements(errorBox).isEmpty()) return "";
        return driver.findElement(errorBox).getText().trim();
    }
}


