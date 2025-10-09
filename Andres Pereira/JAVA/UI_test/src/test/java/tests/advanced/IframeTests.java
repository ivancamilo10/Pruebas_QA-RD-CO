package tests.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.junit.jupiter.api.*;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class IframeTests extends BaseTest {

    private final String url = "https://qa-practice.netlify.app/iframe";

    @Test
    @DisplayName("IFRAME • entrar al iframe y volver al DOM principal")
    void iframe_basic() {
        driver.get(url);
        waitDocReady();  // 1) sincronización base

        // 2) localizar el iframe; si la página tuviera ID/clase, úsalo en lugar del CSS genérico.
        By frame = By.cssSelector("iframe");

        // 3) cambiar de contexto con espera explícita (evita NoSuchFrame / timing issues)
        switchToFrame(frame);

        // 4) pequeña verificación de que estamos “dentro”
        Boolean inside = (Boolean) js("return window.self !== window.top;");
        assertTrue(inside, "Deberíamos estar dentro del iframe");

        // (opcional) si conoces un elemento dentro del frame, interactúa aquí
        // By input = By.cssSelector("input, textarea");
        // if (!driver.findElements(input).isEmpty()) type(input, "hola iframe");

        // 5) volver SIEMPRE al documento principal
        switchToDefault();
        Boolean backToTop = !(Boolean) js("return window.self !== window.top;");
        assertTrue(backToTop, "Deberíamos haber vuelto al DOM principal");
    }
}
