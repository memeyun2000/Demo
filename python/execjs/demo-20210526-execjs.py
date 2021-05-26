#%%
import execjs


if __name__ == "__main__":
    jsStr = "/** 这是注释 **/ var s= 'hello world'"
    jsComplied = execjs.compile(jsStr)
    varS = jsComplied.eval("s")
    print(varS)
    
# %%

