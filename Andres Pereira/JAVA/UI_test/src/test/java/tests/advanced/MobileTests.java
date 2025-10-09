package tests.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import tests.BaseTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MobileTests extends BaseTest {

    @Test
    @DisplayName("MOBILE • header compacto (algún menú tipo hamburguesa se abre)")
    void mobile_header_compacto() {
        driver.get("https://qa-practice.netlify.app/");
        waitDocReady();

        // 1) Asegura vista móvil (solo informativo)
        Long w = (Long) js("return window.innerWidth;");
        System.out.println("innerWidth=" + w);
        assertTrue(w < 700, "El viewport debería ser estrecho (móvil). width=" + w);

        // 2) Intentar con el menú principal (derecha)
        By burgerMain = By.cssSelector("button.btn.btn-dark.d-inline-block.d-lg-none.ml-auto, button.navbar-toggler");
        List<WebElement> mains = driver.findElements(burgerMain);

        boolean opened = false;

        if (!mains.isEmpty() && mains.get(0).isDisplayed()) {
            WebElement btn = mains.get(0);
            btn.click();
            // Espera a que se abra: aria-expanded=true o .navbar-collapse.show
            opened = wait.until(d ->
                    "true".equalsIgnoreCase(btn.getAttribute("aria-expanded")) ||
                            !d.findElements(By.cssSelector("#navbarSupportedContent.show, nav .navbar-collapse.show")).isEmpty()
            );

        }

        // 3) Fallback: intentar con el sidebar (izquierda)
        if (!opened) {
            By sidebarBtn = By.id("sidebarCollapse");
            List<WebElement> sideBtns = driver.findElements(sidebarBtn);
            if (!sideBtns.isEmpty() && sideBtns.get(0).isDisplayed()) {
                WebElement sbtn = sideBtns.get(0);
                sbtn.click();
                // sidebar suele volverse 'active'
                WebElement sidebar = driver.findElement(By.id("sidebar"));
                opened = wait.until(d -> sidebar.getAttribute("class").contains("active"));
            }
        }

        assertTrue(opened, "Debería abrirse algún menú (navbar o sidebar) en vista móvil.");
        takeScreenshot("mobile_menu_opened");
    }
}