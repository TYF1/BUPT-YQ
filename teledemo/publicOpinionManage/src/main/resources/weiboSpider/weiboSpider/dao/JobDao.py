from .BaseDao import BaseDao
#.代表当前路径
class JobDao(BaseDao):
    def createJobdata(self,sql,params):
        result=self.execute(sql,params)
        self.commit()

    def getStatisticByJobCity(self):
        sql='SELECT AVG(jobMeanSalary)as avgsalary,jobCity FROM job GROUP BY jobCity ORDER BY avgsalary ;'
        result=self.execute(sql)
        return self.fetchall()

    def getStatisticByJobType(self):
        sql='SELECT AVG(jobMeanSalary)as avgsalary,jobType FROM job GROUP BY jobType ORDER BY avgsalary;'
        self.execute(sql)
        return self.fetchall()


