import random

estudiantes = ["Ana", "Luis", "Carlos", "Marta", "Sofía", "Jorge", "Lucía",
               "Diego", "Elena", "Pablo", "Carmen", "Alberto", "Isabel", "Fernando", "Raquel"]


estudiante_seleccionado = random.randint(0, len(estudiantes)-1)

print(f"El estudiante ganador es :{estudiantes[estudiante_seleccionado]}")
