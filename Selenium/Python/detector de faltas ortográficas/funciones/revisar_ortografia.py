import re
from spellchecker import SpellChecker

spell = SpellChecker(language='es')

def revisar_ortografia(parrafo):
    """
    Recibe un párrafo de texto y devuelve:
    - 1 si hay errores ortográficos, 0 si no
    - Lista de palabras mal escritas (ignora números con comas, puntos, etc.)
    """
    palabras = parrafo.split()
    palabras_limpias = []

    for p in palabras:
        # Quitar signos de puntuación al inicio/fin
        palabra = re.sub(r'^[^\wáéíóúüñ]+|[^\wáéíóúüñ]+$', '', p, flags=re.IGNORECASE)

        # Si es un número (con o sin comas/puntos), se ignora
        if re.fullmatch(r'[\d.,]+', palabra):
            continue

        if palabra:  # Evitar cadenas vacías
            palabras_limpias.append(palabra)

    errores = list(spell.unknown(palabras_limpias))
    resultado = 1 if errores else 0
    return resultado, errores


# 
