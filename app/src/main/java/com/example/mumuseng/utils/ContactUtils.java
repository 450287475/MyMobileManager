package com.example.mumuseng.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.bean.Contact;

import java.util.ArrayList;

/**
 * Created by Mumuseng on 2016/3/29.
 */
public class ContactUtils {
    public static ArrayList<Contact> getContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();

        ContentResolver contentResolver = MyApplication.mContext.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),
        new String[]{"contact_id"},null,null, null);
        while (cursor.moveToNext()){
            Contact contact = new Contact();
            int cursorInt = cursor.getInt(0);
            if(cursorInt==0){
                continue;
            }
            Cursor cursor1 = contentResolver.query(Uri.parse("content://com.android.contacts/data"),
                    new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{"" + cursorInt}, null);
            while (cursor1.moveToNext()){
                String data1 = cursor1.getString(cursor1.getColumnIndex("data1"));
                String mimetype = cursor1.getString(cursor1.getColumnIndex("mimetype"));
                if("vnd.android.cursor.item/name".equals(mimetype)){
                    contact.setName(data1);
                }else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                    contact.setNumber(data1);
                }

            }
            contacts.add(contact);

        }
        System.out.println(contacts);
        return contacts;
    }
}
