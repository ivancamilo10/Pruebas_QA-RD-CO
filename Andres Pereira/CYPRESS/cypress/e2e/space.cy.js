describe("Mi primera prueba", () => {

  it("Visita pagina principal", () => {

    cy.visit("https://example.cypress.io");

    cy.contains("Kitchen Sink");

    cy.contains("commands").click();

  });

});
