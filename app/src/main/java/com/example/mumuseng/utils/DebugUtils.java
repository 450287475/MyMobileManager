package com.example.mumuseng.utils;

/**
 * Created by Mumuseng on 2016/3/25.
 */

public class DebugUtils {
    private static final boolean FLAG = true;

    public static void sout(String info){
        if(FLAG){
            System.out.println(info);
        }
    }

}
