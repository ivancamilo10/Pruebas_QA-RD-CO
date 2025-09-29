from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time

service = Service(r"C:\EJERCICIOS DE PRUEBAS\PROGRAMAS\Drivers\chromedriver.exe")
driver = webdriver.Chrome(service=service)

driver.get("https://www.mercadolibre.com.co/")

print(driver.title)

search_input = driver.find_element(By.ID, "cb1-edit")
search_input.clear()
search_input.send_keys("iphone")
search_input.send_keys(Keys.RETURN)

time.sleep(3)  # Espera a que cargue la página de resultados
driver.save_screenshot("mercadolibre_iphone.png")
# Encuentra todos los títulos de los productos
titulo_productos = driver.find_elements(By.CSS_SELECTOR, "h2.ui-search-item__title")

for titulo in titulo_productos:
    print(titulo.text)

time.sleep(2)
driver.close()
