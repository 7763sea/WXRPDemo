package com.example.wxrpdemo;

import android.content.ContentValues;

import java.security.PublicKey;
import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Tools {

    public boolean log_isOpen = true;

    public void log(boolean isOpen,String Tag,String text)
    {
        if (isOpen)
        XposedBridge.log(Tag + text);
    }

    public  void hookDatabaseInsert(final XC_LoadPackage.LoadPackageParam loadPackageParam){
        Class<?> classDb = XposedHelpers.findClassIfExists("com.tencent.wcdb.database.SQLiteDatabase",loadPackageParam.classLoader);

        if (classDb == null)
        {
            this.log(log_isOpen,"7763","hook数据库Insert操作：未找到类");
        }

        XposedHelpers.findAndHookMethod(
                classDb,
                "insertWithOnConflict",
                String.class,
                String.class,
                ContentValues.class,
                int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                       log(log_isOpen,"7763","----------------insert start -------------------"+"\r\n");
                       log(log_isOpen,"7763","param args0: "+ param.args[0]+"\r\n");
                       log(log_isOpen,"7763","param args1: "+ param.args[1]+"\r\n");
                       ContentValues contentValues = (ContentValues)param.args[2];
                       log(log_isOpen,"7763","param args2: "+"\r\n");

                       for (Map.Entry<String,Object> item: contentValues.valueSet()){
                           if (item.getValue()!=null)
                           {
                               log(log_isOpen,"7763","key is --->"+item.getKey()+"value is --->"+item.getValue().toString());
                           }else
                           {
                               log(log_isOpen,"7763","key is --->"+item.getKey()+"value is null");
                           }
                       }
                        log(log_isOpen,"7763","----------------insert over -------------------"+"\r\n");

                       String tableName = (String)param.args[0];
                       if (tableName.isEmpty()||!tableName.equals("message"))
                       {
                           return;
                       }

                       Integer type = contentValues.getAsInteger("type");
                        log(log_isOpen,"7763","----------------red packet type is  -------------------"+type.toString()+"\r\n");
                       if (type == 436207665)
                       {

                       }else
                       {

                       }
                        super.afterHookedMethod(param);
                    }
                });
    }
}
