export class ProductPage {
  getTitle() {
    return cy.get(".title");
  }

  addProductToCart(productName: string) {
    cy.contains(".inventory_item", productName).find("button").click();
  }

  getCartIcon() {
    cy.get(".shopping_cart_link").click();
  }
}
