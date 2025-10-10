from selenium import webdriver

from selenium.webdriver.chrome.service import Service

from selenium.webdriver.chrome.options import Options

from webdriver_manager.chrome import ChromeDriverManager
import time
import os

from funciones.Variables_globales import carpeta



def capturar_pagina_completa(driver, nombre_archivo="captura_pagina.png"):
    os.makedirs(carpeta, exist_ok=True)

    ruta_archivo = os.path.join(carpeta, nombre_archivo)

    time.sleep(2)

    ancho_total = driver.execute_script("return document.body.scrollWidth")
    alto_total = driver.execute_script("return document.body.scrollHeight")

    driver.set_window_size(ancho_total, alto_total)
    driver.save_screenshot(ruta_archivo)

    return ruta_archivo




