import Test.Category as Category
import Test.Enquirie as Enquirie
import Test.Keyword as Keyword
import Test.Post as Post
import Test.User as User
import Test.Comment as Comment
# from  Test import  *

import unittest  # 需要引入 unittest、time、 等程式

if __name__=="__main__":
    test1 = [ Post.test_post('test_create_post'), Post.test_post('test_edit_post'), Post.test_post('test_search_post') , Post.test_post('test_delete_post') ]
    test2 = [ Comment.test_comment('test_create_comment'), Comment.test_comment('test_edit_comment'), Comment.test_comment('test_delete_comment') ]
    test3 = [ Category.test_category('test_create_category'), Category.test_category('test_show_post') ]
    test4 = [ Enquirie.test_enquire('test_create_enquiry'), Enquirie.test_enquire('test_delete_enquiry') ]
    test5 = [ User.test_user('test_create_user') ]
    suite = unittest.TestSuite()
    suite.addTests(test1)
    suite.addTests(test2)
    suite.addTests(test3)
    suite.addTests(test4)
    suite.addTests(test5)
    unittest.TextTestRunner(verbosity=2).run(suite)