package com.stiven;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenGoogle {
    public static void main(String[] args) {
        // 1) WebDriverManager baja y configura chromedriver adecuado
        WebDriverManager.chromedriver().setup();

        // 2) Creamos el driver de Chrome y abrimos la página
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

        // 3) Imprimimos el título (prueba rápida)
        System.out.println("Título: " + driver.getTitle());

        // 4) Cerramos el navegador
        driver.quit();
    }
}
