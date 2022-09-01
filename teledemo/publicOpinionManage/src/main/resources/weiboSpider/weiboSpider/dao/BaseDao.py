import pymysql
import json
import os
class BaseDao():
    def __init__(self):
        myConfig = {
                           "host": "127.0.0.1",
                           "user": "root",
                           "password": "000000",
                           "database": "eqe",
                           "port": 3306,
                           "charset":"utf8"
                       }
        self.__config = myConfig
        self.__conn = None
        self.__cursor = None
        pass

    def getConnection(self):
        if self.__conn!=None:
            return self.__conn
        pass
        self.__conn=pymysql.connect(**self.__config)#传多个参数
        return self.__conn
        pass

    def execute(self,sql,params=[],ret="dict"):
        result=0
        try:
            self.__conn=self.getConnection()
            if ret == 'dict':
                self.__cursor=self.__conn.cursor(pymysql.cursors.DictCursor)
            else:
                self.__cursor=self.__conn.cursor()
            result=self.__cursor.execute(sql,params)
            #params为传参，sql中的参数
        except pymysql.DatabaseError as e:
            print(e)

        return  result
        pass


    def fetchone(self):
        if self.__cursor!=None:
            return self.__cursor.fetchone()
        pass


    def fetchall(self):
        if self.__cursor:
            return self.__cursor.fetchall()
        pass



    def close(self):
        if self.__cursor:
            self.__cursor.close()
            pass
        if self.__conn:
            self.__conn.close()
            pass


    def commit(self):
        if self.__conn:
            self.__conn.commit()


    def rollback(self):
        if self.__conn:
            self.__conn.rollback()


    pass


# mysql相关操作
# 1.连接
#     pymysql.connect(相关参数 user、password、database、port、charset)
# 2.cursor
# 3.cursor.execute(sql,params)
# 4.cursor.fentchall()
# 5.close——cursor、connection