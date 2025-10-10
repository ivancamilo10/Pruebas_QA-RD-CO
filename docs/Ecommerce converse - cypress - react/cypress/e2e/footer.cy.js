describe('Footer y enlaces', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('Muestra secciones del footer y enlaces de productos', () => {
    cy.get('footer').within(() => {
      cy.contains('Products').should('exist');
      cy.contains('Help').should('exist');
      // One of the product names from constants
      cy.contains('Chuck Taylor All Star').should('exist');
    });
  });
});
