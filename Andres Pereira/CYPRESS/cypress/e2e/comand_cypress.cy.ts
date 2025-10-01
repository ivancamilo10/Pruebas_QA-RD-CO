// ===============================
// 📌 Cypress Cheat Sheet (TypeScript)
// ===============================
// Autor: Andres 🚀
// Esta hoja NO es un test real, es solo referencia de comandos Cypress
// Úsala para copiar/pegar rápidamente en tus pruebas

describe('Cypress Cheat Sheet', () => {
  it('Referencia de comandos', () => {
    // ---------- Navegación ----------
    cy.visit('https://example.com')     // Visitar una página
    cy.go('back')                       // Ir atrás en el historial
    cy.go('forward')                    // Ir adelante
    cy.reload()                         // Recargar la página

    // ---------- Búsqueda de elementos ----------
    cy.get('#idElemento')               // Buscar por id
    cy.get('.clase')                    // Buscar por clase
    cy.get('input[name="email"]')       // Buscar por selector CSS
    cy.contains('Texto del botón')      // Buscar por texto
    cy.get('form').find('input')        // Buscar dentro de otro elemento

    // ---------- Interacciones ----------
    cy.get('input[name="email"]').type('correo@test.com') // Escribir
    cy.get('input[name="password"]').clear().type('1234') // Borrar y escribir
    cy.get('button[type="submit"]').click()               // Clic
    cy.get('input[type="checkbox"]').check()              // Marcar checkbox
    cy.get('input[type="checkbox"]').uncheck()            // Desmarcar
    cy.get('select').select('Opción 2')                   // Seleccionar dropdown
    cy.get('input').dblclick()                            // Doble clic
    cy.get('input').rightclick()                          // Clic derecho

    // ---------- Validaciones (Assertions) ----------
    cy.url().should('include', '/dashboard')              // Validar parte de URL
    cy.title().should('eq', 'Mi Página')                  // Validar título
    cy.get('h1').should('have.text', 'Bienvenido')        // Validar texto exacto
    cy.get('h1').should('contain.text', 'Bien')           // Validar que contenga
    cy.get('input').should('be.visible')                  // Validar que es visible
    cy.get('input').should('not.be.disabled')             // Validar que NO está deshabilitado
    cy.get('input').should('have.value', 'correo@test.com') // Validar valor de input

    // ---------- Esperas ----------
    cy.wait(2000)                                         // Esperar 2 segundos (usar poco)
    cy.get('#loader').should('not.exist')                 // Mejor: esperar que desaparezca
    cy.intercept('GET', '/api/users').as('getUsers')      // Interceptar request
    cy.wait('@getUsers')                                  // Esperar a que la API responda

    // ---------- Screenshots / Viewport ----------
    cy.screenshot('captura-pantalla')                     // Tomar screenshot
    cy.viewport(1280, 720)                                // Cambiar tamaño de pantalla
    cy.viewport('iphone-6')                               // Emular dispositivo

    // ---------- Archivos / Datos ----------
    cy.fixture('data.json').then((data) => {              // Cargar fixture
      cy.log(data.username)                               // Usar datos en pruebas
    })

    // ---------- Utilidades ----------
    cy.log('Mensaje en consola de Cypress')               // Log en el runner
    cy.scrollTo('bottom')                                 // Hacer scroll
  })
})
