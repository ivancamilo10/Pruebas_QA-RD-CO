



from funciones.Variables_globales import etiquetas
from selenium.webdriver.common.by import By
from funciones.ElementData import ElementData






def extraer_elements(driver):
    elementos_info = []

    for tag in etiquetas:
        elementos = driver.find_elements(By.TAG_NAME, tag)
        for el in elementos:
            if el.text.strip():
                data = ElementData(
                    etiqueta=el.tag_name,
                    texto=el.text.strip(),
                    id_elemento=el.get_attribute("id"),
                    clase_elemento=el.get_attribute("class"),
                    ubicacion=el.location,
                    esta_activo=el.is_enabled(),
                    faltas_ortograficas=[]
                )
                
                elementos_info.append(data)

    return elementos_info
