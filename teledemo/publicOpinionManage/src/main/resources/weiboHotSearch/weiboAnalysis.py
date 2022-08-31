from snownlp import SnowNLP
from snownlp import sentiment
from dao.weiboDao import WeiboDao
import numpy as np
import matplotlib.pyplot as plt


sentimentList = []  # 情感分析结果列表

try:
    weiboDao = WeiboDao()
    comments = weiboDao.getAllWeiboComments()
    commentsList = []
    for i in range(len(comments)):
        comment = comments[i]['comments_1']
        commentId = comments[i]['commentId']
        # 情感分析
        s = SnowNLP(comment)
        sentiment = s.sentiments
        sentimentList.append(sentiment)
        data = {
            'sentiment': sentiment,
            'commentId': commentId
        }
        weiboDao.updateWeiboSentiment(data)
        pass
except Exception as e:
    print(e)
finally:
    weiboDao.close()
    pass

freqList = np.zeros(3)
for i in range(len(sentimentList)):
    freqSerial = int(sentimentList[i] * 10)
    if freqSerial < 3:
        freqSerial = 0
    elif freqSerial > 7:
        freqSerial = 2
    else:
        freqSerial = 1
    freqList[freqSerial] += 1
    print(i)
    pass
print(freqList)

# 结果绘图
plt.rcParams['font.sans-serif'] = ['SimHei']  # 用来正常显示中文标签
# x = np.arange(0, 1.1, 0.1)
x = np.array(["负面","中性", "积极"])
print(x)
plt.bar(x, freqList, width=0.5)
plt.title(u"情绪分析分布图")
# plt.xlabel(u"积极性")
plt.ylabel(u"频数")
plt.show()


