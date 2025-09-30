def es_Polindromo( texto: str)->bool: 
    #srt combierte cualquier dato a string
    #bool determina si la funcion devuelbe verdadero o falso como true o false

    texto = texto.lower()#lower() combierte texto en minusculas 

    texto = texto.replace(" ","") # replace() remplaza los espacios vacios

    #reversed() devulbe un iterador con el texto al reves
    # "".join() une los caracteres en una lista de string 
    texto_invertodo = "".join(reversed(texto))

    return texto == texto_invertodo

print(es_Polindromo("Anita lava la tina"))
print(es_Polindromo("Python")) 