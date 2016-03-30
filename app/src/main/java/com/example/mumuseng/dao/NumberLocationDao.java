package com.example.mumuseng.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mumuseng.application.MyApplication;

/**
 * Created by Mumuseng on 2016/3/29.
 */
public class NumberLocationDao {
   public static String getLocation(String number){
       String result="";
       if(number.length()>=7) {
           String substring = number.substring(0, 7);
           SQLiteDatabase sqLiteDatabase = MyApplication.mContext.openOrCreateDatabase(MyApplication.mContext.getFilesDir().getAbsolutePath() + "/naddress.db",
                   Context.MODE_PRIVATE, null);
           Cursor cursor = sqLiteDatabase.rawQuery("select city, cardtype from address_tb where _id=(select outkey from numinfo where mobileprefix=" + substring + ")", null);
           while (cursor.moveToNext()) {
               String city = cursor.getString(cursor.getColumnIndex("city"));
               String cardtype = cursor.getString(cursor.getColumnIndex("cardtype"));
               result = city + ":" + cardtype;
           }
       }
       return result;
   }
}
