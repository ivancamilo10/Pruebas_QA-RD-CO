
from funciones.Variables_globales import etiquetas as selectors


def resaltar_palabras(driver, words):
    """
    Resalta en rojo, negrita, subrayado y fondo amarillo
    todas las palabras del array dentro de los elementos HTML más comunes.
    """


    driver.execute_script("""
        let words = arguments[0];
        let selectors = arguments[1];

        selectors.forEach(sel => {
            document.querySelectorAll(sel).forEach(el => {
                let html = el.innerHTML;
                words.forEach(word => {
                    let regex = new RegExp(word, "gi");
                    html = html.replace(regex, '<span style="color:#ff0000; text-decoration:underline; background-color:yellow;">$&</span>');
                });
                el.innerHTML = html;
            });
        });
    """, words, selectors)
