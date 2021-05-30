import sqlite3
import matplotlib.pyplot as plt
import numpy as np

conn = sqlite3.connect("/apps/demo/Demo/python/Spider/resoureces/sqlite3.db")
cur = conn.cursor()
whereStr = " and fund_dt >= '2021-03-01' and fund_dt <= '2021-07-01' "
# whereStr = ""
cur.execute("select fund_dt , amt from fund_worth where fund_id = '001792' " + whereStr)

fundList = cur.fetchall()

dateList = []
acList = []
for fund in fundList:
    dateList.append(fund[0])
    acList.append(fund[1])



print(dateList)
print(acList)


plt.plot(dateList,acList)
plt.show()


