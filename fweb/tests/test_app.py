import unittest

from app import app


class MyTest(unittest.TestCase):
    def setUp(self):
        app.app.config["TESTING"] = True
        app.app.config["WTF_CSRF_ENABLED"] = False
        app.app.config["DEBUG"] = False
        self.app = app.app.test_client()

        self.assertEqual(app.app.debug, False)

    def tearDown(self):
        pass

    def test_main_page(self):
        response = self.app.get("/", follow_redirects=True)
        self.assertEqual(response.status_code, 200)


if __name__ == "__main__":
    unittest.main()
