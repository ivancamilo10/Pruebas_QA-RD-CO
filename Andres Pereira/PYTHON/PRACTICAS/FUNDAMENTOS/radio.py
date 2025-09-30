# Crear un programa que calcule el área de un círculo.
import math

radio =  float(input("Ingresa el area del circulo: "))

area = math.pi * (radio ** 2)

print(f"El radio del circulo es {area} y el area es: {area:.2f}")