package com.example.mumuseng.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Mumuseng on 2016/3/26.
 */
public class MD5Utils {
    public static String getMd5Digest(String password)
    {
        StringBuffer result=new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b:digest) {
                int i = b & 0xff;
                String s = Integer.toHexString(i);
                result.append(s);

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result.toString();

    }
}
