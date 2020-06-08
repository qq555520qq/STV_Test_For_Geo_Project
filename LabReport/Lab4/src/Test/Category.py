from selenium import webdriver
from selenium.webdriver.common.by  import  By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import unittest  # 需要引入 unittest、time、 等程式
from selenium.webdriver.common.keys import Keys
import random
import time
from .Keyword import *

class test_category(unittest.TestCase):  # 測試項目
    def setUp(self):
        self.url="http://127.0.0.1:3000/"  # 要執行自動測試的網站
        self.test=webdriver.Chrome()
        self.test.get( self.url )
        self.test.maximize_window()
        # precondition
        login( self.test )

    def test_create_category(self):
        sub_tab_go_to_page( self.test, 'categories' )
        # precondition 
        create_post( self.test, 'Test' )
        top_tab_go_to_page( self.test, 'categories' )
        create_post( self.test, '12345' )
        top_tab_go_to_page( self.test, 'categories' )
        create_post( self.test, '!@#$%' )
        top_tab_go_to_page( self.test, 'categories' )
        create_post( self.test, 'Test1' )
        top_tab_go_to_page( self.test, 'categories' )

        exist = is_text_present( self.test, 'Test' )
        self.assertTrue( exist )
        exist = is_text_present( self.test, 'Test1' )
        self.assertTrue( exist )
        exist = is_text_present( self.test, '12345' )
        self.assertTrue( exist )
        exist = is_text_present( self.test, '!@#$%' )
        self.assertTrue( exist )
        
        time.sleep(2)
        self.test.close()
    
    def test_show_post(self):
        get_web_element( self.test, '//a[contains(@href,"posts") and contains(@class,"dashboard-group__list-tile")]' ).click() 
        # go to create page
        create_post( self.test, 'THIS IS GENE' )
        stateText = select_dropdown_field( self.test , 'state',  'Draft' )
        stateText = select_dropdown_field( self.test , 'state',  'Published' )
        self.assertEqual( stateText, 'Published' ) 
        get_web_element( self.test, '//h2[contains(normalize-space(), "Relationships")]').location_once_scrolled_into_view
        select_dropdown_field( self.test , 'categories', 'Test' )
        go_to_login_page ( self.test )
        go_to_login_page_subtab( self.test, 'blog' )
        get_web_element( self.test, '//*[contains( @href , "blog") and contains( normalize-space() , "Test")]' ).click()
        exist = is_text_present( self.test, 'THIS IS GENE' ) # web bug it should present
        self.assertTrue( exist )

        time.sleep(2)
        self.test.close()

if __name__=="__main__":
    testsuite=unittest.TestSuite()
    testsuite.addTest(test_category("test_create_category"))
    testsuite.addTest(test_category("test_show_post"))
    runner = unittest.TextTestRunner(verbosity=2)
    runner.run(testsuite)