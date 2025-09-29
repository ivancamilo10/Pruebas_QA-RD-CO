const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
    baseUrl: "https://github.com",
    viewportWidth: 1000,
    viewportHeight: 500,
    experimentalWebKitSupport: true,
  },
});
