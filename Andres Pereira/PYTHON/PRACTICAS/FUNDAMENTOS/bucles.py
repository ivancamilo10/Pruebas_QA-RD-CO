print("\n")
# Recurriendo una lista
frutas = ["manzana","pera","mango"]

for i in frutas:  # For in 
    print(i)

print("")
# bucle Range()
for i in range(1,6):  #recorre de un limite a otro
    print(i)

print("")

# Recorriendo un diccionario
usuarios ={"nombre": "Carlos", "edad": 22} 
for clave, valor in usuarios.items(): #Coje la propiedad como clave y el valor 
    print(clave,"=",valor)

print("")

# Bucle While
contador = 0

while contador <5:
    print("Contador: ",contador)
    contador+=1


# Ejemplo 

for i in range(10):
    if(i==3):
        continue
    if(i==7):
        break
    print(i)
else:
    print("Bucle terminado")