from .BaseDao import BaseDao

class WeiboDao(BaseDao):
    
    def createWeiboData(self, sql, params):
        result = self.execute(sql, params)
        self.commit()
        return result
    
    def getAllWeiboComments(self):
        sql = "select commentId, comments_1 from eqe.weibo_comments"
        self.execute(sql)
        return self.fetchall()
    
    def updateWeiboSentiment(self, data={}):
        sql = "update weibo_comments set sentiment=%s where commentId=%s"
        result = self.execute(sql, [data.get('sentiment'), data.get('commentId')])
        self.commit()
        return result
    
    pass