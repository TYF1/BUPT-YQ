# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter

from weiboSpider.dao.JobDao import JobDao


class WeibospiderPipeline:
    def process_item(self, item, spider):

        sql="insert into weibo_comments(userName,userFans,userComments,keyWord,Link,comments_1) values(%s,%s,%s,%s,%s,%s)"
        params=[item['userName'],item['userFans'],item['userComments'],item['keyWord'],item['Link'],item['comments_1']]
        try:
            jobDao=JobDao()
            result=jobDao.createJobdata(sql,params)
            if result>0:
                print("写入成功！")

        except Exception as e:
            print(e)
        finally:
            jobDao.close()
        return item
