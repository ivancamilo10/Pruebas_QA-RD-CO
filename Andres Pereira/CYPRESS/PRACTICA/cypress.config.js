import { defineConfig } from 'cypress'

export default defineConfig({
  e2e: {
    baseUrl: 'https://www.saucedemo.com',
    supportFile: 'cypress/support/e2e.ts',   // ✅ en lugar de index.ts
    specPattern: 'cypress/e2e/**/*.cy.ts'
  },
})
