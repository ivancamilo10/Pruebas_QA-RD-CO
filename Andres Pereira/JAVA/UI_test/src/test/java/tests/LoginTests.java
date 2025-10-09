package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests extends BaseTest {

    @Test
    @DisplayName("T01 - Login correcto")
    void loginOk() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.open(baseUrl);
        lp.login("admin@admin.com", "admin123");
        lp.waitForResult();

        if (!lp.isLoggedIn()) {
            System.out.println("Mensaje de error: " + lp.errorText());
            takeScreenshot("loginOk_fallo");
        }
        assertTrue(lp.isLoggedIn(), "Tras login debería verse el link 'Log Out' (id=logout).");
    }

    @Test
    @DisplayName("T02 - Credenciales inválidas")
    void loginInvalido() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.open(baseUrl);
        lp.login("admin@admin.com", "clave_mala");
        lp.waitForResult();

        String err = lp.errorText();
        if (err.isBlank()) takeScreenshot("loginInvalido_sin_mensaje");

        assertTrue(
                err.toLowerCase().contains("bad credentials") || err.toLowerCase().contains("invalid"),
                "Debería mostrarse mensaje de error."
        );
    }

    @Test
    @DisplayName("T03 - Campos requeridos")
    void camposRequeridos() {
        LoginPage lp = new LoginPage(driver, wait);
        lp.open(baseUrl);
        lp.login("", "");
        lp.waitForResult();

        if (lp.isLoggedIn()) takeScreenshot("camposRequeridos_fallo");
        assertFalse(lp.isLoggedIn(), "No debería loguear con campos vacíos.");
    }
}