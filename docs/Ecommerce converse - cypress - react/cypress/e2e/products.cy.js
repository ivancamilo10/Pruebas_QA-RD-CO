describe('Productos populares', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('La lista de productos se renderiza y contiene items', () => {
    cy.get('.product-list').should('exist');
    cy.get('.product-list').children().its('length').should('be.gte', 1);
  });

  it('Buscar un producto existente muestra resultados', () => {
    cy.get('input[placeholder="Buscar"]').type('Chuck Taylor{enter}');
    cy.get('.product-list').should('contain', 'Chuck Taylor');
  });

  it('Buscar un producto inexistente muestra mensaje de no resultados', () => {
    cy.get('input[placeholder="Buscar"]').clear().type('NoExisteProducto{enter}');
    cy.get('.no-results').should('be.visible').and('contain', 'No se encontraron productos');
  });
});
