package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver inDriver() {
        System.setProperty("webdriver.edge.driver", "C:\\EJERCICIOS DE PRUEBAS\\DESC\\msedgedriver.exe");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/v1/");
        driver.manage().window().maximize();
        return driver;
    }
}
