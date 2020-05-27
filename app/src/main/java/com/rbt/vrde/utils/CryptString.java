package com.rbt.vrde.utils;


public class CryptString {

	public static String decConstStr(String src) {
	    if(src == null || src.length() == 0) {
	        return null;
        }
		try {
			String key = src.substring(0, 8);
			StringBuffer sb = new StringBuffer(key);
			return CryptBase64.decryptDES(src.substring(8), sb.reverse().toString());
		} catch (Exception e) {
			return null;
		}

	}

    public static String encConstStr(String src) {
        if(src == null || src.length() == 0) {
            return null;
        }
        String key = CryptBase64.initKeyDes();
        String result = "";
        try {
            result = CryptBase64.encryptDES(src, key);
        } catch (Exception e) {
            return null;
        }
        StringBuffer sb = new StringBuffer(key);
        return sb.reverse().toString() + result;
    }

}
