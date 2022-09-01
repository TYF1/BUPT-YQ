# BUPT-YQ

### 舆情管理模块

python脚本文件夹：./teleDemo/publicOpinionManage/src/main/resources

+ 脚本中包含：爬虫脚本、wordcloud生成脚本、微博爬虫结果情感分析脚本

> 由于微博网站的反爬虫机制，脚本文件具有执行不稳定的特点。因此，未设置前端与脚本的交互机制。使用python脚本进行爬虫，并将结果存入数据库。后端读取数据库中信息进行展示

##### Sql文件夹：sql
+ eqe.sql：包含创建schema语句，创建t_hotsearch热搜表以及weibo_comments微博爬虫结果表

