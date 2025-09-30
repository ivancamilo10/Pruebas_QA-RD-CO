usuaros = [
    {"nombre":"carlos", "edad":25},
    {"nombre":"andres", "edad":15},
    {"nombre":"ana", "edad":30},
]

usuarios_ordenados = sorted(usuaros, key=lambda u: u["edad"])

print(usuarios_ordenados)