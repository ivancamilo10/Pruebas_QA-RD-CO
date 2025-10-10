package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryPage extends BasePage {

    private By inventoryItems = By.className("inventory_item");
    private By addToCartButtonRelative = By.xpath("//*[@id=\"inventory_container\"]/div/div[1]/div[3]/button");
    private By shoppingCartLink = By.xpath("//*[@id=\"shopping_cart_container\"]/a");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAllProducts() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(inventoryItems));
        return driver.findElements(inventoryItems);
    }

    public void addProductByIndice(List<Integer> indices) {
        List<WebElement> products = getAllProducts();
        for (Integer idx : indices) {
            if (idx >= 0 && idx < products.size()) {
                WebElement product = products.get(idx);
                WebElement addBtn = product.findElement(addToCartButtonRelative);
                addBtn.click();
                wait.until(ExpectedConditions.textToBePresentInElement(addBtn, "REMOVE"));
            }
        }
    }

    public List<Integer> addRandomProducts(int count) {
        List<WebElement> products = getAllProducts();
        int total = products.size();
        List<Integer> indices = new ArrayList<>();
        if (count <= 0) {
            return indices;
        }

        Random rnd = new Random();

        while (indices.size() < Math.min(count, total)) {
            int candidate = rnd.nextInt(total);
            if (!indices.contains(candidate))
                indices.add(candidate);
        }
        addProductByIndice(indices);
        return indices;
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCartLink));
        driver.findElement(shoppingCartLink).click();
    }
}
