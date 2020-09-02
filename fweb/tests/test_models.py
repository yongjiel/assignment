import unittest

from app import models


class MyModelsTest(unittest.TestCase):
    def setUp(self):
        pass

    def tearDown(self):
        pass

    def test_modeles(self):
        s = "New Message"
        m = models.Message(s)
        self.assertEqual(m.get_message(), s)


if __name__ == "__main__":
    unittest.main()
