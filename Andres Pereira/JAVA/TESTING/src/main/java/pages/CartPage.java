package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {
    private By checkoutButton = By.xpath("//*[@id=\"cart_contents_container\"]/div/div[2]/a[2]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        driver.findElement(checkoutButton).click();
    }
}
