import org.openqa.selenium.WebDriver;
import pages.CheckoutPage;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = DriverFactory.inDriver();
        try {
            driver.get("https://www.saucedemo.com/v1/");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.login();

            if (!loginPage.isLoginSuccessful()) {
                System.out.println("Login fallido");
                return;
            }
            System.out.println("Login exitoso.");

            InventoryPage inventory = new InventoryPage(driver);

            // elegir aleatoriamente 2 o 3 productos (ejemplo: random entre 2 y 3)
            int toBuy = 2 + (int)(Math.random() * 2); // da 2 o 3
            System.out.println("Se agregarán " + toBuy + " productos al carrito.");

            List<Integer> chosen = inventory.addRandomProducts(toBuy);
            System.out.println("Indices elegidos: " + chosen);

            // abrir carrito y proceder al checkout
            inventory.openCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.proceedToCheckout();

            // llenar datos y finalizar
            CheckoutPage checkout = new CheckoutPage(driver);
            checkout.fillInformationAndFinish("Andres", "Pereira", "11001");

            if (checkout.isComplete()) {
                System.out.println("Compra completada");
            } else {
                System.out.println("Compra no completada");
            }

            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            driver.quit();
        }
    }
}
