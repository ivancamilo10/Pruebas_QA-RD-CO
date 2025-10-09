package tests.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AlertTests extends BaseTest {

    private final String url = "https://qa-practice.netlify.app/alerts";

    // Botones reales de la página
    private final By btnAlert   = By.id("alert-btn");
    private final By btnConfirm = By.id("confirm-btn");

    // Textos que muestra el sitio (ajusta si cambian)
    private static final String ALERT_TEXT   = "Hello! I am an alert box!!";
    private static final String CONFIRM_TEXT = "Press a button!\nEither OK or Cancel.";

    // ===== helpers locales =====
    public Alert waitAlert() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
    }

    private boolean noAlertPresent() {
        try { driver.switchTo().alert(); return false; }
        catch (NoAlertPresentException e) { return true; }
    }

    // ===== tests =====

    @Test
    @DisplayName("ALERT • validar texto y aceptar")
    void alert_text_and_accept() {
        driver.get(url);
        waitDocReady();

        click(btnAlert);
        Alert a = waitAlert();

        // 1) el texto exacto de la alerta (lo que realmente qu                                                                                                                                                                                                                                                                                                                                                                                                                    eremos enseñar)
        assertEquals(ALERT_TEXT, a.getText(), "El texto del alert no coincide");
        // 2) la cerramos
        a.accept();

        // 3) verificación mínima: ya no hay alerta y el botón es clickeable otra vez
        assertTrue(noAlertPresent(), "La alerta debería estar cerrada");
        wait.until(ExpectedConditions.elementToBeClickable(btnAlert));
    }

    @Test
    @DisplayName("CONFIRM • validar texto, cancelar y luego aceptar al reabrir")
    void confirm_text_cancel_then_accept() {
        driver.get(url);
        waitDocReady();

        // — Primera vez: cancelamos
        click(btnConfirm);
        Alert c1 = waitAlert();
        assertEquals(CONFIRM_TEXT, c1.getText(), "El texto del confirm no coincide");
        c1.dismiss();
        assertTrue(noAlertPresent(), "La confirm debería estar cerrada tras cancelar");
        wait.until(ExpectedConditions.elementToBeClickable(btnConfirm));

        // — Segunda vez: aceptamos (demostramos que se puede reabrir sin efectos laterales)
        click(btnConfirm);
        Alert c2 = waitAlert();
        assertEquals(CONFIRM_TEXT, c2.getText(), "El texto del confirm no coincide (segunda vez)");
        c2.accept();

        assertTrue(noAlertPresent(), "La confirm debería estar cerrada tras aceptar");
        wait.until(ExpectedConditions.elementToBeClickable(btnConfirm));
    }
}



