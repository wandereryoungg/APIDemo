package com.rbt.vrde.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class MySystemProperties {
	private static final String TAG = MySystemProperties.class.getSimpleName();
	//android.os.SystemProperties
	@SuppressWarnings("rawtypes")
	private static Class sClass;
	private static Method sMethodGet;
	private static Method sMethodSet;
	private static Method sMethodGetInt;
	
	static {
		try {
			sClass = Class.forName(com.rbt.vrde.utils.R.utils.MySystemProperties._android_os_SystemProperties);
			sMethodGet = sClass.getDeclaredMethod(com.rbt.vrde.utils.R.utils.MySystemProperties._get, new Class[] { String.class });
			sMethodSet = sClass.getDeclaredMethod(com.rbt.vrde.utils.R.utils.MySystemProperties._set, new Class[] { String.class, String.class });
			sMethodGetInt = sClass.getDeclaredMethod(com.rbt.vrde.utils.R.utils.MySystemProperties._getInt, new Class[] { String.class, int.class });
		} catch (ClassNotFoundException e) {
//			MyLog.e(TAG, "```----class android.os.SystemProperties not found.----");
		} catch (SecurityException e) {
//			MyLog.e(TAG, "```----SecurityException----" + e.getMessage());
		} catch (NoSuchMethodException e) {
//			MyLog.e(TAG, "```----NoSuchMethodException get(String)----");
		}
//		MyLog.i(TAG, "```----class android.os.SystemProperties is found.----"+sClass);
	}
	public MySystemProperties() {
		
	}
	
	public static String get(String key) {
		if (sMethodGet == null)
			return null;
		try {
			Object ret = sMethodGet.invoke(null, key);
			return (String) ret;
		} catch (IllegalArgumentException e) {
//			MyLog.e(TAG, "```----IllegalArgumentException----");
		} catch (IllegalAccessException e) {
//			MyLog.e(TAG, "```----IllegalAccessException----");
		} catch (InvocationTargetException e) {
//			MyLog.e(TAG, "```----InvocationTargetException----");
		}
		return null;
	}
	
	public static String set(String key, String value) {
		if (sMethodSet == null)
			return null;
		try {
			Object ret = sMethodSet.invoke(null, key, value);
			return (String) ret;
		} catch (IllegalArgumentException e) {
//			MyLog.e(TAG, "```----IllegalArgumentException----");
		} catch (IllegalAccessException e) {
//			MyLog.e(TAG, "```----IllegalAccessException----");
		} catch (InvocationTargetException e) {
//			MyLog.e(TAG, "```----InvocationTargetException----");
		}
		return null;
	}
	
	public static int getInt(String key, int def){
		if (sMethodGetInt == null)
			return -100;
		
		try {
			Object ret = sMethodGetInt.invoke(null, key, def);
			return (Integer) ret;
		} catch (IllegalArgumentException e) {
//			MyLog.e(TAG, "```----IllegalArgumentException----");
		} catch (IllegalAccessException e) {
//			MyLog.e(TAG, "```----IllegalAccessException----");
		} catch (InvocationTargetException e) {
//			MyLog.e(TAG, "```----InvocationTargetException----");
		}
		
		return -100;
	}
}
