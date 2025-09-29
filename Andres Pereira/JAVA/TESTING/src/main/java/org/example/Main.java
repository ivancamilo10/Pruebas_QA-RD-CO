package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\apereira\\Apps\\msedgedriver.exe");

        WebDriver driver = new EdgeDriver();

        try {
            driver.get("https://www.google.com");

            // Espera explícita para el cuadro de búsqueda
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));

            // Escribir consulta y ejecutar búsqueda
            searchBox.sendKeys("Ingieneria en sistemas");
            searchBox.sendKeys(Keys.ENTER);

            // Esperar que los resultados estén visibles
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));

            // Obtener los resultados de búsqueda (pueden variar, aquí tomamos los títulos y
            // links de resultados principales)
            List<WebElement> results = driver.findElements(By.cssSelector("div#search h3"));

            System.out.println("Resultados de búsqueda:");
            for (int i = 0; i < Math.min(results.size(), 5); i++) {
                WebElement titleElement = results.get(i);
                WebElement parentLink = titleElement.findElement(By.xpath("./ancestor::a"));
                String title = titleElement.getText();
                String url = parentLink.getAttribute("href");

                System.out.println((i + 1) + ". " + title);
                System.out.println("   URL: " + url);
            }

            // Tomar screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("google_search_results.png");
            Files.copy(screenshot.toPath(), destination.toPath());

            System.out.println("Screenshot guardado en: " + destination.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
