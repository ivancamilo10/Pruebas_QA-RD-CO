// index.d.ts
/// <reference types="cypress" />

declare namespace Cypress {
  interface Chainable {
    /**
     * Custom command to log in with username and password
     * @example cy.login('standard_user', 'secret_sauce')
     */
    login(username: string, password: string): Chainable<void>
  }
}
