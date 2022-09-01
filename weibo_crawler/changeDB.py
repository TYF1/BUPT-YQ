import time
import pymysql as pmq
from pymysql.cursors import DictCursor

conn = pmq.connect(
    host='localhost',
    port=3306,
    user='root',
    passwd='root',
    db='weibo',
)
cur = conn.cursor(DictCursor)

cur.execute("select id, created_at from weibo")
dates = cur.fetchall()

# print(type(dates[0].get("created_at")))

for date in dates:
    try:
        try:
            mktime = int(time.mktime(date.get("created_at").timetuple()))
            # print("UPDATE weibo SET timestamp={} WHERE id={}".format(mktime, date.get("id")))
            # print(mktime)
        except Exception as e:
            print(e)
        cur.execute("UPDATE weibo SET timestamp={} WHERE id={}".format(mktime, date.get("id")))
    except Exception as e:
        print(e)

conn.commit()