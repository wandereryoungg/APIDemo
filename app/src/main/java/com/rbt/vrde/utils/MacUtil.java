package com.rbt.vrde.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.young.myaddemo.utils.CryptPrefs;
import com.young.myaddemo.utils.StringUtil;
import com.young.myaddemo.utils.SystemUtil;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class MacUtil {

    public static MacUtil macUtil;
    private Context context;
    private final static String PREFS_MAC_KEY = com.rbt.vrde.utils.R.utils.MacUtils._mac;

    private MacUtil(Context context) {
        this.context = context;
    }

    public static MacUtil getInstance(Context context) {
        if (macUtil == null) {
            macUtil = new MacUtil(context);
        }
        return macUtil;
    }

    @SuppressLint("MissingPermission")
    public String getWifiMac() {
        if (!SystemUtil.checkPermission(com.rbt.vrde.utils.R.utils.MacUtils._android_permission_ACCESS_WIFI_STATE)) {
            return com.rbt.vrde.utils.R.utils.MacUtils._02_00_00_00_00_00;
        }
        CryptPrefs prefs = CryptPrefs.getPrefs(context, Constant.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        String mac = prefs.getString(PREFS_MAC_KEY, null);
        if (StringUtil.isNotBlank(mac)) {
            return mac;
        }
        //TODO理解系统服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            return null;
        }
        WifiInfo wifiInfo = null;
        if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_UNKNOWN) {
            wifiInfo = wifiManager.getConnectionInfo();
            mac = wifiInfo.getMacAddress();
        }
        if (StringUtil.isBlank(mac) || mac.equals(com.rbt.vrde.utils.R.utils.MacUtils._02_00_00_00_00_00)) {
            mac = getMacAddress();
        }
        //TODO
        return mac;

    }

    private String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface net : all) {
                if (!net.getName().equalsIgnoreCase(com.rbt.vrde.utils.R.utils.MacUtils._wlan0)) {
                    continue;
                }
                byte[] bytes = net.getHardwareAddress();
                if (bytes == null) {
                    return null;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : bytes) {
                    builder.append(String.format(com.rbt.vrde.utils.R.utils.MacUtils.__02X_, b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                return builder.toString().toLowerCase();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


}
