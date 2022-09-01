import os
import sys

# user是查询输入的用户名，可以不为全名
# data是xxxx-xx-xx格式的字符串，查询的开始时间
# words是查询的相关关键字，如果和疫情相关必须加上，其他关键字可以用逗号添加在后面：“疫情，其他“
user = sys.argv[1]
data = sys.argv[2]
words = sys.argv[3]
# print(user, end=" ")
# print(data, end=" ")
# print(words, end=" ")
s = "E:\\user\\Desktop\\中软\\代码\\venv\\Scripts\\python E:\\user\\Desktop\\面向领域2\\weibo_crawler\\weibo_crawler.py "\
    + user + " " + data + " " + words
p = os.system(s)
print(p, end=" ")
s = "E:\\user\\Desktop\\中软\\代码\\venv\\Scripts\\python " \
    "E:\\user\\Desktop\\面向领域2\\weibo_crawler\\weibo-crawler\\weibo.py"
p = os.system(s)
print(p, end=" ")
