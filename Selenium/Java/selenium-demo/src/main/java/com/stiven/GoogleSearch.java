package com.stiven;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleSearch {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");

        // 1) Localizamos la caja de búsqueda por 'name'
        WebElement searchBox = driver.findElement(By.name("q"));

        // 2) Escribimos en la caja
        searchBox.sendKeys("Selenium WebDriver");

        // 3) Simulamos ENTER
        searchBox.sendKeys(Keys.RETURN);

        // 4) Imprimimos el título de la página de resultados
        System.out.println("Nuevo título: " + driver.getTitle());

        driver.quit();
    }
}
