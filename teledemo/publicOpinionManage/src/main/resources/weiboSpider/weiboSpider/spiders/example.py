import json
from urllib.parse import quote

import scrapy
from scrapy import Request

from weiboSpider.items import WeibospiderItem

class ExampleSpider(scrapy.Spider):
    name = 'example'
    allowed_domains = ['m.weibo.com']
    keyword="新冠"
    # base_url="https://m.weibo.cn/api/container/getIndex?containerid=100103type%3D1%26q%3D%E7%96%AB%E6%83%85&page_type=searchall&page=2"
    start_urls = ['https://m.weibo.cn/api/container/getIndex?containerid=100103type%3D1%26q%3D'+keyword+'&page_type=searchall']
    Referer={"Referer":"https://m.weibo.cn/search?containerid=100103type%3D1%26q%3D"+keyword}

    def start_requests(self):
        yield Request(url="https://m.weibo.cn/api/container/getIndex?containerid=100103type%3D1%26q%3D"+self.keyword+"&page_type=searchall&page=1",headers=self.Referer,meta={"page":1,"keyword":self.keyword})

    # def __init__(self,baseURL,*args,**kw):
    #     super(ExampleSpider,self).__init__(*args,**kw)
    #     ExampleSpider.start_urls.append(baseURL)


    def parse(self, response):
        base_url="https://m.weibo.cn/api/container/getIndex?containerid=100103type%3D1%26q%3D"+self.keyword+"&page_type=searchall&page="
        # print(str(response.body, encoding="utf-8"))
        # print(str(response.text))
        results=json.loads(response.text,encoding="utf-8")
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
                    print(userName,userTime,userComments,userFans)
                    # pipItem["userName"]=userName
                    # pipItem["userTime"]=userTime
                    # pipItem["userComments"]=userComments
                    # pipItem["userFans"]=userFans
                    # yield pipItem
        print("退出循环")
        print(page)
        print(next_page)
        if page != next_page:
            url = base_url + str(next_page)
            yield Request(url=url, headers=self.Referer,dont_filter=True,callback=self.parse, meta={"page": next_page, "keyword": keyword})

        # print('执行代码')
        # selectlist=response.xpath("//div[@class='job-card']")
        # print(selectlist)
        # if selectlist:
        #     for item in selectlist:
        #         pipItem=WeibospiderItem()
        #
        #         userName=item.xpath("//h3[@class='m-text-cut']/text()").extract()[0]
        #         userTime=item.xpath("//h4[@class='time']/text()").extract()[0]
        #         userComments=item.xpath("//div[@class='weibo-text']/text()").extract()[0]
        #         print(userName,userTime,userComments)
        #
        #         pipItem['userName']=userName
        #         pipItem['userTime']=userTime
        #         pipItem['userComments']=userComments
        #         yield pipItem

        pass




