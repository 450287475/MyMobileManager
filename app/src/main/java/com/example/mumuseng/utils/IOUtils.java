package com.example.mumuseng.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Mumuseng on 2016/3/29.
 */
public class IOUtils {
    public static void fisWriteToFos(InputStream open, FileOutputStream fileOutputStream) throws IOException {
        int len=-1;
        byte[] bytes = new byte[1024];
        while ((len=open.read(bytes))!=-1){
            fileOutputStream.write(bytes,0,len);
        }
    }
}
