from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.keys import Keys
import time

service = Service(r"C:\EJERCICIOS DE PRUEBAS\PROGRAMAS\Drivers\chromedriver.exe")
driver = webdriver.Chrome(service=service)


try:
    driver.get("https://www.google.com")

    search_box = driver.find_element(By.NAME, "q")

    search_box.send_keys("Python selenium tutorial")

    search_box.send_keys(Keys.RETURN)   

    time.sleep(3)  # Espera a que cargue la página de resultados

    result = driver.find_element(By.CSS_SELECTOR, "h3.LC20lb MBeuO DKV0Md")

    for r in result:
        print(r.text)
finally:
    time.sleep(30)
    driver.quit()
