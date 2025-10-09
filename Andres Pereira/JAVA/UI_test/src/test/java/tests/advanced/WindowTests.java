package tests.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WindowTests extends BaseTest {

    private final String url = "https://qa-practice.netlify.app/window";

    @Test
    @DisplayName("WINDOW • abrir nueva ventana y regresar al original")
    void window_newTab() {
        driver.get(url);
        waitDocReady();

        // En esa página el link que abre la nueva tab es un <a target="_blank">
        By openNew = By.cssSelector("a[target='_blank'], button[onclick*='window']");

        clickSafe(openNew);

        // 1) Esperar 2 ventanas y pasar a la nueva
        String original = switchToNewWindow(2);

        // 2) Esperar a que la nueva pestaña deje de estar “vacía” (título/url)
        wait.until(d -> {
            String t = d.getTitle();
            String u = d.getCurrentUrl();
            return (t != null && !t.isBlank()) && u != null && !u.equals("about:blank");
        });

        assertFalse(driver.getTitle().isBlank(), "La nueva ventana debería tener un título");

        // 3) Cerrar y volver al original
        closeCurrentAndReturn(original);
    }

    /** Click robusto (JS) + pequeña espera */
    private void clickSafe(By locator) {
        var el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", el);
        demoPause();
    }
}


