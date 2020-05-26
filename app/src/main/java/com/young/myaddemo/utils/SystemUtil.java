package com.young.myaddemo.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebSettings;

import com.rbt.vrde.core.CoreManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Random;

public class SystemUtil {

    public static int getOSVCode() {
        return Build.VERSION.SDK_INT;
    }

    public static String getDevVendor() {
        return Build.BRAND;
    }

    public static String getDevModel() {
        return Build.MODEL;
    }

    public static int getAppVer() {
        return new Random().nextInt(15);
    }

    public static String getPackageName() {
        return CoreManager.getContext().getPackageName();
    }

    public static String getWifiMac() {
        return MacUtil.getInstance(CoreManager.getContext()).getWifiMac();
    }

    public static HashMap<String, Integer> getScreenSize() {
        WindowManager manager = (WindowManager) CoreManager.getContext().getSystemService(com.rbt.vrde.utils.R.utils.SystemUtil._window);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        HashMap<String, Integer> map = new HashMap<>();
        map.put(com.rbt.vrde.utils.R.utils.SystemUtil._width, width);
        map.put(com.rbt.vrde.utils.R.utils.SystemUtil._height, height);
        return map;
    }

    public static int getOperatorTypeInt(String imsi) {
        if (imsi != null) {
            if (imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46000) || imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46002) || imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46007) || imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46020)) {
                // 中国移动
                return 1;
            } else if (imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46001) || imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46006)) {
                // 中国联通
                return 3;
            } else if (imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46003) || imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46005) || imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46013) || imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46014) || imsi.startsWith(com.rbt.vrde.utils.R.utils.SystemUtil._46011)) {
                // 中国电信
                return 2;
            }
        }
        return 0;
    }

    public static String getIMSI() {
        if (!checkPermission(com.rbt.vrde.utils.R.utils.SystemUtil._android_permission_READ_PHONE_STATE)) {
            return null;
        }
        TelephonyManager manager = (TelephonyManager) CoreManager.getContext().getSystemService(com.rbt.vrde.utils.R.utils.SystemUtil._phone);
        @SuppressLint("MissingPermission") String imsi = manager.getSubscriberId();
        if (imsi == null) {
            imsi = manager.getSimOperator();
        }
        return imsi;
    }

    public static String getNetworkType() {
        String netType = com.rbt.vrde.utils.R.utils.SystemUtil._CELL_UNKNOWN;
        if (!checkPermission(com.rbt.vrde.utils.R.utils.SystemUtil._android_permission_ACCESS_NETWORK_STATE)) {
            return netType;
        }
        ConnectivityManager manager = (ConnectivityManager) CoreManager.getContext().getSystemService(com.rbt.vrde.utils.R.utils.SystemUtil._connectivity);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    netType = com.rbt.vrde.utils.R.utils.SystemUtil._WIFI;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String typeName = networkInfo.getSubtypeName();

                    // TD-SCDMA   networkType is 17
                    int networkType = networkInfo.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                            netType = com.rbt.vrde.utils.R.utils.SystemUtil._CELL_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                        case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                        case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                            netType = com.rbt.vrde.utils.R.utils.SystemUtil._CELL_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                            netType = com.rbt.vrde.utils.R.utils.SystemUtil._CELL_4G;
                            break;
                        default:
                            // TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (typeName.equalsIgnoreCase(com.rbt.vrde.utils.R.utils.SystemUtil._TD_SCDMA) || typeName.equalsIgnoreCase(com.rbt.vrde.utils.R.utils.SystemUtil._WCDMA) || typeName.equalsIgnoreCase(com.rbt.vrde.utils.R.utils.SystemUtil._CDMA2000)) {
                                netType = com.rbt.vrde.utils.R.utils.SystemUtil._CELL_3G;
                            } else {
                                netType = com.rbt.vrde.utils.R.utils.SystemUtil._CELL_UNKNOWN;
                            }
                            break;
                    }
                }

            }
        }
        return netType;
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(CoreManager.getContext().getContentResolver(), com.rbt.vrde.utils.R.utils.SystemUtil._android_id);
    }

    public static String getUserAgent() {
        if (CoreManager.getContext() != null && getOSVCode() >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return WebSettings.getDefaultUserAgent(CoreManager.getContext());
        }
        return "";
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        String imei = AnalyticsUtils.getImei(CoreManager.getContext());
        if (!checkPermission(com.rbt.vrde.utils.R.utils.SystemUtil._android_permission_READ_PHONE_STATE)) {
            return imei;
        }
        TelephonyManager manager = (TelephonyManager) CoreManager.getContext().getSystemService(com.rbt.vrde.utils.R.utils.SystemUtil._phone);
        if (manager != null) {
            return StringUtil.isBlank(manager.getDeviceId()) ? imei : manager.getDeviceId();
        }
        return imei;
    }

    public static int getScreenDensity() {
        WindowManager manager = (WindowManager) CoreManager.getContext().getSystemService(com.rbt.vrde.utils.R.utils.SystemUtil._window);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return (int) dm.density;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static boolean checkPermission(String p) {
        synchronized (SystemUtil.class) {
            if (hasSupportMApi()) {
                int result = checkSelfPermission(p);
                return result == PackageManager.PERMISSION_GRANTED;
            } else {
                PackageManager packageManager = CoreManager.getContext().getPackageManager();
                int permission = packageManager.checkPermission(p, CoreManager.getContext().getPackageName());
                return permission == PackageManager.PERMISSION_GRANTED;
            }
        }
    }

    //TODO会报找不到Method的Exception
    public static int checkSelfPermission(String permission) {
        try {
            Class cls = Class.forName("android.content.ContextWrapper");
            Method checkSelfPermission = cls.getDeclaredMethod("checkSelfPermission", String.class);
            checkSelfPermission.setAccessible(true);
            Object obj = checkSelfPermission.invoke(null, permission);
            if (null != obj) {
                return (int) obj;
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return PackageManager.PERMISSION_DENIED;
    }

    public static boolean hasSupportMApi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }


}
