describe('Servicios', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('Muestra tarjetas de servicios', () => {
    cy.get('section').contains('Free shipping').should('exist');
    cy.get('section').contains('Secure Payment').should('exist');
    cy.get('section').contains('Love to help you').should('exist');
  });
});
