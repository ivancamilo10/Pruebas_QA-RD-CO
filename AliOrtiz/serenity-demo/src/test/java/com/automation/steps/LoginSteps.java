package com.automation.steps;

import com.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.assertj.core.api.Assertions.assertThat;
import net.thucydides.core.annotations.Steps;

public class LoginSteps {

    @Steps
    private LoginPage loginPage;

    @Given("el usuario está en la página de login")
    public void usuarioEnPaginaLogin() {
        loginPage.abrirPagina();
    }

    @When("ingresa usuario y contraseña válidos")
    public void ingresaCredenciales() {
        loginPage.ingresarCredenciales("usuario_demo", "contraseña_demo");
        loginPage.hacerClickLogin();
    }

    @Then("debería ver la página de inicio")
    public void deberiaVerPaginaInicio() {
        // Validación real de un elemento de la página de inicio
        assertThat(loginPage.obtenerTituloPagina()).contains("Inicio");
    }
}
