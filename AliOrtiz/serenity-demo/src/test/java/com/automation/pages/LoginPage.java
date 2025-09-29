package com.automation.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(id = "username")
    private WebElementFacade usernameField;

    @FindBy(id = "password")
    private WebElementFacade passwordField;

    @FindBy(id = "login-button")
    private WebElementFacade loginButton;

    @FindBy(css = "h1.page-title") // Cambia esto por un selector real de tu página de inicio
    private WebElementFacade tituloPaginaInicio;

    public void abrirPagina() {
        openUrl("https://example.com/login"); // Cambia por tu URL real
    }

    public void ingresarCredenciales(String usuario, String contraseña) {
        usernameField.type(usuario);
        passwordField.type(contraseña);
    }

    public void hacerClickLogin() {
        loginButton.click();
    }

    // Método para obtener el título de la página de inicio
    public String obtenerTituloPagina() {
        return tituloPaginaInicio.getText();
    }
}
