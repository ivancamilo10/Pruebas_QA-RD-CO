describe('Navegación principal', () => {
  beforeEach(() => {
    // Force a desktop viewport so the full nav (not the hamburger) is visible
    cy.viewport(1280, 800);
    cy.visit('/');
  });

  it('Muestra el logo y enlaces del nav', () => {
    cy.get('header').within(() => {
      cy.get('img[alt="logo"]').should('be.visible');
      cy.get('ul').should('exist');
      // On desktop the 'Products' link should be visible
      cy.contains('Products').should('be.visible');
    });
  });
});
