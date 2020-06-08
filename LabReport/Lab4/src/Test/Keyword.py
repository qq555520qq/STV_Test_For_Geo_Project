from selenium import webdriver
from  selenium.webdriver.common.by  import  By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
import time

def get_web_element( _driver,locater ):
    try:
        element = WebDriverWait(_driver, 5).until(
            EC.visibility_of_element_located((By.XPATH, locater))
        )
        return element
    except Exception:      
      _driver.close()

def login( _driver ):
    get_web_element( _driver, '//a[contains(@href,"keystone/sign") and contains(@class,"btn")]' ).click() 
    get_web_element( _driver, '//*[normalize-space() = "Email"]/input' ).send_keys( 'demo@keystonejs.com' )
    get_web_element( _driver, '//*[normalize-space() = "Password"]/input' ).send_keys("demo")
    get_web_element( _driver, '//*[normalize-space() = "Sign In"]' ).click()
    get_web_element( _driver, '//*[contains( @class , "dashboard-group__heading-icon octicon octicon-book") ]' ) # ensure login success

def create_post( _driver, text ):
    get_web_element( _driver, '//button[contains(normalize-space(), "Create")]' ).click() 
    get_web_element( _driver, '//*[contains(@class,"FormField__inner field-size-full")]//input' ).send_keys(text)
    get_web_element( _driver, '//*[normalize-space() = "Create"]' ).click()
    get_web_element( _driver, '//*[contains( @data-button , "update") ]' ).click()

def open_dropdown_list( _driver, field ):
    get_web_element( _driver, '//*[contains( @for, "%s") ]//*[contains( @class,"Select-arrow-zone") ]' %field ).click()

def top_tab_go_to_page( _driver, field ):
    get_web_element( _driver, '//*[contains( @href , "%s") and contains( @class,"css-dmf4a8") ]' %field ).click()
    get_web_element( _driver, '//*[contains( @data-list-path , "%s") and contains( @class,"active") ]' %field )
    

def get_select_dropdown_text( _driver, field ):
    get_web_element( _driver, '//*[contains( @for,"%s") ]//*[contains(@class, "Select-value-label")]' %field )
    text = get_web_element( _driver, '//*[contains( @for,"%s") ]//*[contains(@class, "Select-value-label")]' %field ).text
    return text

def sub_tab_go_to_page( _driver, field ):
    get_web_element( _driver, '//*[contains( @href , "%s") and contains( @class,"dashboard-group__list-tile") ]' %field ).click()

def edit_content(_driver, field, text ):
    _driver.switch_to.default_content()
    if ( field == 'content.brief' ):
        _driver.execute_script("window.scrollTo(0, 1000)") 
    get_web_element( _driver, '//*[contains( @class, "css-1wrt3l9 field-type-html") and contains( @for , "%s") ]//*[contains(@aria-label , "Source code")]' %field ).click()
    get_web_element( _driver, '//textarea[contains(@class, "mce-textbox mce-multiline mce-abs-layout-item mce-first mce-last")]' ) # wait
    get_web_element( _driver, '//textarea[contains(@class, "mce-textbox mce-multiline mce-abs-layout-item mce-first mce-last")]' ).send_keys( Keys.CONTROL+'a' )
    get_web_element( _driver, '//textarea[contains(@class, "mce-textbox mce-multiline mce-abs-layout-item mce-first mce-last")]' ).send_keys( Keys.BACKSPACE )
    get_web_element( _driver, '//textarea[contains(@class, "mce-textbox mce-multiline mce-abs-layout-item mce-first mce-last")]' ).send_keys( text )
    get_web_element( _driver, '//*[contains( @class , "mce-txt") ]' ).click()

def select_dropdown_field(_driver, field, text):
    open_dropdown_list( _driver ,field )  # change to field
    get_web_element( _driver, '//*[contains(@class, "Select-option") and contains(normalize-space(), "%s")]' %text ).click()
    get_web_element( _driver, '//*[contains( @data-button , "update") ]' ).click()
    stateText = get_select_dropdown_text( _driver, field )
    return stateText

def is_text_present( _driver, text ):
    time.sleep(1)
    return str(text) in _driver.page_source

def go_to_login_page(_driver):
    get_web_element( _driver, '//*[contains( @title, "Front page - Demo") ]' ).click()

def go_to_login_page_subtab(_driver, subtab ):
    time.sleep(1)
    get_web_element( _driver, '//*[contains( @href, "%s") ]' %subtab ).click()
    time.sleep(1)

def input_field( _driver, field, text ):
    get_web_element( _driver, '//input[contains( @name , "%s") ]' %field ).send_keys( Keys.CONTROL+'a' )
    get_web_element( _driver, '//input[contains( @name , "%s") ]' %field).send_keys( Keys.BACKSPACE )
    get_web_element( _driver, '//input[contains( @name , "%s") ]' %field ).send_keys( text )