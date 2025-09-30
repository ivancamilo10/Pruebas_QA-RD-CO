# 🧪 Proyecto de QA con Cypress - SauceDemo

Este documento corresponde a la carpeta **`docs/`** de mi rama en el repositorio compartido.  
Aquí muestro el paso a paso del **mini proyecto con Cypress** utilizando la aplicación [SauceDemo](https://www.saucedemo.com/).

El objetivo es **documentar el flujo completo de pruebas automatizadas E2E (end-to-end)**:  
✨ Inicio de sesión  
✨ Agregar productos al carrito  
✨ Finalizar el proceso de compra  

---

## 🚀 Tecnologías utilizadas
- 🟢 **Node.js** (v16+)  
- 🧪 **Cypress** (v13)  
- 🛒 **SauceDemo** (sitio demo para pruebas de e-commerce)  

---

## 🔧 Instalación y configuración

1️⃣ Clonar el repositorio y moverse a la rama propia:  
```bash
git https://github.com/ivancamilo10/Pruebas_QA-RD-CO.git
cd Pruebas_QA-RD-CO.git
git checkout Leonardo-Rodriguez
cd docs


2️⃣ Instalar dependencias:

npm install

3️⃣ Abrir Cypress en modo gráfico:

npx cypress open


4️⃣ O ejecutar en modo headless (sin interfaz):

npx cypress run


Estructura del proyecto
PRUEBAS_QA-RD-CO/
│
├── docs/                      # 📘 Documentación y recursos
│   └── README.md              # Documentación de tu mini proyecto
│
├── cypress.config.js          # ⚙️ Configuración principal de Cypress
├── cypress.zip                # 📦 Carpeta Cypress comprimida
│   └── cypress/               # (dentro del zip)
│       ├── e2e/               # 🧪 Casos de prueba
│       │   ├── login.cy.js
│       │   ├── carrito.cy.js
│       │   └── checkout.cy.js
│       ├── fixtures/          # 📂 Datos de prueba en JSON
│       └── support/           # 🔧 Comandos y configuración extra
│
├── documento prueba.xlsx       # 📊 Documento de pruebas manuales
├── package.json                # 📦 Dependencias del proyecto
├── package-lock.json           # 🔒 Versiones exactas instaladas
└── README.md                   # 📘 Documentación general del repo



📝 Escenarios de prueba
🔑 Login

✅ Inicio de sesión correcto con usuario válido

❌ Error de login con credenciales inválidas

🛒 Carrito

➕ Agregar productos al carrito

💳 Checkout

📝 Completar flujo de compra con datos válidos

🏁 Validar confirmación de la orden

▶️ Ejecución de las pruebas

⚡ En modo gráfico (ideal para desarrollo):

npx cypress open


⚡ En terminal (para CI/CD):

npx cypress run

📊 Resultados esperados

✅ Login exitoso con usuario standard_user y contraseña secret_sauce

✅ Carrito con los productos seleccionados

🎉 Checkout finalizado con el mensaje:

Thank you for your order!

🎯 Conclusión

Este mini proyecto documenta el uso de Cypress para pruebas E2E en un e-commerce.
Sirve como parte de portafolio para mostrar mis habilidades en QA Automation 🤖 y el trabajo en un repositorio colaborativo 🌐.