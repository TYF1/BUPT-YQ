import json
from urllib.parse import quote

import scrapy
from lxml import etree
from scrapy import Request

from weiboSpider.items import WeibospiderItem

class ExampleSpider(scrapy.Spider):
    name = 'example'
    allowed_domains = ['m.weibo.com']
    #若没有设定关键词，默认关键词为疫情
    keyword="疫情"

    # base_url="https://m.weibo.cn/api/container/getIndex?containerid=100103type%3D1%26q%3D%E7%96%AB%E6%83%85&page_type=searchall&page=2"
    start_urls = []
    Referer={}

    def start_requests(self):
        yield Request(url="https://m.weibo.cn/api/container/getIndex?containerid=100103type%3D1%26q%3D"+self.keyword+"&page_type=searchall&page=1",headers=self.Referer,meta={"page":1,"keyword":self.keyword})

    # def __init__(self,baseURL,*args,**kw):
    #     super(ExampleSpider,self).__init__(*args,**kw)
    #     ExampleSpider.start_urls.append(baseURL)
    def __init__(self,keyword,*args,**kw):
        super(ExampleSpider,self).__init__(*args,**kw)
        ExampleSpider.keyword=keyword
        self.start_urls.append(
            'https://m.weibo.cn/api/container/getIndex?containerid=100103type%3D1%26q%3D' + keyword + '&page_type=searchall')
        self.Referer = {"Referer": "https://m.weibo.cn/search?containerid=100103type%3D1%26q%3D" + keyword}


    def parse(self, response):
        base_url="https://m.weibo.cn/api/container/getIndex?containerid=100103type%3D1%26q%3D"+self.keyword+"&page_type=searchall&page="
        # print(str(response.body, encoding="utf-8"))
        # print(str(response.text))
        results=json.loads(response.text)
        page=response.meta.get("page")
        keyword=response.meta.get("keyword")
        #下一页
        next_page=results.get("data").get("cardlistInfo").get("page")

        result = results.get("data").get("cards")
        for r in result:
            print("外循环")
            card_type=r.get("card_type")
            show_type = r.get("show_type")
            if show_type==0 and card_type==11:
                cg=r.get("card_group")[0]
                pipItem=WeibospiderItem()
                if(cg.get('mblog')):
                    userTime=cg.get("mblog").get("created_at")
                    userName=cg.get("mblog").get("user").get("screen_name")
                    userComments=cg.get("mblog").get("text")
                    userFans=cg.get("mblog").get("fans")
                    #停用词需要 全文、省略号等


                    h = etree.HTML(userComments)
                    weblinks = h.xpath("//a/@href")
                    weblink=None
                    if weblinks:
                        weblink = weblinks[0]
                        if weblink:
                            if weblink.find("https://") == -1:
                                weblink=None


                    h = etree.HTML("<div class='bupt'>" + userComments + "</div>")
                    data = h.xpath("//div[@class='bupt']")[0]
                    text = data.xpath('string(.)').strip()
                    # 获取所有的text


                    print(userName,userTime,userComments,userFans,weblink,text)
                    pipItem["userName"]=userName
                    pipItem["userTime"]=userTime
                    pipItem["userComments"]=userComments
                    pipItem["userFans"]=userFans
                    pipItem["keyWord"]=self.keyword
                    pipItem["Link"]=weblink
                    pipItem["comments_1"]=text
                    yield pipItem
        print("退出循环")
        print(page)
        print(next_page)
        if page != 3:
            url = base_url + str(next_page)
            yield Request(url=url, headers=self.Referer,dont_filter=True,callback=self.parse, meta={"page": next_page, "keyword": keyword})


        pass




