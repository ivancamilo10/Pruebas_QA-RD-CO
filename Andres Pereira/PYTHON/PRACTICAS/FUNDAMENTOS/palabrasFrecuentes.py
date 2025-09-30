from collections import Counter #cuenta ocurrencia en una lista o string

def palabras_frecuentes(texto: str , top: int =3)->list[tuple[str,int]]:

    texto = texto.lower() #pasa texto a minusculas

    palabras = texto.split() #separa el texto en una lista de palabras

    contador = Counter(palabras) #cuenta las ocurrencias de cada palabra

    return contador.most_common(top)# devuelbe las top palabras mas repetidas

print(palabras_frecuentes("Python es genial y Python es poderoso y Python es divertido"))