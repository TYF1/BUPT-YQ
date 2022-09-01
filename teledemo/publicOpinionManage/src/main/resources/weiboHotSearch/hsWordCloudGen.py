from html.parser import HTMLParser
from lxml import etree
import wordcloud
import os
from re import A
import jieba

hsList = []
fileName = "hotSearch.html"
if os.path.exists(fileName):
    with open(fileName, encoding="UTF-8") as file:
        contents = file.read()
        html = etree.HTML(contents)
        hss = html.xpath("//span[@style='font-weight: bold;']/text()")
        for hs in hss:
            hs = hs.strip("#")
            hsList.append(hs)
        print(hsList)

stopwords = ['疫情', '的', '因', '下', '期间', '第', '了', '在', '个', '性']

wordDict = {}

textList = []
for hs in hsList:
    texts = jieba.cut(hs)
    for text in texts:
        if text not in stopwords:
            textList.append(text) 
    pass
print(textList)

for text in textList:
    if text not in wordDict.keys():
        wordDict[text] = textList.count(text)
wordDict = sorted(wordDict.items(), key=lambda item:item[1], reverse=True)
wordDict = wordDict[:80]

countedList = []
for word in wordDict:
    countedList.append(word[0])

print(countedList)

string = " ".join(countedList)
w = wordcloud.WordCloud(width=1000, height=700, font_path='MSYH.TTC', background_color=None)
w.generate(string)
w.to_file('output.png')