package com.automation.runners;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features", // ruta a tus archivos .feature
        glue = "com.automation.steps",            // paquete donde están tus step definitions
        plugin = {"pretty"}                        // opcional: ver salida legible en consola
)
public class LoginTestRunner {
    // No se necesita nada más aquí
}
