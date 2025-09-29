# Variables

nombre = "Juan"     # string
edad = 20           # int
altura = 1.75       # float
activo = True       # bool

# Tipo de datos

# str → cadenas de texto
# int → números enteros
# float → números decimales
# bool → True o False
# list → listas (arreglos modificables)
# tuple → tuplas (arreglos inmutables)
# dict → diccionarios (clave → valor)
# set → conjuntos (sin elementos repetidos)


# Operadores

# Aritméticos: + - * / % // **
# Comparación: == != > < >= <=
# Lógicos: and, or, not
# Asignación: = += -= *= /=


# Condicionales

edad = 18

if edad >= 18:
    print("Eres mayor de edad")
elif edad == 17:
    print("Ya casi llegas")
else:
    print("Eres menor de edad")

# Bucles

# For -> recorre una colecion o rango
for i in range(10):
    print(i)

# While -> ejecutar mientras se ejecuta una condicion
contador = 0

while contador < 5:
    print(contador)
    contador += 1

# Funciones


def saludo(salu: str):
    return f"Hola {salu}"


print(saludo("Andres"))


# Colecciones -> objetos y arreglos

numeros = [1, 2, 3, 4, 5] #arreglo 

cordenadas = (10, 20) #tupla

personas = {"nombre":"andres","edad":19} #objeto con propiedad

colores = {"azul","rojo","verde"} #objeto   


# Manejo de errores

try:
    print(10/0)
except ZeroDivisionError:
    print("No se puede dividir con cero")

# Modulos y librerias

import math

print(math.sqrt(16))

# Programacion orientada a objetos (POO)

class Personas:
    def __init__(self,nombre,edad):
         self.nombre = nombre
         self.edad = edad

    def saludar (self):
        print(f"Hola, soy {nombre} y mi edad es {edad}")

p = Personas("Carlos",15)

p.saludar()

