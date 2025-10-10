package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private WebDriver driver;

    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By credencialesLogin = By.id("login_credentials");
    private By credencialesPassword = By.className("login_password");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login() {
        WebElement userContainer = driver.findElement(credencialesLogin);
        String[] usernames = userContainer.getText().split("\\n");
        String username = usernames[1];

        WebElement passContainer = driver.findElement(credencialesPassword);
        String[] passwords = passContainer.getText().split("\\n");
        String password = passwords[1];

        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("inventory.html");
    }
}
