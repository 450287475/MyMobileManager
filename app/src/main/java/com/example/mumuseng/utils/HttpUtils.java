package com.example.mumuseng.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Mumuseng on 2016/3/24.
 */
public class HttpUtils {



    public static String getTextFromInputStream(InputStream inputStream){
        String text=null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len=-1;
        byte[] bytes = new byte[1024];
        try {
            while ((len=inputStream.read(bytes))!=-1){
                baos.write(bytes,0,len);
                text = baos.toString("GBK");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
