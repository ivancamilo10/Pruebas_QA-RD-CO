describe('Prueba de búsqueda en Converse Store', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('Debería mostrar resultados al buscar un producto existente', () => {
    cy.get('input[placeholder="Buscar"]').type('Chuck Taylor{enter}');
    cy.get('.product-list').should('contain', 'Chuck Taylor');
  });

  it('Debería mostrar mensaje cuando no hay resultados', () => {
    cy.get('input[placeholder="Buscar"]').type('ProductoInexistente{enter}');
    cy.get('.no-results').should('be.visible').and('contain', 'No se encontraron productos');
  });
});

