package qaproject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        // Ruta del driver Edge
        System.setProperty("webdriver.edge.driver", "C:\\Users\\ivmejia\\Downloads\\WebDrivers\\msedgedriver.exe");

        WebDriver driver = new EdgeDriver();

        try {
            // URL del producto
            driver.get("https://articulo.mercadolibre.com.co/MCO-1011552096-torre-cpu-gamer-ryzen-9-7950-amd-radeon-1tb-32gb-pc-_JM");

            // Espera explícita
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Nombre del producto
            WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("h1.ui-pdp-title")
            ));
            String productName = nameElement.getText();

            // Precio del producto
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("span.andes-money-amount__fraction")
            ));
            String productPrice = priceElement.getText();

            // Descripción del producto
            WebElement descElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("p.ui-pdp-description__content")
            ));
            // Limpiar saltos de línea y <br>
            String productDescription = descElement.getText().replace("\n", " ").replace("\r", " ");

            // Mostrar información
            System.out.println("Producto: " + productName);
            System.out.println("Precio: $" + productPrice);
            System.out.println("Descripción: " + productDescription);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
