import { LoginPage } from "../pages/loginPage";
import { ProductPage } from "../pages/productPage";


describe('Product actions', () => {
  const loginPage = new LoginPage()
  const productsPage = new ProductPage()

  beforeEach(() => {
    cy.fixture('user').then((data) => {
      const user = data.users.standard
      loginPage.login(user.username, user.password)
    })
  })

  it('Should add product to cart and go to cart page', () => {
    productsPage.addProductToCart('Sauce Labs Backpack')
    productsPage.getCartIcon()
    cy.url().should('include', '/cart.html')
    cy.contains('Sauce Labs Backpack').should('be.visible')
  })
})
