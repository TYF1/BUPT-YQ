from dao.BaseDao import BaseDao


class OpiDao(BaseDao):

    def createcontent(self,aid,data={}):

        sql = "insert into opinions (aid,context,feature,voteupcount,commentcount,heat,keyword,questiontext,qid,link) values (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        params = [aid,data.get('context'),data.get('feature'), data.get('voteupcount'), data.get('commentcount'),data.get('heat'),data.get('keyword'),data.get('questiontext'),data.get('qid'),data.get('link')]

        result = self.execute(sql, params)
        self.commit()
        return result
        pass

    def isempty(self):
        sql = "select count(*) from opinions"
        self.execute(sql)
        if self.fetchone() == 0:
            return True
        else:
            return False