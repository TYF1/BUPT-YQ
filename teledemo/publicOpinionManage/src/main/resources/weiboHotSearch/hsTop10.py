from html.parser import HTMLParser
import sys
import os
# sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from dao.weiboDao import WeiboDao
from lxml import etree
import html

'''
hsDict = {
    'hsContent' = '#xxxxxx#',
    'hsHotness' = '111111',
    'hsTime' = 'YYYY-MM-DD HH:MM:SS'
}
'''
hsDict = {}

fileName = "teledemo/publicOpinionManage/src/main/resources/weiboHotSearch/hotSearch.html"
if os.path.exists(fileName):
    with open(fileName, encoding="UTF-8") as file:
        contents = file.read()
        html = etree.HTML(contents)
        
        hsList = html.xpath("//span[@style='font-weight: bold;']/text()")
        hotnessList = html.xpath("//td[@class='el-table_1_column_5 is-center ']/div/text()")
        timeList = html.xpath("//td[@class='el-table_1_column_6 is-center ']/div/span/text()")
        
        # for hs, hotness in zip(hsList, hotnessList):
        #     hsDict[hs] = int(hotness)
        # hsTupleList = sorted(hsDict.items(), key=lambda item:item[1], reverse=True)        
        # top10hs = hsTupleList[:10]
        # print(top10hs)
        weiboDao = WeiboDao()

        try:
            for hs, hotness, time in zip(hsList, hotnessList, timeList):
                sql = 'insert into t_hotsearch (hsContent, hsHotness, hsTime) values (%s, %s, %s)'
                params = [hs, hotness, time]
                weiboDao.createWeiboData(sql, params)
                print(hs)
        except Exception as e:
            print(e)
        finally:
            weiboDao.close()
            
        
        