from openai import OpenAI
import os
from dotenv import load_dotenv

load_dotenv()  # Carga las variables del .env

client = OpenAI(
    api_key=os.getenv("OPENAI_API_KEY")  # Ahora debería funcionar
)

def detectar_falta_ortografica(texto):
    # 0 significa que el texto no tiene errores ortográficos,
    # 1 significa que el texto contiene al menos un error.

    try:
        intentos_maximos = 0
        while intentos_maximos < 10:
            response = client.chat.completions.create(
                model="gpt-4o",
                messages=[
                    {"role": "system", "content": 
                        "You are a Spanish spelling assistant. Your only job is to check any text you receive and determine if it has spelling mistakes. "
                        "If the text has at least one spelling mistake, respond only with 1. "
                        "If the text has no mistakes, respond only with 0. "
                        "Do not write anything else, no explanations, no comments, no symbols, just 1 or 0."},
                    {"role": "user", "content": texto}
                ],
                max_tokens=2
            )

            respuesta = response.choices[0].message.content.strip()

            if respuesta in ["0", "1"]:
                return respuesta, True
            intentos_maximos += 1

        return 0, False

    except Exception as e:
        print(f"Error en la IA: {e}")
        return 0, False


resultado = detectar_falta_ortografica("Hola, ¿como estás?")
print(f"Resultado: {resultado}") 
