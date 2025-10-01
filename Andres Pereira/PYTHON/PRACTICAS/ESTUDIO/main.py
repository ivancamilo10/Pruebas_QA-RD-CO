# nombre = "Andres David Pereira Puello"

# # Métodos y funciones de cadenas
# print(len(nombre))  # Devuelve la longitud de la cadena
# print(nombre.upper())  # Convierte la cadena a mayúsculas
# print(nombre.lower())  # Convierte la cadena a minúsculas
# print(nombre.replace("Andres", "Yaseth"))  # Reemplaza caracteres en la cadena
# print(nombre.split("P"))  # Divide la cadena en una lista usando un delimitador
# print("Andres" in nombre)  # Verifica si una subcadena está en la cadena
# print(nombre[0:3])  # Rebanado de cadena (slicing)
# print(nombre.index("d"))  # Encuentra la posición de un carácter
# print(nombre.count("e"))  # Cuenta las ocurrencias de un carácter
# print(nombre.startswith("An"))  # Verifica si la cadena comienza con un prefijo
# print(nombre.endswith("es"))  # Verifica si la cadena termina con un sufijo
# print(nombre.isalpha())  # Verifica si todos los caracteres son alfabéticos
# print(nombre.isdigit())  # Verifica si todos los caracteres son dígitos

# print(nombre.find("d"))  # Encuentra la primera aparición de un carácter
# print(nombre.rfind("e"))  # Encuentra la última aparición de un carácter

# # Encuentra una subcadena ignorando mayúsculas/minúsculas
# print(nombre.lower().find("i"))


# # Recibimos datos del usuario
# nombre = input("Ingrese su nombre completo: ")
# edad = int(input("Ingrese su edad: "))
# altura = float(input("Ingrese su altura en metros: "))
# print("Su nombre es:", nombre)


#Funciones matematicas   
import math , random as r
print(math.sqrt(16))  # Raíz cuadrada

ramdon = r.random(0,10)
py = 3.14

print(round(py))  # Redondea al entero más cercano
print(math.ceil(py))  # Redondea hacia arriba
print(math.floor(py))  # Redondea hacia abajo
print(math.abs(-7.5))  # Valor absoluto negativo a positivo
print(ramdon)  # Número aleatorio entre 0 y 1