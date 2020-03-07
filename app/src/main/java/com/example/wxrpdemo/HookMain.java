package com.example.wxrpdemo;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

 /*
         # Created by Weiya on 2018/3/27.
         相关的 xposed hook代码都在这里编写
*/

public class HookMain implements IXposedHookLoadPackage {
    @Override //重写了 handleLoadPackage 方法
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable
    {
        XposedBridge.log("7763 load app: " + loadPackageParam.packageName);//显示加载的 app 名称
        Tools tool = new Tools();
        tool.hookDatabaseInsert(loadPackageParam);
    }
}