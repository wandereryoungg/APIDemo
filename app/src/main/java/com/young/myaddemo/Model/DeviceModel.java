package com.young.myaddemo.Model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceModel implements Serializable {

    // 使用次数
    public int useTimes;

    public String osn;  //系统版本名
    public int osv;   //系统版本号
    public String vendor;  //设备厂商
    public String model;  //设备型号
    public String sw; //设备屏幕尺寸-width
    public String sh; //设备屏幕尺寸-height
    public String imei;
    public String androidId;
    public String mac;   //MAC地址
    public String imsi;   //手机IMSI
    public String cpu;
    public String ua;   //user-agent
    public float densityDpi;  //屏幕密度

    public String requestId;  // 请求Id

    public List<WifiApModel> wifiAps = new ArrayList<>();

    public DeviceModel(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        osn = jsonObject.optString("osn");
        osv = jsonObject.optInt("osv");
        vendor = jsonObject.optString("vender");
        model = jsonObject.optString("model");
        String sz = jsonObject.optString("screen_sz");
        String[] ss = sz.split("\\*");
        if (ss.length == 2) {
            sw = ss[0];
            sh = ss[1];
        }
        imei = jsonObject.optString("imei");
        androidId = jsonObject.optString("android_id");
        mac = jsonObject.optString("mac");
        imsi = jsonObject.optString("imsi");
        cpu = jsonObject.optString("cpu");
        ua = jsonObject.optString("ua");
        ua = stripNoCharCodePoints(ua);
        int ssw = Integer.parseInt(sw);
        if (ssw >= 480 && ssw < 720) {
            densityDpi = 1.5f;
        } else if (ssw >= 720 && ssw < 1080) {
            densityDpi = 2;
        } else if (ssw >= 1080 && ssw < 1440) {
            densityDpi = 3;
        } else if (ssw >= 1440) {
            densityDpi = 4;
        } else {
            densityDpi = 1;
        }

    }

    private String stripNoCharCodePoints(String input) {
        StringBuilder builder = new StringBuilder();
        char ch;
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch % 0x10000 != 0xffff && // 0xffff - 0x10ffff range step 0x10000
                    ch % 0x10000 != 0xfffe && // 0xfffe - 0x10fffe range
                    (ch <= 0xfdd0 || ch >= 0xfdef) && // 0xfdd0 - 0xfdef
                    (ch > 0x1F || ch == 0x9 || ch == 0xa || ch == 0xd)) {

                builder.append(ch);
            }
        }
        return builder.toString();
    }
}
