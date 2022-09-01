import pymysql
import json

class BaseDao():
    def __init__(self):
#         self.__config = json.load(open(config, mode='r', encoding="utf-8"))
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
        if self.__conn != None:
            return self.__conn
        self.__conn = pymysql.connect(**self.__config)
        return self.__conn
    
    def execute(self, sql, params=[], ret="dict"):
        result = 0
        try:
            self.__conn = self.getConnection()
            if ret == "dict":
                self.__cursor = self.__conn.cursor(pymysql.cursors.DictCursor)
            else:
                self.__cursor = self.__conn.cursor()
            result = self.__cursor.execute(sql, params)
        except pymysql.DatabaseError as e:
            print(e)
            pass
        
        return result
    
    def fetchone(self):
        if self.__cursor:
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
        pass
    
    def commit(self):
        if self.__conn:
            self.__conn.commit()
        pass
    
    def rollback(self):
        if self.__conn:
            self.__conn.rollback()
            pass
        pass