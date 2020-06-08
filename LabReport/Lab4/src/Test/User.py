from selenium import webdriver
from  selenium.webdriver.common.by  import  By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import unittest  # 需要引入 unittest、time、 等程式
from selenium.webdriver.common.keys import Keys
import random
import time
from .Keyword import *

class test_user(unittest.TestCase):  # 測試項目
    def setUp(self):
        self.url="http://127.0.0.1:3000/"  # 要執行自動測試的網站

    def test_create_user(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        login( test )
        sub_tab_go_to_page( test, 'users' )
        # precondition 
        get_web_element( test, '//button[contains(normalize-space(), "Create")]' ).click() 
        input_field( test, 'name.first', 'GeneChing' )
        input_field( test, 'name.last', 'Wu' )
        input_field( test, 'email', 'qq55555520qq@gmail.com' )
        input_field( test, 'password', 'geneisNumber1' )

        input_field( test, 'password', 'geneisNumber2' )
        input_field( test, 'password_confirm', 'geneisNumber2' )
        password = get_web_element( test, '//input[contains( @name , "password") ]' ).get_attribute('value')
        self.assertEqual( password, 'geneisNumber2' )

        input_field( test, 'password', '!@#$%^&*' )
        input_field( test, 'password_confirm', '!@#$%^&*' )
        password = get_web_element( test, '//input[contains( @name , "password") ]' ).get_attribute('value')
        self.assertEqual( password, '!@#$%^&*' )
        
        input_field( test, 'password', 'geneisNumber3' )
        input_field( test, 'password_confirm', 'geneisNumber3' )
        password = get_web_element( test, '//input[contains( @name , "password") ]' ).get_attribute('value')
        self.assertEqual( password, 'geneisNumber3' )
        
        get_web_element( test, '//button[contains(@type, "submit") and contains(normalize-space(), "Create")]' ).click() 
        
        input_field( test, 'phone', "0226352552" )
        input_field( test, 'phone', "09123456798" )
        get_web_element( test, '//*[contains( @data-button , "update") ]' ).click()
        top_tab_go_to_page( test, 'users' )

        exist = is_text_present( test, 'GeneChing Wu' )
        self.assertTrue( exist )
        exist = is_text_present( test, 'qq55555520qq@gmail.com' )
        self.assertTrue( exist )
        exist = is_text_present( test, '09123456798' )
        self.assertTrue( exist )
        time.sleep(2)
        test.close()


if __name__=="__main__":
    testsuite=unittest.TestSuite()
    testsuite.addTest(test_user("test_create_user"))
    runner = unittest.TextTestRunner(verbosity=2)
    runner.run(testsuite)