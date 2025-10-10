package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueBtn = By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[2]/input");
    private By finishBtn = By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[8]/a[2]");
    private By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillInformationAndFinish(String fName, String lName, String code) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(postalCode).sendKeys(code);
        driver.findElement(continueBtn).click();

        // esperar la página de overview y terminar
        wait.until(ExpectedConditions.elementToBeClickable(finishBtn));
        driver.findElement(finishBtn).click();

        // confirmar que la compra finalizó
        wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
    }

    public boolean isComplete() {
        return !driver.findElements(completeHeader).isEmpty();
    }
}
