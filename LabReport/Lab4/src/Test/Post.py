from selenium import webdriver
from  selenium.webdriver.common.by  import  By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import unittest  # 需要引入 unittest、time、 等程式
from selenium.webdriver.common.keys import Keys
import random
import time

from .Keyword import *


class test_post(unittest.TestCase):  # 測試項目
    def setUp(self):
        self.url="http://127.0.0.1:3000/"  # 要執行自動測試的網站

    def test_create_post(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        # precondition
        login( test )
        get_web_element( test, '//a[contains(@href,"posts") and contains(@class,"dashboard-group__list-tile")]' ).click() 
        # go to create page
        create_post( test, 'THIS IS GENE' )
        top_tab_go_to_page( test, 'posts' )
        create_post( test, 'TEST1' )
        top_tab_go_to_page( test, 'posts' )
        create_post( test, '!@#$%' )
        top_tab_go_to_page( test, 'posts' )
        create_post( test,' 34567' )
        top_tab_go_to_page( test, 'posts' )

        exist = is_text_present( test, 'THIS IS GENE' )
        self.assertTrue( exist )
        exist = is_text_present( test, 'TEST1' )
        self.assertTrue( exist )
        exist = is_text_present( test, '34567' )
        self.assertTrue( exist )
        exist = is_text_present( test, '!@#$%' )
        self.assertTrue( exist )
    
        time.sleep(2)
        test.close()
        

    def test_edit_post(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        login( test )
        # precondition

        get_web_element( test, '//*[contains( @class , "dashboard-group__list-tile") and contains( @href,"posts") ]' ).click()
        get_web_element( test, '//*[contains( @class , "ItemList__value ItemList__value--text") ]' )
        webelements = test.find_elements( By.XPATH, '//*[contains( @class , "ItemList__value ItemList__value--text") ]' )
        webelements[2].click()
        # go to post page

        stateText = select_dropdown_field( test , 'state',  'Published' )
        self.assertEqual( stateText, 'Published' ) 
        stateText = select_dropdown_field( test , 'state', 'Draft' )
        self.assertEqual( stateText, 'Draft' ) 
        stateText = select_dropdown_field( test , 'state', 'Archived' )
        self.assertEqual( stateText, 'Archived' )        
        # select state dropdown 

        open_dropdown_list( test ,'author')
        get_web_element( test, '//*[contains( @class , "Select-option is-focused") ]' ).click()
        authorText = get_select_dropdown_text(test,'author')
        self.assertEqual( authorText, 'Demo User' ) 
        # select author dropdown
        
        clear_text_field (test, 'publishedDate' )
        day, ExpectText = input_published_date_field(test)
        publishedDateText = get_web_element( test, '//*[contains( @for,"publishedDate") ]//*[contains(@type, "text")]').get_attribute('value')
        get_web_element( test, '//*[contains( @aria-label,"%s") ]' %day ).click()
        self.assertEqual( publishedDateText, ExpectText )
        # select publishedDate
        
        edit_content(test, 'content.brief', 'EDIT TEST!!!')
        test.switch_to.frame(get_web_element( test, '//*[contains( @for,"content.brief") ]//iframe'))
        ExpectText = get_web_element( test, '//p').text
        self.assertEqual( ExpectText, 'EDIT TEST!!!' )
        # edit content.brief

        edit_content(test, 'content.extended', 'EDIT TEST!!!' )
        test.switch_to.frame(get_web_element( test, '//*[contains( @for,"content.extended") ]//iframe'))
        ExpectText = get_web_element( test, '//p').text
        self.assertEqual( ExpectText, 'EDIT TEST!!!' )
        # edit content.extended
        test.switch_to.default_content()
        get_web_element( test, '//*[contains( @data-button , "update") ]' ).click()
        time.sleep(2)
        test.close()

    def test_search_post(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        login( test )
        # precondition

        get_web_element( test, '//a[contains(@href,"posts") and contains(@class,"dashboard-group__list-tile")]' ).click() 
        
        ExpectText = search_post(test,'!')
        self.assertEqual( ExpectText, '!@#$%' )
        ExpectText = search_post(test,'!@#')
        self.assertEqual( ExpectText, '!@#$%' )
        ExpectText = search_post(test,'!@#$%')
        self.assertEqual( ExpectText, '!@#$%' )
        
        ExpectText = search_post( test,'THIS' )
        self.assertEqual( ExpectText, 'THIS IS GENE' )
        ExpectText = search_post( test,'THIS IS')
        self.assertEqual( ExpectText, 'THIS IS GENE' )
        ExpectText = search_post( test,'THIS IS GENE' )
        self.assertEqual( ExpectText, 'THIS IS GENE' )

        ExpectText = search_post(test,'3')
        self.assertEqual( ExpectText, '34567' )
        ExpectText = search_post(test,'345' )
        self.assertEqual( ExpectText, '34567' )
        ExpectText = search_post(test,'34567' )
        self.assertEqual( ExpectText, '34567' )

        ExpectText = search_post(test,'TEST1' )
        self.assertEqual( ExpectText, 'TEST1' )
        time.sleep(2)
        test.close()

    def test_delete_post(self):
        test=webdriver.Chrome()
        test.get( self.url )
        test.maximize_window()
        login( test )
        # precondition
        get_web_element( test, '//a[contains(@href,"posts") and contains(@class,"dashboard-group__list-tile")]' ).click() 

        items = collect_post(test)
        finalitem = items[len(items)-1] # escape null assert 
        for item in items:
            get_web_element( test, '//*[contains(@class, "ItemList__value ItemList__value--text") and normalize-space() = "%s"]/../..//*[contains(@class,"octicon octicon-trashcan")]' %item ).click()
            get_web_element( test, '//*[normalize-space() = "Delete"]' ).click()
            if item != finalitem:
                actualResult = collect_post(test)
                self.assertNotIn( item , actualResult )
        time.sleep(2)
        test.close()


def collect_post(_driver):
    get_web_element(_driver,'//*[contains(@class, "ItemList__value ItemList__value--text")]')
    webelements = _driver.find_elements(By.XPATH,'//*[contains(@class, "ItemList__value ItemList__value--text")]')
    result = []
    for element in webelements:
        result.append(element.text)
    return result


def search_post(_driver,text):
    get_web_element( _driver, '//*[contains( @placeholder , "Search") ]' ).send_keys(Keys.CONTROL+'a')
    get_web_element( _driver, '//*[contains( @placeholder , "Search") ]' ).send_keys(Keys.BACKSPACE)
    get_web_element( _driver, '//*[contains( @placeholder , "Search") ]').send_keys(text)
    time.sleep(1) #solve stale element
    ExpectText = get_web_element( _driver, '//*[contains( @class , "ItemList__value ItemList__value--text") ]').text
    return  ExpectText

def clear_text_field(_driver,field):
    get_web_element( _driver, '//*[contains( @for,"%s") ]//*[contains(@type, "text")]' %field ).send_keys( Keys.CONTROL+'a' )
    get_web_element( _driver, '//*[contains( @for,"%s") ]//*[contains(@type, "text")]' %field ).send_keys( Keys.BACKSPACE )

def input_published_date_field(_driver):
    day = str(random.randint(1,30)).zfill(2)
    text = ('2019-05-"%s"' %(day))
    get_web_element( _driver, '//*[contains( @for,"publishedDate") ]//*[contains(@type, "text")]' ).send_keys( text )
    return day,text

if __name__=="__main__":
    testsuite=unittest.TestSuite()
    testsuite.addTest(test_post("test_create_post"))  
    testsuite.addTest(test_post("test_edit_post"))
    testsuite.addTest(test_post("test_search_post"))
    testsuite.addTest(test_post("test_delete_post"))  
    runner = unittest.TextTestRunner(verbosity=2)
    runner.run(testsuite)