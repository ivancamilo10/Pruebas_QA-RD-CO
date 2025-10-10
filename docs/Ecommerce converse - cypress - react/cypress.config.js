import { defineConfig } from "cypress";

export default defineConfig({
  e2e: {
    // Base URL where Vite serves the app (default Vite port 5173).
    // You can change this if you run the dev server on a different port.
    baseUrl: 'http://localhost:5173',

    // Only run tests placed under cypress/e2e with .cy.js extension
    specPattern: 'cypress/e2e/**/*.cy.js',

    // Use the ES module support file already present
    supportFile: 'cypress/support/e2e.js',

    setupNodeEvents(on, config) {
      // Example: add a simple `log` task that tests can call to print to terminal
      on('task', {
        log(message) {
          console.log(message);
          return null;
        },
      });

      // Return the config object as required
      return config;
    },
  },
});
