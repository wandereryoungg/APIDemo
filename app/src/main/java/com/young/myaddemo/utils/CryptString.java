package com.young.myaddemo.utils;

public class CryptString {
    public static String decConstStr(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String key = str.substring(0, 8);
        StringBuffer sb = new StringBuffer(key);
        try {
            return CryptBase64.decryptAES(str.substring(8), sb.reverse().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
