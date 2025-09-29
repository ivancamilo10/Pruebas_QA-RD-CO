from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time


def init_driver():
    driver = webdriver.Edge()
    return driver


def login(driver):
    # USUARIO
    input_username = driver.find_element(By.ID, "user-name")
    container_usernames = driver.find_element(
        By.XPATH, '//*[@id="login_credentials"]')
    username_container = container_usernames.text.split("\n")
    username = username_container[1]
    input_username.send_keys(username)

    # CONTRASEÑA
    input_password = driver.find_element(By.ID, "password")
    container_passwords = driver.find_element(
        By.XPATH, '/html/body/div[2]/div[2]/div/div[2]')

    password_container = container_passwords.text.split("\n")
    input_password.send_keys(password_container[1])

    login_button = driver.find_element(By.ID, "login-button")
    login_button.click()


def main():
    driver = init_driver()
    driver.get("https://www.saucedemo.com/v1/")
    login(driver)
    if driver.current_url == "https://www.saucedemo.com/v1/inventory.html":
        print("Login exitoso")
        menu(driver)
        cerrar_session(driver)
    else:
        print("Login fallido")

    time.sleep(5)
    # print(username)


def menu(driver):
    menu_button = driver.find_element(
        By.CLASS_NAME, "bm-burger-button")
    menu_button.click()
    time.sleep(2)
    # print(menu_button.text)
    return menu_button


def cerrar_session(driver):
    logout_link = driver.find_element(By.ID, "logout_sidebar_link")
    logout_link.click()
    print("Sesión cerrada")
    return logout_link


if __name__ == "__main__":
    main()