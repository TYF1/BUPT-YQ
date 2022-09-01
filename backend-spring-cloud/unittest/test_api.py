import pytest as pytest
import requests
import unittest



class TestAPI(unittest.TestCase):
    def test_get_by_type(self):
        url = "http://127.0.0.1:10003/publicopinion/type/"
        data = {
            "page":1,
            "limit":20,
            "type":0,
            "property":"dd"
        }
        res01 = requests.get(url,data)
        self.assertEqual(res01.status_code,200)
        #检查起始页出界情况
        data["page"] = -1
        self.assertEqual(res01.status_code, 200)
        data["page"] = 9999
        self.assertEqual(res01.status_code, 200)
        # 检查类型数据出界情况
        data["page"] = 1
        data["type"] = 10
        self.assertEqual(res01.status_code, 200)

    def test_get_by_time(self):
        url = "http://127.0.0.1:10003/publicopinion/time/"
        data = {
            "page":1,
            "limit":20,
            "since_day":0,
            "property":"dd"
        }
        res02 = requests.get(url,data)
        self.assertEqual(res02.status_code,200)
        self.assertIsNotNone(res02.json()["content"])

    def test_get_by_keyword(self):
        url = "http://127.0.0.1:10003/publicopinion/keyword/"
        data = {
            "page": 1,
            "limit": 20,
            "keyword": "上海"
        }
        res03 = requests.get(url, data)
        self.assertEqual(res03.status_code, 200)
        self.assertEqual(res03.json()["empty"],False)

    def test_get_by_user(self):
        url = "http://127.0.0.1:10003/publicopinion/user/"
        data = {
            "page": 1,
            "limit": 20,
            "user": "丁香医生",
            "property": "dd"
        }
        res04 = requests.get(url, data)
        self.assertEqual(res04.status_code, 200)
        data["user"] = "NullUser1234"
        self.assertEqual(res04.status_code, 404)


    def test_get_stats(self):
        url = "http://127.0.0.1:10003/publicopinion/stats/"
        res05 = requests.get(url)
        self.assertEqual(res05.status_code, 200)
        json01 = res05.json()
        self.assertEqual(json01["negative"]+json01["positive"]+json01["neutral"], json01["total"])


if __name__ == '__main__':
    unittest.main()