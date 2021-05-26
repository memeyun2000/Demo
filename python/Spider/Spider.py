#%%
import sqlite3
import execjs
import time
import requests

def requestFundData(fund_id):
    url = "http://fund.eastmoney.com/pingzhongdata/"+fund_id+".js?v=" + str(int(time.time()))
    print(url)
    fund_reponnse = requests.get(url)
    return fund_reponnse.text



if __name__ == '__main__':
    fund_id = "003511"
    jsCommandStr = requestFundData(fund_id)
    jsCompiled = execjs.compile(jsCommandStr)
    acWorthList = jsCompiled.eval("Data_ACWorthTrend")
    
    conn = sqlite3.connect("/apps/demo/Demo/python/Spider/resoureces/sqlite3.db")
    cur = conn.cursor();
    for x in acWorthList:
        data = (fund_id,time.strftime("%Y-%m-%d",time.localtime(x[0] / 1000)),x[1])
        cur.execute("insert into fund_worth(fund_id,fund_dt,amt) values(?,?,?)",data)

    conn.commit()
    conn.close()

    print()

    



# %%
