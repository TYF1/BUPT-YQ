import os
import sys
from urllib.parse import urlencode
from lxml import etree

import re
import requests

headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) "
                  "Chrome/54.0.2840.99 Safari/537.36",
    "Cookie": "SINAGLOBAL=2571391638955.1895.1652513192992; un=13087647990; ariaDefaultTheme=default; ariaFixed=true; "
              "ariaReadtype=1; ariaMouseten=null; ariaStatus=false; _s_tentry=weibo.com; "
              "Apache=4473803530929.02.1661485214708; "
              "ULV=1661485214748:3:2:2:4473803530929.02.1661485214708:1661146206498; "
              "SUBP=0033WrSXqPxfM72wWs9jqgMF55529P9D9WWHxKCEXV8CvPq1yrbNmN_Y5JpVF020ehnNSoBN1K.7; "
              "SUB=_2AkMUVM5WdcPxrAZUm_Acz23maYpH-jyngaegAn7uJhMyAxh87lwWqSdutBF-XEMn9NBEkNbDAw0b24bkUcctVE9D; "
              "login_sid_t=a33b68ac2de2ed1cc20caf4e7a85cc75; cross_origin_proto=SSL; UOR=www.baidu.com,weibo.com,"
              "cn.bing.com; PC_TOKEN=504ab88be8"
}

def find_uid_title_from_xpath_element(result_):
    users_ = set()
    for e in result_:
        # print(etree.tostring(e, encoding="utf-8").decode())
        # print(e)
        id = str(e.xpath("./@href")[0])
        # print(id)
        match_obj = re.match(r".*/(\d+)\D?", id)
        title = e.xpath("./text()")
        # title = etree.tostring(e, encoding="utf-8").decode
        if match_obj and title is not None:
            # print(match_obj)
            # print(uid)
            title = title[0]
            # print(title)
            if str(title).find(keyword) != -1:
                uid = match_obj.group(1)
                users_.add(uid + " " + title)
    return users_


if __name__ == "__main__":
    keyword = sys.argv[1]
    data = sys.argv[2]
    words = sys.argv[3]
    # keyword = "人民网"
    users = []
    url = 'https://s.weibo.com/weibo?' + urlencode({"q": keyword})

    # print(url)
    strhtml = requests.get(url, headers=headers)  # Get方式获取网页数据
    html = etree.HTML(strhtml.text)
    xpaths = ["//div[@class='card card-user-b s-brt1 card-user-b-padding']/div[2]/div[1]/a",
              "//div[@node-type='like' and @class='content']/div[1]/div[2]/a"]
    # print(html)
    # print(type(html))
    # print(type(result))
    # print("result:", end="")
    # print(result)
    #
    # for e in result:
    #     print(etree.tostring(e, encoding="utf-8").decode())
    for xpath in xpaths:
        result = html.xpath(xpath)
        users.extend(find_uid_title_from_xpath_element(result))

    with open(os.path.abspath("./weibo-crawler/users.txt"), "w", encoding="utf-8") as fp:
        for u in users:
            fp.write(u + " " + data + " " + words + "\n")
