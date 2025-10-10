import os
import time
from PIL import Image

from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.common.exceptions import WebDriverException, TimeoutException
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait

from funciones.ElementData import ElementData
from funciones.revisar_ortografia import revisar_ortografia as detectar_falta_ortografica
from funciones.Variables_globales import etiquetas
from funciones.resaltador_palabras import resaltar_palabras
from funciones.capturar_pagina import capturar_pagina_completa
from funciones.utilidades import extraer_elements


from Generador_de_reporte import generate_report_from_elements








def initialize_driver(URL='https://www.google.com'):
    
    
    opciones = Options()
    opciones.add_argument("--headless=new")  # modo sin interfaz
    opciones.add_argument("--hide-scrollbars")
    
    driver = webdriver.Chrome( options=opciones)
    
    driver.get(URL)


    # Espera hasta que la página haya terminado de cargar (readyState == complete)
    WebDriverWait(driver, 20).until(
        lambda d: d.execute_script("return document.readyState") == "complete"
    )
    
    driver.maximize_window()
    return driver












def main():
    Url_pagina = 'https://tecnomarketrd.com/'
    
    try:
        cantidad_parrafos = 0
        cantidad_palabras = 0
        cantidad_faltas = 0
        
        elementos_faltas = []
        elementos_bien = []
        driver = initialize_driver(Url_pagina)
        time.sleep(1)
        elementos_info = extraer_elements(driver)
        
        cantidad_parrafos = len(elementos_info)
        # cantidad_palabras = sum(len(e.text.split()) for e in elementos_info)
        n=1
        for elemento in elementos_info:
     
            # print(detectar_falta_ortografica(elemento.text))
            detectadas = detectar_falta_ortografica(elemento.texto)

            if detectadas[0] == 1:
                resaltar_palabras(driver, detectadas[1])
                elementos_faltas.append(elemento)
                elemento.faltas_ortograficas = detectadas[1]
                elemento.id_elemento = f"p-{n}"
                n+=1

                print(elemento.id_elemento)
                print(elemento.faltas_ortograficas)

                cantidad_faltas += len(detectadas[1])
                elementos_faltas.append(elemento)

            else:
                elementos_bien.append(elemento)

            palabras_parrafo = len(elemento.texto.split())
            cantidad_palabras += palabras_parrafo 

        screenshot = capturar_pagina_completa(driver, "pagina_con_faltas.png")
        # print(f"Elementos con faltas ortográficas: {len(elementos_faltas)}")
        # print(f"Elementos sin faltas ortográficas: {len(elementos_bien)}")
        print(f"Cantidad de párrafos analizados: {cantidad_parrafos}")
        print(f"Cantidad de palabras analizadas: {cantidad_palabras}")
        print(f"Cantidad de faltas ortográficas: {cantidad_faltas}")

        print(elementos_faltas[0].faltas_ortograficas)
        
        reporte  = generate_report_from_elements(
            page_name=Url_pagina,
            elementos_faltas=elementos_faltas,
            paragraphs_count=cantidad_parrafos,
            words_count=cantidad_palabras,
            errors_count=cantidad_faltas,
            screenshot_path=screenshot,
            output_root="reports",
            top_n=10
        )
        
        print(f"Reporte generado: {reporte}")







    except (WebDriverException, TimeoutException) as e:
        print(f"❌ Ocurrió un error al abrir la página o con el driver: {e}")
    finally:
        try:
            driver.quit()
        except:
            pass




if __name__ == '__main__':
    main()