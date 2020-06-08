from selenium import webdriver
from  selenium.webdriver.common.by  import  By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import unittest  # 需要引入 unittest、time、 等程式
from selenium.webdriver.common.keys import Keys
import random
import time
from .Keyword import *

class test_enquire(unittest.TestCase):  # 測試項目
    def setUp(self):
        self.url="http://127.0.0.1:3000/"  # 要執行自動測試的網站

    def test_create_enquiry(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        go_to_login_page_subtab(test, 'contact' )
        # precondition
        input_field( test, 'name', "GENE" )
        input_field( test, 'email', "qq55555520qq@gmail.com" )
        input_field( test, 'phone', "0426352452" )
        input_field( test, 'phone', "09123456789" )
        select_contact_dropdown_field( test, 'Just leaving a message' )
        exist = is_text_present( test, 'Just leaving a message' )
        self.assertTrue( exist )
        select_contact_dropdown_field( test, "I've got a question" )
        exist = is_text_present( test, "I've got a question" )
        self.assertTrue( exist )
        select_contact_dropdown_field( test, 'Something else...' )
        exist = is_text_present( test, 'Something else...' )
        self.assertTrue( exist )
        input_text_area( test, 'message', '!@#$%' )
        input_text_area( test, 'message', '12345' )
        input_text_area( test, 'message', 'Test1' )
        input_text_area( test, 'message', 'EDIT TEST' )
        submit( test )
        
        success = get_web_element( test, '//*[normalize-space() = "Success!"]' ).text
        self.assertEqual( success, 'Success!' )
        time.sleep(2)
        test.close()
    
    def test_delete_enquiry(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        login( test )
        sub_tab_go_to_page( test, 'enquiries' )
        # precondition
        get_web_element( test, '//*[contains(@class, "ItemList__value") and normalize-space() = "GENE"]/../..//*[contains(@class,"octicon octicon-trashcan")]' ).click()
        get_web_element( test, '//*[normalize-space() = "Delete"]' ).click()
        exist = is_text_present( test, "GENE" )
        self.assertFalse( exist )
        time.sleep(2)
        test.close()
        
def submit( _driver ):
    get_web_element( _driver, '//button[contains(normalize-space(), "Submit")]' ).click()

def select_contact_dropdown_field( _driver, text ):
    get_web_element( _driver, '//*[contains( @name, "enquiryType") ]' ).click()
    get_web_element( _driver, '//option[contains(normalize-space(), "%s")]' %text ).click()

def input_text_area( _driver, field, text ):
    get_web_element( _driver, '//textarea[contains( @name , "%s") ]' %field ).send_keys( Keys.CONTROL+'a' )
    get_web_element( _driver, '//textarea[contains( @name , "%s") ]' %field).send_keys( Keys.BACKSPACE )
    get_web_element( _driver, '//textarea[contains( @name , "%s") ]' %field ).send_keys( text )

if __name__=="__main__":
    testsuite=unittest.TestSuite()
    testsuite.addTest(test_enquire("test_create_enquiry"))
    testsuite.addTest(test_enquire("test_delete_enquiry"))
    runner = unittest.TextTestRunner(verbosity=2)
    runner.run(testsuite)