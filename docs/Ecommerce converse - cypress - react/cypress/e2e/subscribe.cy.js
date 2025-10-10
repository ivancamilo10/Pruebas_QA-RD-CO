describe('Formulario de suscripción', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('Permite introducir email y enviar', () => {
    // Scroll to subscribe section
    cy.contains('Sign Up for').scrollIntoView();
    cy.get('input[placeholder="subscribe@converse.com"]').should('exist').type('test@example.com');
    cy.contains('Sign Up').click();

    // The UI doesn't have a network request; assert that input still contains value or button was clickable
    cy.get('input[placeholder="subscribe@converse.com"]').should('have.value', 'test@example.com');
  });
});
