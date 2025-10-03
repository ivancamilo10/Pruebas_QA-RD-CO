describe("Prueba inicial de typescript", () => {
  // **UNIFICADO:** Un solo bloque para la acción (Login) y la validación
  it("Login con usuario y password encontrados Y validación de éxito", function () {
    cy.visit("https://www.saucedemo.com/v1/");

    // Obtener usuario
    cy.get("#login_credentials").then(($el) => {
      const usuarios = $el
        .text()
        .split("\n")
        .map((u) => u.trim())
        .filter(Boolean);
      const user = usuarios[1];

      // Obtener password
      cy.get(".login_password").then(($el2) => {
        const passwords = $el2
          .text()
          .split("\n")
          .map((p) => p.trim())
          .filter(Boolean);
        const pass = passwords[1];

        // Hacer login
        cy.get("#user-name").type(user);
        cy.get("#password").type(pass);
        cy.get("#login-button").click();

        //Valida que la URL contiene el segmento clave
        cy.url().should("include", "/inventory.html");

        //Valida que el título es correcto
        cy.get(".product_label").should("have.text", "Products");

        // Listar productos y precios
        cy.get(".inventory_list").then((i) => {
          const items = i.find(".inventory_item");
          items.each((index, item) => {
            const itemName = Cypress.$(item)
              .find(".inventory_item_name")
              .text();
            const itemPrice = Cypress.$(item)
              .find(".inventory_item_price")
              .text();
            cy.log(`Item ${index + 1}: ${itemName} - Price: ${itemPrice}`);
          });

          // Hacer clic en los botones de los productos en posiciones pares
          for (let i = 0; i < items.length; i++) {
            if(i %2 === 0) {
              cy.wrap(items[i]).find('button').click();
            }
          }
          cy.get(".shopping_cart_container").click();

          cy.get(".btn_action").click();

          //Completar compras
          cy.get("#first-name").type("Juan");
          cy.get("#last-name").type("Perez");
          cy.get("#postal-code").type("12345");
          cy.get(".cart_button").click();
          cy.get(".cart_button").click();

          //Validar compra exitosa
          cy.get(".complete-header").should("have.text", "THANK YOU FOR YOUR ORDER");
        });
      });
    });
  });
});
