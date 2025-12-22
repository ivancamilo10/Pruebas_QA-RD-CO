describe('pruebas de cypress', () => {
    it("visitar la pagina y marcar una tarea como terminada", () => {
        cy.visit("https://example.cypress.io/todo")
        cy.get('[data-test="new-todo"]').type("hola desde Cypress")
    })
})

