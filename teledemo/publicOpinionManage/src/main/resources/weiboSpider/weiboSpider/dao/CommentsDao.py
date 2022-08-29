from lxml import etree

from weiboSpider.dao.BaseDao import BaseDao
from weiboSpider.dao.JobDao import JobDao


class CommentsDao(BaseDao):
    #读取到评论的id和内容
    def getcomments(self):
        baseDao=BaseDao()
        sql="select commentId,userComments from weibo_comments"
        baseDao.execute(sql)
        result=baseDao.fetchall()
        return result


    #链接的获取
    def getLink(self):
        result=self.getcomments()
        for r in result:
            #链接的提取
            originalcomments=r.get('userComments')
            id=r.get('commentId')
            h=etree.HTML(originalcomments)
            weblinks=h.xpath("//a/@href")
            if weblinks:
                weblink=weblinks[0]
                if weblink:
                    if weblink.find("https://")!=-1:
                        sql="update weibo_comments set Link=%s where commentId=%s"
                        params=[weblink,id]
                        jobDao=JobDao()
                        jobDao.createJobdata(sql,params)


            print(weblink)




    def resetcomment(self):
        result = self.getcomments()
        for r in result:
            originalcomments = r.get('userComments')
            id = r.get('commentId')
            h = etree.HTML("<div class='bupt'>"+originalcomments+"</div>")
            data=h.xpath("//div[@class='bupt']")[0]
            text=data.xpath('string(.)').strip()
            #获取所有的text
            sql = "update weibo_comments set comments_1=%s where commentId=%s"
            params = [text, id]
            jobDao = JobDao()
            jobDao.createJobdata(sql, params)



if __name__ == '__main__':
    commentsDao=CommentsDao()
    commentsDao.getLink()#提取相关链接
    commentsDao.resetcomment()