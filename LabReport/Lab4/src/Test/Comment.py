from selenium import webdriver
from  selenium.webdriver.common.by  import  By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import unittest  # 需要引入 unittest、time、 等程式
from selenium.webdriver.common.keys import Keys
import random
import time
from .Keyword import *

class test_comment(unittest.TestCase):  # 測試項目
    def setUp(self):
        self.url="http://127.0.0.1:3000/"  # 要執行自動測試的網站

    def test_create_comment(self):
        test=webdriver.Chrome()
        comment_precondiction(test,self.url)
        # precondition create post
        get_web_element( test, '//button[contains(normalize-space(), "Create")]' ).click() 
        select_comment_dropdown_field( test, 'author', 'Demo User')
        if ( is_text_present( test, 'Demo User' ) ):
          select_comment_dropdown_field( test, 'post', 'THIS IS GENE')
        else : select_comment_dropdown_field( test, 'author', 'Demo User')

        if ( is_text_present( test, 'THIS IS GENE' ) ):
          get_web_element( test, '//*[normalize-space() = "Create"]' ).click()
        else : select_comment_dropdown_field( test, 'post', 'THIS IS GENE')
        # create comment
        if ( is_text_present( test, 'Demo User' ) ):
            authorText = get_select_dropdown_text( test, 'author' )
            self.assertEqual( authorText, 'Demo User' )
            authorText = get_select_dropdown_text( test, 'post' )
            self.assertEqual( authorText, 'THIS IS GENE' )
            expectResult = get_web_element( test, '//*[contains(@class,"EditForm__name-field")]' ).text
            top_tab_go_to_page( test, 'comments' )
            actualResult = get_web_element( test, '//*[contains( @class , "ItemList__value ItemList__value--id") ]' ).text
            self.assertEqual( actualResult, expectResult )
            # assert comment info
        # assert comment ID
        time.sleep(2)
        test.close()
       
        

    def test_edit_comment(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        login( test )
        sub_tab_go_to_page( test, 'comments' )
        get_web_element( test, '//*[contains( @class , "ItemList__value ItemList__value--id") ]' ).click() 
        # precondition            
        stateText = select_dropdown_field( test , 'commentState', 'Published' )
        self.assertEqual( stateText, 'Published' ) 
        stateText = select_dropdown_field( test , 'commentState', 'Draft' )
        self.assertEqual( stateText, 'Draft' ) 
        stateText = select_dropdown_field( test , 'commentState', 'Archived' )
        self.assertEqual( stateText, 'Archived' )  

        edit_content(test,'content', 'EDIT TEST!!!')

        test.switch_to.frame(get_web_element( test, '//*[contains( @for,"content") ]//iframe'))
        ExpectText = get_web_element( test, '//p').text
        self.assertEqual( ExpectText, 'EDIT TEST!!!' )

        edit_content(test,'content', '12345')

        test.switch_to.frame(get_web_element( test, '//*[contains( @for,"content") ]//iframe'))
        ExpectText = get_web_element( test, '//p').text
        self.assertEqual( ExpectText, '12345' )

        edit_content(test,'content', '!@#$%')

        test.switch_to.frame(get_web_element( test, '//*[contains( @for,"content") ]//iframe'))
        ExpectText = get_web_element( test, '//p').text
        self.assertEqual( ExpectText, '!@#$%' )
        # edit
        time.sleep(2)
        test.close()
      
    def test_delete_comment(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        login(test)
        # precondition
        sub_tab_go_to_page( test, 'comments' )
        get_web_element( test, '//*[contains(@class,"octicon octicon-trashcan")]' ).click()
        get_web_element( test, '//*[normalize-space() = "Delete"]' ).click()
        exist = is_text_present( test, 'THIS IS GENE' )
        self.assertFalse( exist )
        time.sleep(2)
        test.close()

def comment_precondiction( _driver, url ):
    _driver.get( url )
    _driver.maximize_window()
    login( _driver )
    get_web_element( _driver, '//a[contains(@href,"posts") and contains(@class,"dashboard-group__list-tile")]' ).click() 
    create_post( _driver, 'THIS IS GENE' )
    get_web_element( _driver, '//*[normalize-space()= "Comments" and contains(@href,"comments")]' ).click()

def select_comment_dropdown_field(_driver, field, text):
    open_dropdown_list( _driver ,field )  # change to field
    get_web_element( _driver, '//*[contains(@class, "Select-option") and contains(normalize-space(), "%s")]' %text )
    get_web_element( _driver, '//*[contains(@class, "Select-option") and contains(normalize-space(), "%s")]' %text ).click()
    stateText = get_select_dropdown_text( _driver, field )
    return stateText

if __name__=="__main__":
    testsuite=unittest.TestSuite()
    testsuite.addTest(test_comment("test_create_comment"))
    testsuite.addTest(test_comment("test_edit_comment"))
    testsuite.addTest(test_comment("test_delete_comment"))
    runner = unittest.TextTestRunner(verbosity=2)
    runner.run(testsuite)
        