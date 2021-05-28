# %%
import sqlite3
import execjs
import time
import requests

def fundsPersistMaxDtDict(conn):
    cur = conn.cursor()
    cur.execute("select fund_id,max(fund_dt) from fund_worth group by fund_id")
    fundMaxDtList = cur.fetchall()
    cur.close()
    dict = {}
    for data in fundMaxDtList:
        dict[data[0]] = data[1]
    return dict

def fundTblPersistDict(conn):
    curr = conn.cursor()
    curr.execute("select fund_id,descr from fund_tbl ")
    list = curr.fetchall()
    curr.close()
    dict = {}
    for fund in list:
        dict[fund[0]] = fund[1]
    return dict


conn = sqlite3.connect("/apps/demo/Demo/python/Spider/resoureces/sqlite3.db")
print("connected to database.")

fundMaxDtDict = fundsPersistMaxDtDict(conn)
fundTblDict = fundTblPersistDict(conn)
updateDateStr = time.strftime("%Y-%m-%d", time.localtime())
lateUpdateDateStr = time.strftime("%Y-%m-%d", time.localtime(time.time() - 86400 * 3 ))


def requestFundData(fund_id):
    url = "http://fund.eastmoney.com/pingzhongdata/" + fund_id + ".js?v=" + str(int(time.time()))
    print(url)
    try:
        resultStr = requests.get(url).text
    except BaseException:
        print(url, " access failed!")
        resultStr = ""
    return resultStr


def analysisRequestData(responseStr):
    try:
        jsCompiled = execjs.compile(responseStr)
        #structs [(fundID,fundAmt),...]
        acWorthList = jsCompiled.eval("Data_ACWorthTrend")
    except:
        print("responseStr compiled faild!!")
        acWorthList = []
    return acWorthList



def fundDataPersist(conn,acWorthList, fund_id, persistedFundDt):
    cur = conn.cursor()
    for x in acWorthList:
        _fundDt = time.strftime("%Y-%m-%d", time.localtime(x[0] / 1000))
        if _fundDt <= persistedFundDt :
            continue
        data = (fund_id, _fundDt, x[1],updateDateStr)
        cur.execute("insert into fund_worth(fund_id,fund_dt,amt,updatedate) values(?,?,?,?)", data)
    cur.close()
    conn.commit()

def findFundMaxDt(fundID):
    if fundID in fundMaxDtDict:
        return fundMaxDtDict[fundID]
    else:
        return ""



def updateFund(conn,fundID):
    fundMaxDt = findFundMaxDt(fundID)
    print("fund:", fundID ," max date is :"+fundMaxDt+" ,persist fund data")
    if fundMaxDt >= lateUpdateDateStr :
        return
    responseStr = requestFundData(fundID)
    worthList = analysisRequestData(responseStr)

    fundDataPersist(conn,worthList,fundID,fundMaxDt)
    print("updated one fundation ac data:", fundID)
    conn.commit()

def updateFundLList(conn,fundList):
    for fundId in fundList:
        updateFund(conn,fundId)








def updateFundTbl(conn):
    responseData = requests.get("http://fund.eastmoney.com/js/fundcode_search.js");
    compiledJs = execjs.compile(responseData.text)
    # [["000001","HXCZHH","华夏成长混合","混合型","HUAXIACHENGZHANGHUNHE"],...]
    fundList = compiledJs.eval("r")
    curr = conn.cursor()
    for fundData in fundList:
        fundID = fundData[0]
        if fundID in fundTblDict:
            continue
        fundShortEnName = fundData[1]
        descr = fundData[2]
        fundTypeDescr = fundData[3]
        fundLongEnName = fundData[4]
        param = (fundID,descr,fundTypeDescr,fundShortEnName,fundLongEnName,updateDateStr)
        curr.execute("insert into fund_tbl (fund_id,descr,fund_type_descr,short_en_name,long_en_name,updatetime) values(?,?,?,?,?,?)",param)
        print("Attention:  add new fundation:",fundID)
    curr.close();
    conn.commit()
    print("refreshed all fundation definition.")

if __name__ == '__main__':
    updateFundTbl(conn)
    # fundList = list(fundTblDict.keys())
    # updateFundLList(conn,fundList)

conn.close()
print("closed database connection")