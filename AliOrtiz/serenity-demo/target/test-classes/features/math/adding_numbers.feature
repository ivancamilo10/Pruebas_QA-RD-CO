Feature: Login de usuario

  Scenario: Usuario válido puede iniciar sesión
    Given el usuario está en la página de login
    When ingresa usuario y contraseña válidos
    Then debería ver la página de inicio