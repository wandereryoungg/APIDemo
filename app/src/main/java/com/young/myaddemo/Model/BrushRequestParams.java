package com.young.myaddemo.Model;

import com.young.myaddemo.utils.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class BrushRequestParams {

    public static String generateSTApiParams(STModel model) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pos_id", "广告位id");
            jsonObject.put("os_type", 1);
            jsonObject.put("os_ver", SystemUtil.getOSVCode());
            jsonObject.put("app_ver", SystemUtil.getAppVer());
            jsonObject.put("app_package_name", SystemUtil.getPackageName());
            jsonObject.put("dev_type", 1);
            jsonObject.put("dev_vendor", SystemUtil.getDevVendor());
            jsonObject.put("dev_model", SystemUtil.getDevModel());
            jsonObject.put("mac", SystemUtil.getWifiMac());
            jsonObject.put("scrn_width", SystemUtil.getScreenSize().get("width"));
            jsonObject.put("scrn_height", String.valueOf(SystemUtil.getScreenSize().get("height")));
            //TODO model没有赋值
            jsonObject.put("ip", model.ipv4);
            if (model.deviceInfo != null) {
                jsonObject.put("operator", SystemUtil.getOperatorTypeInt(model.deviceInfo.imsi));
            } else {
                jsonObject.put("operator", SystemUtil.getOperatorTypeInt(SystemUtil.getIMSI()));
            }
            jsonObject.put("conn_type", SystemUtil.getNetworkType());
            jsonObject.put("ad_width", model.adslotWidth);
            jsonObject.put("ad_height", model.adslotHeight);
            jsonObject.put("android_id", SystemUtil.getAndroidId());
            jsonObject.put("imei", SystemUtil.getIMEI());
            jsonObject.put("imsi", SystemUtil.getIMSI());
            jsonObject.put("ppi", SystemUtil.getScreenDensity());
            jsonObject.put("brand", SystemUtil.getDeviceBrand());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
