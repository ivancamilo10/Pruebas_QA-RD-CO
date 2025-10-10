from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time


def initialize_driver():
    driver = webdriver.Chrome()
    return driver


def login(driver):
    input_username = driver.find_element(By.ID , "user-name")
    user_name_valuer = driver.find_element(By.ID , "login_credentials")
    split_user_name_valuer = user_name_valuer.text.split("\n")
    name = split_user_name_valuer[1]
    
    input_username.send_keys(name)
    
    
    input_password = driver.find_element(By.ID , "password")
    
    password = "secret_sauce"
    input_password.send_keys(password)
    


def main():
    driver = initialize_driver()
    driver.get("https://www.saucedemo.com/")
    
    login(driver)
    
    loggin_button = driver.find_element(By.ID , "login-button")
    loggin_button.click()
    
    if driver.current_url == "https://www.saucedemo.com/":
        print("exito")
    else:
        print("failed")


    
    time.sleep(10)



if __name__ == '__main__':
    main()