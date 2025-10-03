export class LoginPage {
  visit() {
    cy.visit("/v1/");
  }

  fillUsername(username: string) {
    cy.get("#user-name").type(username);
  }

  fillPassword(password: string) {
    cy.get("#password").type(password);
  }

  submit() {
    cy.get("#login-button").click();
  }

  login(username: string, password: string) {
    this.visit();
    this.fillUsername(username);
    this.fillPassword(password);
    this.submit();
  }

  getErrorMessage() {
    return cy.get("[data-test='error']");
  }
}
