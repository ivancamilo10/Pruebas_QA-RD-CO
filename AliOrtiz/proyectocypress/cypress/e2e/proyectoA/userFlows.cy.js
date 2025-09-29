describe('Flujos típicos de usuario', () => {

  // Prueba 1: Registro de usuario
  it('Registro de nuevo usuario', () => {
    cy.visit('https://example.cypress.io') // Cambia por tu URL real de registro
    cy.contains('Sign Up').click() // Ajusta según el texto de tu botón

    cy.get('#username').type('usuarioPrueba')
    cy.get('#email').type('usuario@test.com')
    cy.get('#password').type('Clave123!')
    cy.get('button[type="submit"]').click()

    // Verifica que se registró correctamente
    cy.contains('Bienvenido, usuarioPrueba').should('exist')
  })

  // Prueba 2: Login
  it('Login con usuario registrado', () => {
    cy.visit('https://example.cypress.io') // Cambia por tu URL de login
    cy.contains('Login').click()

    cy.get('#email').type('usuario@test.com')
    cy.get('#password').type('Clave123!')
    cy.get('button[type="submit"]').click()

    // Verifica que el login fue exitoso
    cy.contains('Dashboard').should('exist')
  })

  // Prueba 3: Logout
  it('Logout de usuario', () => {
    cy.visit('https://example.cypress.io/dashboard') // Página después de login
    cy.contains('Logout').click()

    // Verifica que el usuario fue deslogueado
    cy.url().should('include', '/login')
  })

})

