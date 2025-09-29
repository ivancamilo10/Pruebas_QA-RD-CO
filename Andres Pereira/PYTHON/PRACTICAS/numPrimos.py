def num_Primos(n: int)-> list[int]:
    #int trasfoma los datos a entero
    
    # range() = genera un rango de números
    # list() = convierte el rango en una lista
    numeros = list(range(2, n + 1))

    primos = []

    for num in numeros:
        #all()  devuelbe un true si todas las condiciones son verdaderas
        #any() seria lo contrario a all()
        if all(num % i !=0 for i in range(2, int(num**0.5)+1)):
            primos.append(num)
    
    return primos


print(num_Primos(30))