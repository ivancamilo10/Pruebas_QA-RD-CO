import { LoginPage } from "../pages/loginPage";
import { ProductPage } from "../pages/productPage";


describe("Login Test Suite", () => {
    const loginPage = new LoginPage();
    const productPage = new ProductPage();

    beforeEach(() => {

    })

    it("Should log in successfully with valid user",()=>{
        cy.fixture("user").then((data)=>{
            const user = data.users.standard
            loginPage.login(user.username,user.password)
            productPage.getTitle().should("contain","Products")
        })
    })

    it("Should show error message for locked user",()=>{
        cy.fixture("user").then((data)=>{
            const user = data.users.locked
            loginPage.login(user.username,user.password)
            loginPage.getErrorMessage().should("be.visible").and("contain","locked out")
        })
    })
})